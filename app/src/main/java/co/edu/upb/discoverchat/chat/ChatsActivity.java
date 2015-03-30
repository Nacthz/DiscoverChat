package co.edu.upb.discoverchat.chat;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import java.util.ArrayList;
import co.edu.upb.discoverchat.R;
import co.edu.upb.discoverchat.data.db.ChatsManager;
import co.edu.upb.discoverchat.models.Chat;

public class ChatsActivity extends Activity {

    ListView chatList;
    ChatsAdapter adapter;
    public ChatsActivity chatsActivity = null;
    public ArrayList<Chat> chats = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chats);

        chatsActivity = this;
        setChatListData();
        Resources res = getResources();
        chatList = (ListView)findViewById(R.id.chats_lst);

        adapter = new ChatsAdapter(chatsActivity, chats,res );




        chatList.setAdapter(adapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_chats, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void setChatListData(){
        ChatsManager manager = new ChatsManager(this);
        chats.addAll(manager.getAllChats());
    }
    public void onItemClick(int mPosition)
    {
        Chat chat = chats.get(mPosition);
        startActivityForChat(chat);
    }
    private void startActivityForChat(Chat chat){
        //Intent intent = new Intent(this,MessagesActivity.class);
        //intent.putExtra("chat",chat.toString());
        //startActivity(intent);
    }
}