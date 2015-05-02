package co.edu.upb.discoverchat.views.message;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Messenger;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;

import co.edu.upb.discoverchat.R;
import co.edu.upb.discoverchat.data.db.ChatsManager;
import co.edu.upb.discoverchat.data.db.ImageMessagesManager;
import co.edu.upb.discoverchat.data.db.ReceiversManager;
import co.edu.upb.discoverchat.data.db.TextMessagesManager;
import co.edu.upb.discoverchat.data.db.base.DbBase;
import co.edu.upb.discoverchat.data.db.base.MessageManager;
import co.edu.upb.discoverchat.data.web.MessageWeb;
import co.edu.upb.discoverchat.data.web.gcm.GcmIntentService;
import co.edu.upb.discoverchat.models.Chat;
import co.edu.upb.discoverchat.models.ImageMessage;
import co.edu.upb.discoverchat.models.Message;
import co.edu.upb.discoverchat.models.TextMessage;
import co.edu.upb.discoverchat.views.navigation.NavigationDrawerFragment;

import static co.edu.upb.discoverchat.data.db.base.DbBase.KEY_CHAT_ID;

public class MessageActivity extends Activity {

    public final static int QUALITY = 80;
    ListView messageList;
    MessageAdapter adapter;
    public ArrayList<Message> messages = new ArrayList<>();
    private Chat chat;
    public static final String MESSENGER = "messenger";
    static final int REQUEST_IMAGE_GET = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        loadChat();
        loadActionBar();
        setMessageList();
        readMessages();

        Resources res = getResources();
        messageList = (ListView) findViewById(R.id.message_lst);
        messageList.setDivider(null);
        messageList.setDividerHeight(0);
        adapter = new MessageAdapter(this, messages,res);

        messageList.setAdapter(adapter);
        findViewById(R.id.message_send_btn).setOnClickListener(sendMessage);
        findViewById(R.id.message_send_upload_img).setOnClickListener(selectImage);

