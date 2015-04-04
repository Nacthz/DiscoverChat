package co.edu.upb.discoverchat.views.message;

import android.app.ActionBar;
import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import co.edu.upb.discoverchat.R;
import co.edu.upb.discoverchat.data.db.TextMessagesManager;
import co.edu.upb.discoverchat.models.Message;
import co.edu.upb.discoverchat.models.TextMessage;
import co.edu.upb.discoverchat.views.navigation.NavigationDrawerFragment;

public class MessageActivity extends Activity {

    ListView messageList;
    MessageAdapter adapter;
    public ArrayList<Message> messages = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        loadActionBar();
        setMessageList();

        Resources res = getResources();
        messageList = (ListView) findViewById(R.id.message_lst);
        messageList.setDivider(null);
        messageList.setDividerHeight(0);
        adapter = new MessageAdapter(this, messages,res);

        messageList.setAdapter(adapter);
    }

    private void setMessageList(){
        TextMessagesManager textMessagesManager = new TextMessagesManager(this);
        messages.addAll(textMessagesManager.<TextMessage>getAll());
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
        Bundle extras = getIntent().getExtras();
        final TextView user_name = (TextView) findViewById(R.id.chat_txt_user_name);
        user_name.setText("Name_chat_id_: " + extras.getLong("CHAT_ID"));
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
