package co.edu.upb.discoverchat.views.message;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import co.edu.upb.discoverchat.R;
import co.edu.upb.discoverchat.models.ImageMessage;
import co.edu.upb.discoverchat.models.Message;
import co.edu.upb.discoverchat.models.TextMessage;

public class MessageAdapter extends BaseAdapter {
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


    public Object getItem(int position) {
        return position;
    }


    public long getItemId(int position) {
        return position;
    }

    public int getCount() {
        if(data.size()<=0)
            return 1;
        return data.size();
    }


    public View getView(int position, View view, ViewGroup viewGroup) {
        message = null;
        try {
            message = data.get(position);
        } catch (Throwable e) {
            e.printStackTrace();
        }

        if(message!=null){
            ViewHolder holder;
            if(view==null){
                view = inflater.inflate(R.layout.message_item,null);
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
                LinearLayout ll = (LinearLayout) view.findViewById(R.id.layout_img);
                TextView user_name = (TextView) view.findViewById(R.id.message_date);
                LinearLayout mainLayout = (LinearLayout) view.findViewById(R.id.layout_message_main);

                if (message.itsMine(activity)) {
                    mainLayout.setGravity(Gravity.RIGHT);
                    user_name.setTextColor(Color.parseColor("#92A95C"));
                    ll.setBackgroundResource(R.drawable.message_send);
                }else{
                    mainLayout.setGravity(Gravity.LEFT);
                    user_name.setTextColor(Color.parseColor("#BBBBBB"));
                    ll.setBackgroundResource(R.drawable.message_received);
                }

                holder.user_name.setText(message.whoIsSender(activity).getName());

                if(message.getType() == Message.Type.TEXT){
                    TextMessage tmessage = (TextMessage) message;
                    holder.message_txt.setText(tmessage.getContent());
                    holder.message_date.setText(tmessage.getDate().toString());
                }else{
                    ImageMessage imessage = (ImageMessage) message;
                    holder.message_img.setImageBitmap(imessage.getBitmap());
                    holder.message_date.setText(imessage.getDate().toString());
                }
                ll.setPadding(15,10,15,10);
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