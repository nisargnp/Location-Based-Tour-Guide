package edu.umd.cs.cmsc436.location_basedtourguide.Util;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public final class Utils {

    private Utils() {}

    // this method is only for testing
    public static String copyResourceToInternalStorage(Context appContext, int id, String dirName, String fileName) {
        ContextWrapper cw = new ContextWrapper(appContext);
        File dir = cw.getDir(dirName, Context.MODE_PRIVATE);
        File file = new File(dir, fileName);

        InputStream in = appContext.getResources().openRawResource(id);
        FileOutputStream out;

        try {
            out = new FileOutputStream(file.getAbsoluteFile());
            byte[] buff = new byte[1024];
            int read;
            try {
                while ((read = in.read(buff)) > 0) {
                    out.write(buff, 0, read);
                }
            } finally {
                in.close();
                out.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return file.getAbsolutePath();
    }

    // this method is only for testing
    public static String putImageToInternalStorage(Context applicationContext, Bitmap bitmapImage, String dirName, String imageName){

        ContextWrapper cw = new ContextWrapper(applicationContext);
        File dir = cw.getDir(dirName, Context.MODE_PRIVATE);
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

    // this method is only for testing
    public static Bitmap getImageFromInternalStorage(String imageName){
        File file = new File(imageName);
        if (file.exists()) {
            return BitmapFactory.decodeFile(file.getAbsolutePath());
        }
        return null;
    }

}
