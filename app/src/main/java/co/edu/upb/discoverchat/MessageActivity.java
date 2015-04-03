package co.edu.upb.discoverchat;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import co.edu.upb.discoverchat.models.Message;
import co.edu.upb.discoverchat.navigation.NavigationDrawerFragment;

public class MessageActivity extends Activity {
    private ListView listViewMessage;
    private Context ctx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        loadActionBar();

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
    }

    public void setMessageList(){

    }

    public void loadActionBar(){
        final ActionBar actionBar = getActionBar();
        actionBar.setCustomView(R.layout.actionbar_message);
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