        scrollChat();
    }

    private void readMessages() {
        ChatsManager chatsManager = new ChatsManager(this);
        HashMap<String, Object> udpateNew = new HashMap<>();
        udpateNew.put(DbBase.FIELD_READED, false);
        chatsManager.update(udpateNew, DbBase.KEY_ID + " = ?", ""+chat.getId());
    }

    private View.OnClickListener selectImage;
    {
        selectImage = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent pickIntent = new Intent();
                pickIntent.setType("image/*");
                pickIntent.setAction(Intent.ACTION_GET_CONTENT);
                Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                if (takePhotoIntent.resolveActivity(getPackageManager()) != null) {
                    // Create the File where the photo should go
                    File photoFile = null;
                    try {
                        photoFile = createImageFile();
                    } catch (IOException ex) {
                        // Error occurred while creating the File
                    }
                    // Continue only if the File was successfully created
                    if (photoFile != null) {
                        camera = true;
                        takePhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                                Uri.fromFile(photoFile));
                    }
                }

                String pickTitle = "Select or take a new Picture"; // Or get from strings.xml
                Intent chooserIntent = Intent.createChooser(pickIntent, pickTitle);
                chooserIntent.putExtra
                        (
                                Intent.EXTRA_INITIAL_INTENTS,
                                new Intent[] { takePhotoIntent }
                        );
               startActivityForResult(chooserIntent, REQUEST_IMAGE_GET);
            }
        };
    }

    String mCurrentPhotoPath;
    Boolean camera = false;

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStorageDirectory();
        storageDir = new File(storageDir.getPath() + "/Discoverchat");
        storageDir.mkdir();
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    public Bitmap getResize() {
        // Get the dimensions of the View
        int targetW = 285;
        int targetH = 380;

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW/targetW, photoH/targetH);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        return bitmap;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_GET && resultCode == RESULT_OK) {
            Bitmap image = null;
            image= getResize();
            FileOutputStream out = null;
            try {
                out = new FileOutputStream(mCurrentPhotoPath);
                image.compress(Bitmap.CompressFormat.JPEG, QUALITY, out);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }finally {
                if (out != null) {
                    try {
                        out.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            image = BitmapFactory.decodeFile(mCurrentPhotoPath);

            if(image==null){
                Uri selectedImageUri = data.getData();
                try {
                    image = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImageUri);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            ImageMessage message = new ImageMessage(image);
            ImageMessagesManager messagesManager = new ImageMessagesManager(MessageActivity.this);
            prepareMessage(message);
            message.getImage().setPath(mCurrentPhotoPath);
            messagesManager.add(message);
            MessageWeb web = new MessageWeb(MessageActivity.this);
            web.sendImageMessage(chat, message, null);
            messages.add(message);

            scrollChat();

            /*
            ImageMessage imageMessage= new ImageMessage(image);
            prepareMessage(imageMessage);
            MessageWeb web = new MessageWeb(MessageActivity.this);
            web.sendTextMessage(chat, imageMessage, null);
            messages.add(imageMessage);
            scrollChat();*/
        }
    }

    private void setForReceiveUpdates() {
        Handler handler = new Handler(){
            @Override
            public void handleMessage(android.os.Message msg) {
                super.handleMessage(msg);
                Bundle bundle = msg.getData();
                if(bundle.containsKey(DbBase.KEY_MESSAGE_ID)){
                    long id = bundle.getLong(DbBase.KEY_MESSAGE_ID);
                    if(id>0) {
                        Message message=null;
                        if(bundle.getString("type").equals("text")) {
                            message = new TextMessagesManager(MessageActivity.this).get(id);
                        }else{
                            message = new ImageMessagesManager(MessageActivity.this).get(id);
                        }
                        if(message.getChat_id()==chat.getId()){
                            addNewMessage(message);
                        }
                    }
                }
            }
        };
        GcmIntentService.bindMessenger(new Messenger(handler));
    }

    private void addNewMessage(Message textMessage) {
        messages.add(textMessage);
        scrollChat();
    }

    private View.OnClickListener sendMessage;
    {
        sendMessage = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText messageETxt = (EditText) findViewById(R.id.message_txt_field);
                String content = messageETxt.getText().toString();
                if(!content.equals("")) {
                    TextMessage message = new TextMessage();
                    TextMessagesManager textMessagesManager = new TextMessagesManager(MessageActivity.this);
                    prepareMessage(message);
                    message.setContent(content);
                    textMessagesManager.add(message);
                    MessageWeb web = new MessageWeb(MessageActivity.this);
                    web.sendTextMessage(chat, message, null);
                    messages.add(message);
                    messageETxt.setText("");
                    scrollChat();
                }
            }
        };
    }

    private void prepareMessage(Message message){
        ReceiversManager receiversManager = new ReceiversManager(MessageActivity.this);

        message.setDate(GregorianCalendar.getInstance().getTime());
        message.setChat_id(chat.getId());
        message.setReceiver(receiversManager.get(1));

    }
    private void scrollChat(){
        messageList.setSelection(messageList.getCount()-1);
        adapter.notifyDataSetChanged();
    }
    private void loadChat() {
        Bundle extras = getIntent().getExtras();
        chat = new ChatsManager(this).get(extras.getLong(KEY_CHAT_ID));
    }

    private void setMessageList(){
        MessageManager messageManager = new MessageManager(this);
        messages.addAll(messageManager.getAllMessagesForChat(chat));
    }

    public void loadActionBar(){
        final ActionBar actionBar = getActionBar();
        if (null != actionBar) {
            actionBar.setCustomView(R.layout.actionbar_message);
            actionBar.setDisplayHomeAsUpEnabled(true);
            Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.avatar);
            actionBar.setIcon(new NavigationDrawerFragment.RoundImage(bm));
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayShowCustomEnabled(true);
        }
        final TextView user_name = (TextView) findViewById(R.id.chat_txt_user_name);
        user_name.setText(chat.getName());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_message, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; goto parent activity.
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onStop() {
        GcmIntentService.unbindMessageMessenger();
        super.onStop();
    }

    @Override
    protected void onStart() {
        super.onStart();
        setForReceiveUpdates();
    }
}
