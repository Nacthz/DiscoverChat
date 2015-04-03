package co.edu.upb.discoverchat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.List;

import co.edu.upb.discoverchat.models.Message;

public class MessageAdapter extends ArrayAdapter{

    private int resource;
    private LayoutInflater inflater;
    private Context context;

    public MessageAdapter ( Context ctx, int resourceId, List objects) {
        super( ctx, resourceId, objects );
        resource = resourceId;
        inflater = LayoutInflater.from( ctx );
        context=ctx;
    }

    @Override
    public View getView ( int position, View convertView, ViewGroup parent ) {

        /* create a new view of my layout and inflate it in the row */
        convertView = (FrameLayout) inflater.inflate( resource, null );

        /* Extract the city's object to show */
        Message message = (Message) getItem(position);

        /* Take the TextView from layout and set the city's name */
        TextView txtName = (TextView) convertView.findViewById(R.id.message_txt);

        if(message.itsMine()){
            txtName.setText(message.getTxt() + "Es mio");
        }else{
            txtName.setText(message.getTxt() + "De un puto");
        }



        return convertView;
    }
}