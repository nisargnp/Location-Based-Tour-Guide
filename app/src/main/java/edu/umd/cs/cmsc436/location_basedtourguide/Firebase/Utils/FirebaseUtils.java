package edu.umd.cs.cmsc436.location_basedtourguide.Firebase.Utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;

import edu.umd.cs.cmsc436.location_basedtourguide.Interface.OnUriResultListener;

public class FirebaseUtils {

    private FirebaseUtils() {}

    /**
     * This method will upload a specified file to Firebase.
     * @param filePath path of file to upload from internal storage
     * @param uploadDir name of storage directory on Firebase
     * @param uploadName name of uploaded file on Firebase
     * @param onUriResultListener optional function to invoke when download URI becomes available
     */
    public static void uploadFileToFirebase(String filePath, String uploadDir, String uploadName, OnUriResultListener onUriResultListener) {
        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
        StorageReference storageRef =  firebaseStorage.getReference(uploadDir + "/" + uploadName);
        File file = new File(filePath);
        if (file.exists()) {
            // async
            storageRef.putFile(Uri.fromFile(file))
                    .addOnSuccessListener(taskSnapshot -> {
                        storageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                            if (onUriResultListener != null) onUriResultListener.onUriResult(uri);
                        });
                    }).addOnFailureListener(Throwable::printStackTrace);
        }
    }

    /**
     * This method will upload a specified file to Firebase and possibly show a progress dialog.
     * @param context app context
     * @param filePath path of file to upload from internal storage
     * @param uploadDir name of storage directory on Firebase
     * @param uploadName name of uploaded file on Firebase
     * @param showProgressDialog whether to show progress dialog popup
     * @param onUriResultListener optional function to invoke when download URI becomes available
     */
    public static void uploadFileToFirebase(Context context, String filePath, String uploadDir, String uploadName, boolean showProgressDialog, OnUriResultListener onUriResultListener) {
        if (showProgressDialog) {
            ProgressDialog progressDialog = ProgressDialog.show(context, "Uploading", "Please wait...");
            uploadFileToFirebase(filePath, uploadDir, uploadName, uri -> {
                progressDialog.dismiss();
                if (onUriResultListener != null) onUriResultListener.onUriResult(uri);
            });
        } else {
            uploadFileToFirebase(filePath, uploadDir, uploadName, null);
        }
    }

}
