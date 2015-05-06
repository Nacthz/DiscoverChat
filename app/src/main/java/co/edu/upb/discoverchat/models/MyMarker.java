package co.edu.upb.discoverchat.models;

import android.graphics.Bitmap;

public class MyMarker
{
    private Bitmap mIcon;
    private Double mLatitude;
    private Double mLongitude;

    public MyMarker(Bitmap icon, Double latitude, Double longitude)
    {
        this.mLatitude = latitude;
        this.mLongitude = longitude;
        this.mIcon = icon;
    }
    public Bitmap getmIcon()
    {
        return mIcon;
    }

    public void setmIcon(Bitmap icon)
    {
        this.mIcon = icon;
    }

    public Double getmLatitude()
    {
        return mLatitude;
    }

    public void setmLatitude(Double mLatitude)
    {
        this.mLatitude = mLatitude;
    }

    public Double getmLongitude()
    {
        return mLongitude;
    }

    public void setmLongitude(Double mLongitude)
    {
        this.mLongitude = mLongitude;
    }
}