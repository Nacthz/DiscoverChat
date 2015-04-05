package co.edu.upb.discoverchat.views.message;

import android.app.ActionBar;
import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.GregorianCalendar;

import co.edu.upb.discoverchat.R;
import co.edu.upb.discoverchat.data.db.ChatsManager;
import co.edu.upb.discoverchat.data.db.ReceiversManager;
import co.edu.upb.discoverchat.data.db.TextMessagesManager;
import co.edu.upb.discoverchat.data.db.base.DbBase;
import co.edu.upb.discoverchat.data.web.MessageWeb;
import co.edu.upb.discoverchat.models.Chat;
import co.edu.upb.discoverchat.models.Message;
import co.edu.upb.discoverchat.models.Receiver;
import co.edu.upb.discoverchat.models.TextMessage;
import co.edu.upb.discoverchat.views.navigation.NavigationDrawerFragment;

import static co.edu.upb.discoverchat.data.db.base.DbBase.KEY_CHAT_ID;

public class MessageActivity extends Activity {

    ListView messageList;
    MessageAdapter adapter;
    public ArrayList<Message> messages = new ArrayList<>();
    private Chat chat;
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

        scrollChat();
    }

    private View.OnClickListener sendMessage;

    {
        sendMessage = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText messageETxt = (EditText) findViewById(R.id.message_txt_field);
                String content = messageETxt.getText().toString();
                TextMessage message = new TextMessage();
                ReceiversManager receiversManager = new ReceiversManager(MessageActivity.this);
                TextMessagesManager textMessagesManager = new TextMessagesManager(MessageActivity.this);

                message.setContent(content);
                message.setChat_id(chat.getId());
                message.setReceiver(receiversManager.get(1));
                message.setDate(GregorianCalendar.getInstance().getTime());
                textMessagesManager.add(message);
                MessageWeb web = new MessageWeb(MessageActivity.this);
                web.sendTextMessage(chat,message,null);
                messages.add(message);
                adapter.notifyDataSetChanged();
                messageETxt.setText("");
                scrollChat();
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
}
