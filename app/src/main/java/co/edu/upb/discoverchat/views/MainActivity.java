package co.edu.upb.discoverchat.views;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import co.edu.upb.discoverchat.R;
import co.edu.upb.discoverchat.data.db.UserManager;
import co.edu.upb.discoverchat.models.User;
import co.edu.upb.discoverchat.views.chat.ChatsFragment;
import co.edu.upb.discoverchat.views.chat.ContactFragment;
import co.edu.upb.discoverchat.views.map.DiscoverActivity;
import co.edu.upb.discoverchat.views.navigation.NavigationDrawerCallbacks;
import co.edu.upb.discoverchat.views.navigation.NavigationDrawerFragment;
import co.edu.upb.discoverchat.views.user.ProfileFragment;

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

        UserManager um = new UserManager(this);
        User user = um.get(1);
        mNavigationDrawerFragment.setUserData("User", user.getEmail(), BitmapFactory.decodeResource(getResources(), R.drawable.avatar));
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        switch (position) {
            case 0: //Chats//
                fragment = getFragmentManager().findFragmentByTag(ChatsFragment.TAG);
                if (fragment == null) {
                    fragment = new ChatsFragment();
                }
                getFragmentManager().beginTransaction().replace(R.id.container, fragment, ChatsFragment.TAG).commit();
                flag = true;
                this.setTitle("Chats");
                break;
            case 1: //Discover//
                Intent intent = new Intent(this, DiscoverActivity.class);
                startActivity(intent);
                break;
            case 2: //Contacts//
                fragment = getFragmentManager().findFragmentByTag(ContactFragment.TAG);
                if (fragment == null) {
                    fragment = new ContactFragment();
                }
                getFragmentManager().beginTransaction().replace(R.id.container, fragment, ContactFragment.TAG).commit();
                flag = false;
                this.setTitle("Contactos");
                break;
            case 3://Profile//
                fragment = getFragmentManager().findFragmentByTag(ProfileFragment.TAG);
                if (fragment == null) {
                    fragment = new ProfileFragment();
                }
                getFragmentManager().beginTransaction().replace(R.id.container, fragment, ProfileFragment.TAG).commit();
                flag = false;
                this.setTitle("Perfil");
                break;
            case 4: //Settings//
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
