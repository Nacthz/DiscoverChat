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

        TextMessage m1 = new TextMessage() {
            @Override
            public Type getType() {
                return Type.TEXT;
            }
        };
        m1.setContent("Prueba1");
        TextMessage m2 = new TextMessage() {
            @Override
            public Type getType() {
                return Type.TEXT;
            }
        };
        m2.setContent("Prueba2");
        TextMessage m3 = new TextMessage() {
            @Override
            public Type getType() {
                return Type.TEXT;
            }
        };
        m3.setContent("Prueba1");
        TextMessage m4 = new TextMessage() {
            @Override
            public Type getType() {
                return Type.TEXT;
            }
        };
        m4.setContent("Prueba1");
        TextMessage m5 = new TextMessage() {
            @Override
            public Type getType() {
                return Type.TEXT;
            }
        };
        m5.setContent("Prueba1");
        TextMessage m6 = new TextMessage() {
            @Override
            public Type getType() {
                return Type.TEXT;
            }
        };
        m6.setContent("Prueba2");
        TextMessage m7 = new TextMessage() {
            @Override
            public Type getType() {
                return Type.TEXT;
            }
        };
        m7.setContent("Prueba1");
        TextMessage m8 = new TextMessage() {
            @Override
            public Type getType() {
                return Type.TEXT;
            }
        };

        messages.add(m1);
        messages.add(m2);
        messages.add(m3);
        messages.add(m4);
        messages.add(m5);
        messages.add(m6);
        messages.add(m7);

        Resources res = getResources();
        messageList = (ListView) findViewById(R.id.message_lst);
        messageList.setDivider(null);
        messageList.setDividerHeight(0);
        adapter = new MessageAdapter(this, messages,res);

        messageList.setAdapter(adapter);

        /*
        ctx=this;
        List listMessage= new ArrayList();
        listMessage.add(new Message((long) 1,"Hola puto", true));
        listMessage.add(new Message((long) 2,"Carenalga", true));
        listMessage.add(new Message((long) 3,"Que paso? weon", false));
        listMessage.add(new Message((long) 2,"La wea fome weon", true));
        listMessage.add(new Message((long) 3,"Pongase a trabajar...", false));
        listMessage.add(new Message((long) 3,"Mejor", false));
        listMessage.add(new Message((long) 3,"Oe no me deje en visto", false));
        listMessage.add(new Message((long) 3,"Puto", false));

        listViewMessage = ( ListView ) findViewById( R.id.message_lst);
        listViewMessage.setAdapter( new MessageAdapter(ctx, R.layout.message_item, listMessage));
        */
    }

    private void setMessageList(){
        TextMessagesManager textMessagesManager = new TextMessagesManager(this);
        // messages.addAll(textMessagesManager.<TextMessage>getAll());
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
