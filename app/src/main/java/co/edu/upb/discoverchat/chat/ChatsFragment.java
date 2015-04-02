package co.edu.upb.discoverchat.chat;

import android.app.Fragment;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

import co.edu.upb.discoverchat.MessageActivity;
import co.edu.upb.discoverchat.R;
import co.edu.upb.discoverchat.data.db.ChatsManager;
import co.edu.upb.discoverchat.models.Chat;

public class ChatsFragment extends Fragment {
    public static final String TAG = "chats";
    ListView chatList;
    ChatsAdapter adapter;
    public ArrayList<Chat> chats = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_chats, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setChatListData();
        Resources res = getResources();
        chatList = (ListView)getActivity().findViewById(R.id.chats_lst);
        adapter = new ChatsAdapter(this.getActivity(), chats,res,this);
        chatList.setAdapter(adapter);
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getActivity().getMenuInflater().inflate(R.menu.menu_chats, menu);
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

    public void setChatListData() {
        ChatsManager manager = new ChatsManager(getActivity());
        chats.addAll(manager.getAll());
    }
    public void onItemClick(long mPosition) {
        Intent intent = new Intent(this.getActivity(), MessageActivity.class);
        startActivity(intent);
    }
}
