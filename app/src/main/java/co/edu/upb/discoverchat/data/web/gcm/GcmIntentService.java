package co.edu.upb.discoverchat.data.web.gcm;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import java.util.ArrayList;

import co.edu.upb.discoverchat.views.message.MessageActivity;
import co.edu.upb.discoverchat.R;
import co.edu.upb.discoverchat.data.db.ChatsManager;
import co.edu.upb.discoverchat.data.db.TextMessagesManager;
import co.edu.upb.discoverchat.data.db.ReceiversManager;
import co.edu.upb.discoverchat.data.provider.ContactProvider;
import co.edu.upb.discoverchat.models.Chat;
import co.edu.upb.discoverchat.models.Receiver;
import co.edu.upb.discoverchat.models.TextMessage;

/**
 * Created by hatsumora on 3/04/15.
 * This class handles the incoming data from the Google servers
 */
public class GcmIntentService extends IntentService {

    public static final int NOTIFICATION_ID = 1;
    private NotificationManager mNotificationManager;
    NotificationCompat.Builder builder;
    private String TAG = "GcmIntent";
    public GcmIntentService() {
        super("GcmIntentService");
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
                // This loop represents the service doing some work.
                ChatsManager chatsManager = new ChatsManager(this);
                ReceiversManager receiversManager = new ReceiversManager(this);
                TextMessagesManager textMessagesManager = new TextMessagesManager(this);

                Receiver receiver;
                if(extras.getString("type").equals("text")){
                    TextMessage textMessage = new TextMessage();
                    textMessage.setContent(extras.getString("content"));
                    if(extras.containsKey("group_id")){
                        //ToDO
                    }else{
                        receiver = receiversManager.findByField(ReceiversManager.FIELD_PHONE, extras.get("receiver"));
                        if(receiver==null){
                            ContactProvider contactProvider = new ContactProvider(this);
                            receiver = new Receiver();
                            receiver.setPhone(extras.get("receiver").toString());
                            receiver.setName(contactProvider.nameOfPhone(receiver.getPhone()));
                            receiversManager.add(receiver);
                        }
                        textMessage.setReceiver_id(receiver.getId());
                        Chat chat = chatsManager.getOneChatFor(receiver);
                        if(chat==null) {
                            chat = new Chat();
                            ArrayList<Receiver> receivers = new ArrayList<>();
                            receivers.add(receiver);
                            chat.setReceivers(receivers);
                            chat.setName(receiver.getName());
                            chatsManager.add(chat);
                        }
                        textMessage.setChat_id(chat.getId());
                        textMessagesManager.add(textMessage);
                    }
                }
                // Post notification of received message.
                sendNotification("Received: " + extras.getString("content"));
                Log.i(TAG, "Received: " + extras.toString());
            }
        }
        // Release the wake lock provided by the WakefulBroadcastReceiver.
        GcmBroadcastReceiver.completeWakefulIntent(intent);
    }
    private void sendNotification(String msg) {
        mNotificationManager = (NotificationManager)
                this.getSystemService(Context.NOTIFICATION_SERVICE);

        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, MessageActivity.class), 0);

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.powered_by_google_light)
                        .setContentTitle("DiscoverChat")
                        .setStyle(new NotificationCompat.BigTextStyle()
                                .bigText(msg))
                        .setContentText(msg);

        mBuilder.setContentIntent(contentIntent);
        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
    }
}
