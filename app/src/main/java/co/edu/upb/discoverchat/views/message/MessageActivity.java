package co.edu.upb.discoverchat.views.message;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Messenger;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.GregorianCalendar;

import co.edu.upb.discoverchat.R;
import co.edu.upb.discoverchat.data.db.ChatsManager;
import co.edu.upb.discoverchat.data.db.ReceiversManager;
import co.edu.upb.discoverchat.data.db.TextMessagesManager;
import co.edu.upb.discoverchat.data.db.base.DbBase;
import co.edu.upb.discoverchat.data.web.MessageWeb;
import co.edu.upb.discoverchat.data.web.gcm.GcmIntentService;
import co.edu.upb.discoverchat.models.Chat;
import co.edu.upb.discoverchat.models.Message;
import co.edu.upb.discoverchat.models.TextMessage;
import co.edu.upb.discoverchat.views.navigation.NavigationDrawerFragment;

import static co.edu.upb.discoverchat.data.db.base.DbBase.KEY_CHAT_ID;

public class MessageActivity extends Activity {

    ListView messageList;
    MessageAdapter adapter;
    public ArrayList<Message> messages = new ArrayList<>();
    private Chat chat;
    public static final String MESSENGER = "messenger";
    EditText txtSend;
    private  Messenger mMessenger;
    static final int REQUEST_IMAGE_GET = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        loadChat();
        loadActionBar();
        setMessageList();

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

    private View.OnClickListener selectImage;
    {
        selectImage = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent pickIntent = new Intent();
                pickIntent.setType("image/*");
                pickIntent.setAction(Intent.ACTION_GET_CONTENT);

                Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_GET && resultCode == RESULT_OK) {
                Uri selectedImageUri = data.getData();
                String selectedImagePath = getPath(selectedImageUri);
                if(selectedImagePath==null){
                    Bundle extras = data.getExtras();
                    Bitmap imageBitmap = (Bitmap) extras.get("data");
                    //setImageBitmap(imageBitmap);
                }else{
                   // setImageURI(selectedImageUri);
                }

                Toast.makeText(this, "-" + selectedImagePath + "-", Toast.LENGTH_LONG).show();
                //TODO u get a url, wtf do?
        }
    }

    public String getPath(Uri uri) {
        // just some safety built in
        if( uri == null ) {
            return null;
        }
        // try to retrieve the image from the media store first
        // this will only work for images selected from gallery
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        if( cursor != null ){
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        }
        // this is our fallback here
        return uri.getPath();
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
                        TextMessage textMessage = new TextMessagesManager(MessageActivity.this).get(id);
                        if(textMessage.getChat_id()==chat.getId()){
                            addNewMessage(textMessage);
                        }
                    }
                }
            }
        };
        GcmIntentService.bindMessenger(new Messenger(handler));

    }

    private void addNewMessage(TextMessage textMessage) {
        messages.add(textMessage);
        adapter.notifyDataSetChanged();
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
                    ReceiversManager receiversManager = new ReceiversManager(MessageActivity.this);
                    TextMessagesManager textMessagesManager = new TextMessagesManager(MessageActivity.this);

                    message.setContent(content);
                    message.setChat_id(chat.getId());
                    message.setReceiver(receiversManager.get(1));
                    message.setDate(GregorianCalendar.getInstance().getTime());
                    textMessagesManager.add(message);
                    MessageWeb web = new MessageWeb(MessageActivity.this);
                    web.sendTextMessage(chat, message, null);
                    messages.add(message);
                    adapter.notifyDataSetChanged();
                    messageETxt.setText("");
                    scrollChat();
                }
            }
        };
    }
    private void scrollChat(){
        messageList.setSelection(messageList.getCount()-1);
    }
    private void loadChat() {
        Bundle extras = getIntent().getExtras();
        chat = new ChatsManager(this).get(extras.getLong(KEY_CHAT_ID));
    }

    private void setMessageList(){
        TextMessagesManager textMessagesManager = new TextMessagesManager(this);
        messages.addAll(textMessagesManager.getAllBy(KEY_CHAT_ID,chat.getId()));
    }

    public void loadActionBar(){
        final ActionBar actionBar = getActionBar();
        if (null != actionBar) {
            actionBar.setCustomView(R.layout.actionbar_message);
        }
        actionBar.setDisplayHomeAsUpEnabled(true);
        Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.avatar);
        actionBar.setIcon(new NavigationDrawerFragment.RoundImage(bm));
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);
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
        GcmIntentService.unbindMessenger();
        super.onStop();
    }

    @Override
    protected void onStart() {
        super.onStart();
        setForReceiveUpdates();
    }
}
