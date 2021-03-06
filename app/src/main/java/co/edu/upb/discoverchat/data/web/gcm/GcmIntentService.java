package co.edu.upb.discoverchat.data.web.gcm;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import co.edu.upb.discoverchat.data.db.ImageMessagesManager;
import co.edu.upb.discoverchat.data.db.TextMessagesManager;
import co.edu.upb.discoverchat.data.db.base.DbBase;
import co.edu.upb.discoverchat.data.web.MessageWeb;
import co.edu.upb.discoverchat.models.ImageMessage;
import co.edu.upb.discoverchat.models.TextMessage;
import co.edu.upb.discoverchat.views.MainActivity;
import co.edu.upb.discoverchat.R;

/**
 * Created by hatsumora on 3/04/15.
 * This class handles the incoming data from the Google servers
 */
public class GcmIntentService extends IntentService {

    public static final int NOTIFICATION_ID = 1;
    private NotificationManager mNotificationManager;
    private static Messenger messageMessenger;
    private static Messenger chatMessenger;

    private String TAG = "GcmIntent";
    public GcmIntentService() {
        super("GcmIntentService");
    }

    public static void bindMessenger(Messenger _mMessenger) {
        messageMessenger = _mMessenger;
    }

    public static void unbindMessageMessenger() {
        messageMessenger = null;
    }

    public static void bindChatMessenger(Messenger chatMessenger){
        Log.i("Bind","Bind Chat Messenger");
        GcmIntentService.chatMessenger = chatMessenger;
    }
    public static void unbindChatMessenger(){
        Log.i("Unbind","Unbind Chat Messenger");
        GcmIntentService.chatMessenger = null;
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("Service: ", "Stoped");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("Service: ", "Running");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Bundle extras = intent.getExtras();
        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
        // The getMessageType() intent parameter must be the intent you received
        // in your BroadcastReceiver.
        String messageType = gcm.getMessageType(intent);
        if (!extras.isEmpty()) {  // has effect of unparcelling Bundle
            /*
             * Filter messages based on message type. Since it is likely that GCM
             * will be extended in the future with new message types, just ignore
             * any message types you're not interested in, or that you don't
             * recognize.
             */
            if (GoogleCloudMessaging.
                    MESSAGE_TYPE_SEND_ERROR.equals(messageType)) {
                sendNotification("Send error: " + extras.toString());
            } else if (GoogleCloudMessaging.
                    MESSAGE_TYPE_DELETED.equals(messageType)) {
                sendNotification("Deleted messages on server: " +
                        extras.toString());
                // If it's a regular GCM message, do some work.
            } else if (GoogleCloudMessaging.
                    MESSAGE_TYPE_MESSAGE.equals(messageType)) {
                // This loop represents the service doing some work
                // En los extras: {receiver: 3142946469, content: "Un mensaje bien chingon", type: text}.
                Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                v.vibrate(750);

                Log.i(TAG, "Received: " + extras.toString());
                try {
                    Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                    Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
                    r.play();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if(extras.containsKey(DbBase.FIELD_TYPE)){
                    if(extras.getString(DbBase.FIELD_TYPE)==DbBase.TAG_IMAGES_UPDATE){
                        return;
                    }
                    if(extras.getString(DbBase.FIELD_TYPE).equals("text" ) || extras.getString(DbBase.FIELD_TYPE).equals("IMAGE") ){
                        MessageWeb web = new MessageWeb(this);
                        long id = web.receiveMessage(extras);
                        extras.putLong(DbBase.KEY_MESSAGE_ID, id);
                        if(extras.getString("type").equals("text")) {
                            TextMessagesManager messagesManager = new TextMessagesManager(this);
                            TextMessage tm = messagesManager.get(id);
                            extras.putLong(DbBase.KEY_CHAT_ID,tm.getChat_id());
                            if(updateGUI(extras)){
                                sendNotification(extras);
                            }
                        }else{
                            ImageMessagesManager imageMessagesManager = new ImageMessagesManager(this);
                            ImageMessage imageMessage = imageMessagesManager.get(id);
                            extras.putLong(DbBase.KEY_CHAT_ID,imageMessage.getChat_id());
                            if(updateGUI(extras)){
                                sendNotification(extras);
                            }
                        }
                        return;
                    }

                }
            }
        }
        // Release the wake lock provided by the WakefulBroadcastReceiver.
        GcmBroadcastReceiver.completeWakefulIntent(intent);
    }

    private boolean updateGUI(Bundle extras) {
        if(extras.getLong(DbBase.KEY_MESSAGE_ID)>0) {
            if(messageMessenger !=null){
                Messenger messenger = messageMessenger;
                Message message = Message.obtain();
                message.setData(extras);
                try {
                    messenger.send(message);
                    return false;
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }else if(chatMessenger!=null){
                Messenger messenger = chatMessenger;
                Message message = Message.obtain();
                message.setData(extras);
                try {
                    messenger.send(message);
                    return false;
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }
        return true;
    }
    private void sendNotification(String s){
        Log.e("Notification",s);
    }
    private void sendNotification(Bundle extras) {
        mNotificationManager = (NotificationManager)
                this.getSystemService(Context.NOTIFICATION_SERVICE);
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(DbBase.KEY_CHAT_ID, extras.getLong(DbBase.KEY_CHAT_ID));
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                intent, 0);

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("DiscoverChat")
                        .setStyle(new NotificationCompat.BigTextStyle()
                                .bigText(extras.getString("content")))
                        .setContentText(extras.getString("content"));

        mBuilder.setContentIntent(contentIntent);
        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
    }
}
