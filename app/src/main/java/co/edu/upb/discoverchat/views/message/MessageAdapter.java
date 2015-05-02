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
import co.edu.upb.discoverchat.data.web.ImageWeb;
import co.edu.upb.discoverchat.data.web.base.UIUpdater;
import co.edu.upb.discoverchat.models.Chat;
import co.edu.upb.discoverchat.models.ImageMessage;
import co.edu.upb.discoverchat.models.Message;
import co.edu.upb.discoverchat.models.TextMessage;
import co.edu.upb.discoverchat.views.chat.ChatsFragment;
import co.edu.upb.discoverchat.views.chat.ContactFragment;

public class MessageAdapter extends BaseAdapter implements View.OnClickListener {
    private Activity activity;
    private ArrayList <Message> data;
    private static LayoutInflater inflater = null;
    public Resources res;
    private Chat chat;
    Message message = null;

    public MessageAdapter(Chat chat, Activity activity, ArrayList<Message> data, Resources res) {
        this.activity = activity;
        this.data = data;
        this.res = res;
        this.chat = chat;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    public Object getItem(int position) {
        return position;
    }


    public long getItemId(int position) {
        return position;
    }

    public int getCount() {
        return data.size();
    }

    @Override
    public boolean isEnabled (int position) {
        return false;
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
                    if(chat.isGroup())
                        holder.user_name.setText("Yo");
                    mainLayout.setGravity(Gravity.RIGHT);
                    user_name.setTextColor(Color.parseColor("#acbeb8"));
                    ll.setBackgroundResource(R.drawable.message_send);
                }else{
                    if(chat.isGroup())
                        holder.user_name.setText(message.getReceiver().getName());
                    mainLayout.setGravity(Gravity.LEFT);
                    user_name.setTextColor(Color.parseColor("#BBBBBB"));
                    ll.setBackgroundResource(R.drawable.message_received);
                }

                if(message.getType() == Message.Type.IMAGE){
                    ImageMessage imessage = (ImageMessage) message;
                    if(imessage.getImage().getPath()==null || ! (imessage.getImage().getPath().length()>0)){
                        ImageWeb imageWeb = new ImageWeb(activity);
                        imageWeb.setUpdater(new UIUpdater() {
                            @Override
                            public void updateUI() {
                                MessageAdapter.this.notifyDataSetChanged();
                            }
                        });
                        imageWeb.download(imessage.getImage());
                    }
                    holder.message_txt.setText("");
                    holder.message_img.setImageBitmap(imessage.getBitmap());
                    holder.message_date.setText(imessage.getDate().toString());

                }else{
                    TextMessage tmessage = (TextMessage) message;
                    holder.message_txt.setText(tmessage.getContent());
                    holder.message_img.setImageDrawable(null);
                    holder.message_date.setText(tmessage.getDate().toString());
                }
                ll.setPadding(15,10,15,10);
                if(!chat.isGroup()){
                    holder.user_name.setHeight(3);
                    holder.user_name.setMaxHeight(3);
                }
            }
        }
        view.setOnClickListener(new OnMessageClickListener(position));
        return view;
    }

    public class OnMessageClickListener implements View.OnClickListener{
        private int mPosition;
        private OnMessageClickListener(int mPosition) {
            this.mPosition = mPosition;
        }

        @Override
        public void onClick(View v) {
            message = data.get(mPosition);
            if(activity instanceof MessageActivity){
                ((MessageActivity)activity).onItemClick(message);
            }
        }
    }
    @Override
    public void onClick(View v) {}

    public static class ViewHolder {
        public TextView user_name;
        public ImageView message_img;
        public TextView message_txt;
        public TextView message_date;
    }
}