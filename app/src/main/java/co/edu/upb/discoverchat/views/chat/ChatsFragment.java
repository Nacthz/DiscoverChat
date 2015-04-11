package co.edu.upb.discoverchat.views.chat;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

import co.edu.upb.discoverchat.R;
import co.edu.upb.discoverchat.data.db.ChatsManager;
import co.edu.upb.discoverchat.data.db.base.DbBase;
import co.edu.upb.discoverchat.models.Chat;
import co.edu.upb.discoverchat.views.message.MessageActivity;

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

    public void makeGroup(){
        LayoutInflater li = LayoutInflater.from(getActivity());
        View groupView = li.inflate(R.layout.dialog_group, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setView(groupView);

        final EditText userInput = (EditText) groupView.findViewById(R.id.group_txt_name);

        alertDialogBuilder
                .setCancelable(false)
                .setNegativeButton("Salir",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                dialog.cancel();
                            }
                        })
                .setPositiveButton("Crear",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                //TODO CREAR GRUPO
                                getActivity().setTitle(userInput.getText().toString());
                            }
                        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        menu.clear();
        getActivity().getMenuInflater().inflate(R.menu.menu_chats, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id) {
            case R.id.action_group:
                    makeGroup();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void setChatListData() {
        ChatsManager manager = new ChatsManager(getActivity());
        chats.addAll(manager.getAll());
    }
    public void onItemClick(long mPosition) {
        Intent intent = new Intent(this.getActivity(), MessageActivity.class);
        intent.putExtra(DbBase.KEY_CHAT_ID, mPosition);
        startActivity(intent);
    }
}
