package co.edu.upb.discoverchat.chat;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import co.edu.upb.discoverchat.NavigationDrawerFragment;
import co.edu.upb.discoverchat.R;
import co.edu.upb.discoverchat.data.db.ChatsManager;
import co.edu.upb.discoverchat.models.Chat;

public class ChatsAdapter extends BaseAdapter implements View.OnClickListener {

    private Activity activity;
    private Fragment fragment;
    private ArrayList <Chat> data;
    private static LayoutInflater inflater = null;
    public Resources res;
    Chat chat = null;

    public ChatsAdapter(Activity activity, ArrayList<Chat> data, Resources res, Fragment fragment) {
        this.activity = activity;
        this.fragment = fragment;
        this.data = data;
        this.res = res;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public void onClick(View view) {
        Log.v("CustomAdapter", "=====Row button clicked=====");
    }

    @Override
    public int getCount() {
        if(data.size()<=0)
            return 1;
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if(view==null){
            view = inflater.inflate(R.layout.chat_list_item,null);
            holder = new ViewHolder();
            holder.profile = (ImageView)view.findViewById(R.id.chat_img_profile);
            holder.user_name = (TextView)view.findViewById(R.id.chat_txt_user_name);
            holder.lastMessage = (TextView)view.findViewById(R.id.chat_txt_last_message);
            holder.lastMessage_date =(TextView)view.findViewById(R.id.chat_txt_last_message_date);
            view.setTag(holder);
        }else
            holder = (ViewHolder)view.getTag();
        if(data.size()<=0)
            holder.user_name.setText("No chats");
        else{
            chat = null;
            ChatsManager chat_manager = new ChatsManager(activity);
            chat = data.get(position);
            holder.user_name.setText(chat.getName());
            holder.lastMessage.setText(chat_manager.getLastMessageForChat(chat));
            holder.lastMessage_date.setText(chat_manager.getLastDateForChat(chat));
            //TODO Image Profile
            Bitmap bm = BitmapFactory.decodeResource(activity.getResources(), R.drawable.avatar);
            holder.profile.setImageDrawable(new NavigationDrawerFragment.RoundImage(bm));
            view.setOnClickListener(new OnChatClickListener(position));
        }
        return view;    }

    public static class ViewHolder {
        public TextView user_name;
        public ImageView profile;
        public TextView lastMessage;
        public TextView lastMessage_date;
    }

    private class OnChatClickListener implements View.OnClickListener{
        private int mPosition;
        private OnChatClickListener(int mPosition) {
            this.mPosition = mPosition;
        }

        @Override
        public void onClick(View view) {
            chat = data.get(mPosition);
            ((ChatsFragment)fragment).onItemClick(chat.getId());
        }
    }
}
