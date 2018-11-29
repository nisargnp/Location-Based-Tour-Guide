package edu.umd.cs.cmsc436.location_basedtourguide.Util.ImageLoader;

import android.graphics.Bitmap;
import android.util.LruCache;
import android.widget.ImageView;

public class ImageLoader {
    private static final ImageLoader ourInstance = new ImageLoader();
    private LruCache<String, Bitmap> mMemoryCache;

    public static ImageLoader getInstance() {
        return ourInstance;
    }

    private ImageLoader() {
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);

        // Use 1/8th of the available memory for this memory cache.
        final int cacheSize = maxMemory / 8;

        mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                // The cache size will be measured in kilobytes rather than
                // number of items.
                return bitmap.getByteCount() / 1024;
            }
        };
    }

    public void addBitmapToMemoryCache(String key, Bitmap bitmap) {
        if (getBitmapFromMemCache(key) == null) {
            mMemoryCache.put(key, bitmap);
        }
    }

    public Bitmap getBitmapFromMemCache(String key) {
        return mMemoryCache.get(key);
    }

    public void loadBitmap(String url, ImageView mImageView) {
        final Bitmap bitmap = getBitmapFromMemCache(url);
        if (bitmap != null) {
            mImageView.setImageBitmap(bitmap);
        } else {
            DownloadImageTask task = new DownloadImageTask(mImageView::setImageBitmap);
            task.execute(url);
        }
    }

}
