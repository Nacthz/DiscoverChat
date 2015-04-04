package co.edu.upb.discoverchat.data.web;

import android.content.Context;
import android.os.Bundle;

import co.edu.upb.discoverchat.data.db.ChatsManager;
import co.edu.upb.discoverchat.data.db.ReceiversManager;
import co.edu.upb.discoverchat.data.db.TextMessagesManager;
import co.edu.upb.discoverchat.data.provider.ContactProvider;
import co.edu.upb.discoverchat.data.web.base.RestClient;
import co.edu.upb.discoverchat.models.Chat;
import co.edu.upb.discoverchat.models.Receiver;
import co.edu.upb.discoverchat.models.TextMessage;

/**
 * Created by hatsumora on 4/04/15.
 * Allows recive and send data to the Drake server
 */
public class MessageWeb extends RestClient {
    private Context context;
    ReceiversManager receiversManager;
    ChatsManager chatsManager;
    public MessageWeb(Context context){
        this.context = context;
    }

    public void receiveMessage(Bundle extras){
        chatsManager = new ChatsManager(context);
        receiversManager = new ReceiversManager(context);
        TextMessagesManager textMessagesManager = new TextMessagesManager(context);

        Receiver receiver;
        if(extras.getString("type").equals("text")){
            TextMessage textMessage = new TextMessage();
            textMessage.setContent(extras.getString("content"));
            if(extras.containsKey("group_id")){
                //ToDO
                extras.getString("group_id");
            }else{
                receiver = receiversManager.findByField(ReceiversManager.FIELD_PHONE, extras.get("receiver"));
                if(receiver==null){

                    receiver = receiverFromBundle(extras);

                }
                textMessage.setReceiver_id(receiver.getId());
                Chat chat = chatsManager.getOneChatFor(receiver);

                textMessage.setChat_id(chat.getId());
                textMessagesManager.add(textMessage);
            }
        }
    }

    private Receiver receiverFromBundle(Bundle extras){
        Receiver receiver = new Receiver();
        ContactProvider contactProvider = new ContactProvider(context);
        receiver.setPhone(extras.get("receiver").toString());
        receiver.setName(contactProvider.nameOfPhone(receiver.getPhone()));
        receiversManager.add(receiver);
        return receiver;
    }

}
