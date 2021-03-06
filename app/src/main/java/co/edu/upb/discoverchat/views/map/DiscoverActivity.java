package co.edu.upb.discoverchat.views.map;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.HashMap;

import co.edu.upb.discoverchat.R;
import co.edu.upb.discoverchat.data.provider.UbicationProvider;
import co.edu.upb.discoverchat.data.web.ImageWeb;
import co.edu.upb.discoverchat.data.web.base.UIUpdater;
import co.edu.upb.discoverchat.models.Image;
import co.edu.upb.discoverchat.models.MyMarker;

public class DiscoverActivity extends Activity
{
    private static Double latitude, longitude;
    private GoogleMap mMap;
    private ArrayList<MyMarker> mMyMarkersArray = new ArrayList<MyMarker>();
    private HashMap<Marker, MyMarker> mMarkersHashMap;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discover);

        // Initialize the HashMap for Markers and MyMarker object

        Bitmap image = BitmapFactory.decodeResource(getResources(), R.drawable.avatar);



        mMarkersHashMap = new HashMap<Marker, MyMarker>();
        ImageWeb web = new ImageWeb(this);
        web.getClosestImages(new UIUpdater() {
            @Override
            public void updateUI() {
            }

            @Override
            public void updateUI(ArrayList<Image> images) {
                super.updateUI(images);
                for(Image image: images){
                    mMyMarkersArray.add(new MyMarker(image));
                    ImageWeb imageWeb = new ImageWeb(DiscoverActivity.this);
                    imageWeb.download(image);
                }
                plotMarkers(mMyMarkersArray);

            }
        });
        setUpMap();
        centerMe();
    }

    private void plotMarkers(ArrayList<MyMarker> markers)
    {
        if(markers.size() > 0)
        {
            for (MyMarker myMarker : markers)
            {
                // Create user marker with custom icon and other options
                MarkerOptions markerOption = new MarkerOptions().position(new LatLng(myMarker.getmLatitude(), myMarker.getmLongitude()));
                Marker currentMarker = mMap.addMarker(markerOption);
                mMarkersHashMap.put(currentMarker, myMarker);

                mMap.setInfoWindowAdapter(new MarkerInfoWindowAdapter());
            }
        }
    }
    private void centerMe(){

        mMap.setMyLocationEnabled(true);
        UbicationProvider ubicationProvider = UbicationProvider.getInstace(this);
        Location location = ubicationProvider.getLocation();

        //TODO Locaction siempre nulo
        if (location != null) {
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                    new LatLng(location.getLatitude(), location.getLongitude()), 12.0f));
        }
    }

    private void setUpMap()
    {

        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null)
        {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();

            // Check if we were successful in obtaining the map.

            if (mMap != null)
            {
                mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener()
                {
                    @Override
                    public boolean onMarkerClick(com.google.android.gms.maps.model.Marker marker)
                    {
                        marker.showInfoWindow();
                        return true;
                    }
                });

            }
            else
                Toast.makeText(getApplicationContext(), "Unable to create Maps", Toast.LENGTH_SHORT).show();
        }
    }

    public class MarkerInfoWindowAdapter implements GoogleMap.InfoWindowAdapter
    {
        public MarkerInfoWindowAdapter()
        {
        }

        @Override
        public View getInfoWindow(Marker marker)
        {
            return null;
        }

        @Override
        public View getInfoContents(Marker marker)
        {
            View v  = getLayoutInflater().inflate(R.layout.infowindow_layout, null);
            MyMarker myMarker = mMarkersHashMap.get(marker);
            ImageView markerIcon = (ImageView) v.findViewById(R.id.marker_icon);
            markerIcon.setImageBitmap(myMarker.getmIcon());
            return v;
        }
    }
}