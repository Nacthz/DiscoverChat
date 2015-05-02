package co.edu.upb.discoverchat.views.user;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import co.edu.upb.discoverchat.R;

public class ProfileFragment extends Fragment {
    public static final String TAG = "profile";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //TODO poglamar

        final ImageView imv_profile = (ImageView) getView().findViewById(R.id.imv_user_profile);
        imv_profile.setImageDrawable(getResources().getDrawable(R.drawable.avatar));
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.clear();
        getActivity().getMenuInflater().inflate(R.menu.menu_chats, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return super.onOptionsItemSelected(item);
    }
}