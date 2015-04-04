package co.edu.upb.discoverchat;

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
            message = null;
            //MessagesManager message_manager = new MessagesManager(activity);
            message = data.get(position);

            /*
            if(message.itsMine(activity)){
                lp.gravity = Gravity.RIGHT;

            }else{
                lp.gravity = Gravity.LEFT;
                LinearLayout ll = (LinearLayout) view.findViewById(R.id.layout_img);
                ll.setBackgroundColor(R.drawable.message_received);
            }
            */

            LinearLayout ll = (LinearLayout) view.findViewById(R.id.layout_img);
            TextMessage tmessage = (TextMessage) message;
            TextView tw = (TextView) view.findViewById(R.id.message_date);
            if(tmessage.getContent().equals("Prueba1")){
                //holder.user_name.setText(message.whoIsSender(activity).getName());
                holder.user_name.setText("Aldo Mora");
                //holder.message_txt.setText(tmessage.getContent());
                holder.message_txt.setText("Hola que hace");
                //TODO
                // holder.message_img.setImageResource(R.drawable.avatar);
                holder.message_date.setText("17:30");
                LinearLayout mainLayout = (LinearLayout) view.findViewById(R.id.layout_message_main);
                mainLayout.setGravity(Gravity.LEFT);
                tw.setTextColor(Color.parseColor("#BBBBBB"));
                ll.setBackgroundResource(R.drawable.message_received);
            }else{
                holder.user_name.setText("Yo");
                holder.message_txt.setText("Guat da fuak");
                holder.message_date.setText("17:31");
                LinearLayout mainLayout = (LinearLayout) view.findViewById(R.id.layout_message_main);
                mainLayout.setGravity(Gravity.RIGHT);

                tw.setTextColor(Color.parseColor("#92A95C"));
                ll.setBackgroundResource(R.drawable.message_send);
            }
            ll.setPadding(10,5,10,5);

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