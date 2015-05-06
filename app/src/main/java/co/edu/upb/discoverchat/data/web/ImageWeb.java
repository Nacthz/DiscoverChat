package co.edu.upb.discoverchat.data.web;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.SyncHttpClient;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import co.edu.upb.discoverchat.data.db.ImageManager;
import co.edu.upb.discoverchat.data.db.base.DbBase;
import co.edu.upb.discoverchat.data.provider.UbicationProvider;
import co.edu.upb.discoverchat.data.web.base.RestClient;
import co.edu.upb.discoverchat.data.web.base.UIUpdater;
import co.edu.upb.discoverchat.models.Image;

/**
 * Created by hatsumora on 1/05/15.
 *
 */
public class ImageWeb extends RestClient{
    private Context context;
    private Image image;
    private String path;
    private boolean waitForUpdate = false;
    private UIUpdater updater;

    public ImageWeb(Context context) {
        this.context = context;
    }

    public void setUpdater(UIUpdater updater) {
        waitForUpdate = true;
        this.updater = updater;
    }

    public void download(Image image){
        this.image = image;

        BitmapDownloader downloader = new BitmapDownloader();
        downloader.execute(image.getUrl());

    }

    static Bitmap downloadBitmap(String url) {
        final AndroidHttpClient client = AndroidHttpClient.newInstance("Android");
        final HttpGet getRequest;
        getRequest = new HttpGet(url);

        try {
            HttpResponse response = client.execute(getRequest);
            final int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != HttpStatus.SC_OK) {
                Log.w("ImageDownloader", "Error " + statusCode +
                        " while retrieving bitmap from " + url);
                return null;
            }

            final HttpEntity entity = response.getEntity();
            if (entity != null) {
                InputStream inputStream = null;
                try {
                    inputStream = entity.getContent();
                    Log.e("MSG", inputStream.toString());
                    return BitmapFactory.decodeStream(inputStream);
                } finally {
                    if (inputStream != null) {
                        inputStream.close();
                    }
                    entity.consumeContent();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            getRequest.abort();
        } finally {
            client.close();
        }
        return null;
    }

    private class BitmapDownloader extends AsyncTask<String, Void, Bitmap> {

        Bitmap bitmap;
        private String url;

        protected void onPreExecute(){
        }

        protected Bitmap doInBackground(String... args) {
            url = args[0];
            bitmap = downloadBitmap(url);
            return bitmap;
        }

        protected void onPostExecute(Bitmap m_bitmap) {
            if(waitForUpdate){
                updater.updateUI();
            }
            if(m_bitmap != null)
            {
                ImageWeb.this.image.setBitmap(m_bitmap);
                File storageDir = Environment.getExternalStorageDirectory();
                storageDir = new File(storageDir.getPath() + "/Discoverchat");
                storageDir.mkdir();
                OutputStream fOut;
                String name = url.substring(url.lastIndexOf("/")+1);
                File file = new File(storageDir, name);
                try {
                    file.createNewFile();
                    fOut = new FileOutputStream(file);
                    path = file.getAbsolutePath();
                    m_bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
                    fOut.flush();
                    fOut.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                image.setPath(path);
                if(image.getId()>0){
                    ImageManager imageManager = new ImageManager(context);
                    HashMap<String, Object> update = new HashMap<>();
                    update.put(DbBase.FIELD_IMAGE_PATH,path);
                    imageManager.update(update, DbBase.KEY_ID+" == ?",String.valueOf(image.getId()));
                }
            }
        }
    }

    public void getClosestImages(UIUpdater uiUpdater){
        final ArrayList<Image> images = new ArrayList();
        UbicationProvider ubicationProvider = UbicationProvider.getInstace(context);
        Location lastKnow = ubicationProvider.getLocation();
        final RequestParams requestParams = new RequestParams();
        requestParams.put(DbBase.FIELD_LONGITUDE, lastKnow.getLongitude());
        requestParams.put(DbBase.FIELD_LATITUDE,lastKnow.getLatitude());
        final SyncHttpClient httpClient = new SyncHttpClient();
        new AsyncTask<Void, Void, Void>(){
            @Override
            protected Void doInBackground(Void... params) {
                httpClient.get(getAbsoluteUrl(getClosestImagesPath()), requestParams, new JsonHttpResponseHandler(){
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);
                        Log.e("ResponseJson", response.toString());
                        images.add(new Image());
                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                        super.onSuccess(statusCode, headers, response);
                        for(int i = 0; i < response.length(); i++){
                            Image image = new Image();
                            images.add(image);
                            try {
                                image.setPath(response.getString(i));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        super.onFailure(statusCode, headers, throwable, errorResponse);
                        if(errorResponse!=null)
                            Log.e("Response", errorResponse.toString());
                    }
                });
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                for(Image image1:images){
                    Log.e("Response",image1.toString());
                }
                if(updater!=null)
                    updater.updateUI(images);
            }
        }.execute(  );
    }
}
