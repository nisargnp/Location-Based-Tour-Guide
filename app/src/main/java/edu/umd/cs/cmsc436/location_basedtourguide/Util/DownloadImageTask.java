package edu.umd.cs.cmsc436.location_basedtourguide.Util;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.InputStream;

import edu.umd.cs.cmsc436.location_basedtourguide.Interface.OnBitmapResultListener;

/**
 * From
 * http://web.archive.org/web/20120802025411/http://developer.aiwgame.com/imageview-show-image-from-url-on-android-4-0.html
 */
public class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
    private OnBitmapResultListener onBitmapResultListener;

    public DownloadImageTask(OnBitmapResultListener onBitmapResultListener) {
        this.onBitmapResultListener = onBitmapResultListener;
    }

    protected Bitmap doInBackground(String... uris) {
        String uri = uris[0];
        try {
            InputStream in = new java.net.URL(uri).openStream();
            return BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    protected void onPostExecute(Bitmap bitmap) {
        onBitmapResultListener.onBitmapResult(bitmap);
    }
}
