package co.edu.upb.discoverchat.data.web;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;

import co.edu.upb.discoverchat.data.db.ImageManager;
import co.edu.upb.discoverchat.data.db.base.DbBase;
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
                    final Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    return bitmap;
                } finally {
                    if (inputStream != null) {
                        inputStream.close();
                    }
                    entity.consumeContent();
                }
            }
        } catch (Exception e) {
            // Could provide a more explicit error message for IOException or
            // IllegalStateException
            e.printStackTrace();
            getRequest.abort();
        } finally {
            if (client != null) {
                client.close();
            }
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
                ImageManager imageManager = new ImageManager(context);
                HashMap<String, Object> update = new HashMap<>();
                update.put(DbBase.FIELD_IMAGE_PATH,path);
                imageManager.update(update, DbBase.KEY_ID+" == ?",String.valueOf(image.getId()));
            }
        }
    }
}
