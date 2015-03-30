package co.edu.upb.discoverchat.data.db;

import android.database.Cursor;

import java.util.List;

import co.edu.upb.discoverchat.models.Chat;
import co.edu.upb.discoverchat.models.Model;

/**
 * Created by hatsumora on 30/03/15.
 */
public interface DbInterface {
    // Adding new chat
    public long add(Model model);

    // Getting single chat
    public Model get(int id);

    // Getting All chats
    public List getAll();

    // Getting chats Count
    public int getAllCount();
    // Updating single chat
    //public int updateChat(Chat chat) {return 0;}

    // Deleting single chat
    public int delete(Model model);
}
