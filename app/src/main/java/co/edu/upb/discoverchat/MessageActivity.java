package co.edu.upb.discoverchat;

import android.app.ActionBar;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import co.edu.upb.discoverchat.navigation.NavigationDrawerFragment;

public class MessageActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        final ActionBar actionBar = getActionBar();
        actionBar.setCustomView(R.layout.actionbar_messages);
        actionBar.setDisplayHomeAsUpEnabled(true);
        Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.avatar);

        actionBar.setIcon(new NavigationDrawerFragment.RoundImage(bm));
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);
        Bundle extras = getIntent().getExtras();
        final TextView user_name = (TextView) findViewById(R.id.chat_txt_user_name);
        user_name.setText("Name_chat_id_: " + extras.getLong("CHAT_ID"));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_message, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; goto parent activity.
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
