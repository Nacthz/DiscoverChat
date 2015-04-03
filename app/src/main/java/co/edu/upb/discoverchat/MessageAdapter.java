package co.edu.upb.discoverchat;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import co.edu.upb.discoverchat.models.Message;

public class MessageAdapter extends BaseAdapter{
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

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
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
            holder.user_name = (TextView)view.findViewById(R.id.message_user_name);
            holder.message_img = (ImageView)view.findViewById(R.id.message_img);
            holder.message_txt = (TextView)view.findViewById(R.id.message_txt);
            holder.message_date =(TextView)view.findViewById(R.id.message_date);
            view.setTag(holder);
        }else
            holder = (ViewHolder)view.getTag();
        if(data.size()<=0){
            //Noting
        }
        else{
            message = null;
            MessageManager message_manager = new MessageManager(activity);
            message = data.get(position);

            if(message.getType().equals("TEXT")){
                holder.user_name.setText(chat.getName());
                holder.message_txt.setText();
                holder.message_img.seti
                holder.message_date.setText();
            }


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