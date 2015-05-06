package co.edu.upb.discoverchat.models;

import android.graphics.Bitmap;

public class MyMarker
{
    Image image;
    private Bitmap mIcon;
    private Double mLatitude;
    private Double mLongitude;

    public MyMarker(Image image)
    {
        this.image = image;
    }
    public Bitmap getmIcon()
    {return image.getBitmap();}

    public void setmIcon(Bitmap icon)
    {
        this.image.setBitmap(icon);
    }

    public Double getmLatitude()
    {
        return image.getLatitude();
    }

    public void setmLatitude(Double mLatitude)
    {
        image.setLatitude(mLatitude);
    }

    public Double getmLongitude()
    {
        return image.getLongitude();
    }

    public void setmLongitude(Double mLongitude)
    {
        this.image.setLatitude(mLongitude);
    }
}