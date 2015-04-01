package co.edu.upb.discoverchat;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import co.edu.upb.discoverchat.data.provider.ContactProvider;


public class SplashActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ContactProvider cp = new ContactProvider(this);
        Cursor c = cp.getAllContacts();
        String name ="";
        if(c.moveToFirst()){
            do
            name += c.getString(0) + " - ";
            while (c.moveToNext());
        }
        Toast.makeText(this, name, Toast.LENGTH_LONG).show();
        if(checkUserData())
            launchMainActivity();
        else
            launchRegisterActivity();
        finish();
    }

    private void launchRegisterActivity() {
        //Intent intent = new Intent(this,)
    }

    private void launchMainActivity() {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }

    private boolean checkUserData() {

        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_splash, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
