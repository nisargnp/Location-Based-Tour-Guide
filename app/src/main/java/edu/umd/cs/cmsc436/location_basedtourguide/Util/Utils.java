package edu.umd.cs.cmsc436.location_basedtourguide.Util;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public final class Utils {

    private Utils() {}

    public static String putImageToInternalStorage(Context applicationContext, Bitmap bitmapImage, String dirName, String imageName){

        ContextWrapper cw = new ContextWrapper(applicationContext);

        // path to /data/data/yourapp/app_data/images
        File dir = cw.getDir(dirName, Context.MODE_PRIVATE);

        // Create imageDir
        File file = new File(dir, imageName);

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            bitmapImage.compress(Bitmap.CompressFormat.JPEG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fos != null) fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return file.getAbsolutePath();

    }

    public static Bitmap getImageFromInternalStorage(String imageName){
        File file = new File(imageName);
        if (file.exists()) {
            return BitmapFactory.decodeFile(file.getAbsolutePath());
        }
        return null;
    }

}
