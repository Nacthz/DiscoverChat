package co.edu.upb.discoverchat.views.chat;

import android.app.Fragment;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import co.edu.upb.discoverchat.R;
import co.edu.upb.discoverchat.data.db.ChatsManager;
import co.edu.upb.discoverchat.data.db.base.DbBase;
import co.edu.upb.discoverchat.data.provider.ContactProvider;
import co.edu.upb.discoverchat.models.Chat;
import co.edu.upb.discoverchat.models.Receiver;
import co.edu.upb.discoverchat.views.chat.ChatsAdapter;
import co.edu.upb.discoverchat.views.message.MessageActivity;

/**
 * Created by felix on 10/04/2015.
 */
public class ContactFragment extends Fragment {
    public static final String TAG = "contacts";
    ListView contactsList;
    ChatsAdapter adapter;
    public ArrayList<Chat> contacts = new ArrayList<>();

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_chats, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ContactProvider cp = new ContactProvider(getActivity());
        Cursor c = cp.getAllContacts();
        if(c.moveToFirst()){
            int indexName = c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
            int indexNumber = c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
            do {
                Receiver r = new Receiver();

                r.setName(c.getString(indexName)).setPhone(c.getString(indexNumber));
                Chat chat = new Chat();
                List<Receiver> ress = new ArrayList<>();
                ress.add(r);
                chat.setReceivers(ress);
                chat.setName(r.getName());
                contacts.add(chat);
            }while (c.moveToNext());
        }
        Resources res = getResources();
        contactsList = (ListView)getActivity().findViewById(R.id.chats_lst);
        adapter = new ChatsAdapter(this.getActivity(), contacts,res,this);
        contactsList.setAdapter(adapter);
    }
    public void onItemClick(Chat chat) {
        Intent intent = new Intent(this.getActivity(), MessageActivity.class);
        ChatsManager chatsManager = new ChatsManager(getActivity());
        chat = chatsManager.makeConsistent(chat);
        intent.putExtra(DbBase.KEY_CHAT_ID, chat.getId());
        startActivity(intent);
    }
}
