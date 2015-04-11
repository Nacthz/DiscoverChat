package co.edu.upb.discoverchat.views;

import android.app.Fragment;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import co.edu.upb.discoverchat.R;
import co.edu.upb.discoverchat.views.chat.ChatsFragment;
import co.edu.upb.discoverchat.views.navigation.NavigationDrawerCallbacks;
import co.edu.upb.discoverchat.views.navigation.NavigationDrawerFragment;
import co.edu.upb.discoverchat.views.user.ContactFragment;

public class MainActivity extends ActionBarActivity
        implements NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;
    private Toolbar mToolbar;
    boolean flag = false;
    Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        setSupportActionBar(mToolbar);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.fragment_drawer);

        // Set up the drawer.
        mNavigationDrawerFragment.setup(R.id.fragment_drawer, (DrawerLayout) findViewById(R.id.drawer), mToolbar);
        // populate the navigation drawer
        mNavigationDrawerFragment.setUserData("User", "E-Mail", BitmapFactory.decodeResource(getResources(), R.drawable.avatar));
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        switch (position) {
            case 0: //search//todo
                fragment = getFragmentManager().findFragmentByTag(ChatsFragment.TAG);
                if (fragment == null) {
                    fragment = new ChatsFragment();
                }
                getFragmentManager().beginTransaction().replace(R.id.container, fragment, ChatsFragment.TAG).commit();
                flag = true;
                this.setTitle("Chats");
                break;
            case 1: //stats
                break;
            case 2:
                fragment = getFragmentManager().findFragmentByTag(ContactFragment.TAG);
                if (fragment == null) {
                    fragment = new ContactFragment();
                    //setMenuItems("chat");
                }
                getFragmentManager().beginTransaction().replace(R.id.container, fragment, ContactFragment.TAG).commit();
                flag = false;
                this.setTitle("Contactos");
                break;
            case 3: //settings //todo
                break;
        }
    }


    @Override
    public void onBackPressed() {
        if (mNavigationDrawerFragment.isDrawerOpen())
            mNavigationDrawerFragment.closeDrawer();
        else
            super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_group:
                if(flag){
                    ChatsFragment chat = (ChatsFragment) fragment;
                    chat.makeGroup();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
