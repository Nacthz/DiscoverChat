package co.edu.upb.discoverchat;

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
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import co.edu.upb.discoverchat.data.db.ChatsManager;
import co.edu.upb.discoverchat.models.Message;
import co.edu.upb.discoverchat.navigation.NavigationDrawerFragment;

public class MessageAdapter{
    private Activity activity;
    private ArrayList <Message> data;
    private static LayoutInflater inflater = null;
    public Resources res;
    Message message = null;

    public MessageAdapter(Activity activity, ArrayList<Message> data, Resources res) {
        this.activity = activity;
        this.data = data;
        this.res = res;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public int getCount() {
        if(data.size()<=0)
            return 1;
        return data.size();
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if(view==null){
            view = inflater.inflate(R.layout.chat_list_item,null);
            holder = new ViewHolder();
            holder.user_name = (TextView)view.findViewById(R.id.chat_txt_user_name);
            holder.message_img = (TextView)view.findViewById(R.id.chat_txt_user_name);
            holder.message_txt = (ImageView)view.findViewById(R.id.chat_img_profile);
            holder.message_date =(TextView)view.findViewById(R.id.chat_txt_last_message_date);
            view.setTag(holder);
        }else
            holder = (ViewHolder)view.getTag();
        if(data.size()<=0){
            //Noting
        }
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
        }
        return view;
    }

    public static class ViewHolder {
        public TextView user_name;
        public ImageView message_img;
        public TextView message_txt;
        public TextView message_date;
    }
}