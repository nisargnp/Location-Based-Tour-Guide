package edu.umd.cs.cmsc436.location_basedtourguide.Firebase.Utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;

import edu.umd.cs.cmsc436.location_basedtourguide.Firebase.DTO.Comment;
import edu.umd.cs.cmsc436.location_basedtourguide.Firebase.DTO.Place;
import edu.umd.cs.cmsc436.location_basedtourguide.Firebase.DTO.Tour;
import edu.umd.cs.cmsc436.location_basedtourguide.Firebase.DTO.User;
import edu.umd.cs.cmsc436.location_basedtourguide.Interface.OnUriResultListener;

public class FirebaseUtils {

    private FirebaseUtils() {}

    private static FirebaseDatabase db = FirebaseDatabase.getInstance();
    private static DatabaseReference dbTours = db.getReference("Tours");
    private static DatabaseReference dbPlaces = db.getReference("Places");
    private static DatabaseReference dbComments = db.getReference("Comments");
    private static DatabaseReference dbUsers = db.getReference("Users");

    /**
     * Use this to clear Firebase db programmatically.
     */
    public static void clearFirebaseDB() {
        db.getReference().removeValue();
    }

    /**
     * Use this to upload a Tour to Firebase.
     * Tour.pictureFile should be set to the ~local~ path for the data.
     *
     * @param context contexxt
     * @param tour tour
     */
    public static String uploadToFirebase(Context context, Tour tour) {
        String id = dbTours.push().getKey();
        tryUploadTourImage(context, id, tour);
        return id;
    }

    /**
     * Use this to upload a Place to Firebase.
     * Place.pictureFile, Place.audioFile, and Place.videoFile should be set to the ~local~ path for the data.
     *
     * @param context context
     * @param place tour
     * @return id
     */
    public static String uploadToFirebase(Context context, Place place) {
        String id = dbPlaces.push().getKey();
        tryUploadPlaceImage(context, id, place);
        return id;
    }

    /**
     * Use this to upload a Comment to Firebase.
     *
     * @param context context
     * @param comment comment
     * @return id
     */
    public static String uploadToFirebase(Context context, Comment comment) {
        String id = dbComments.push().getKey();
        uploadToFirebaseRaw(id, comment);
        return id;
    }

    /**
     * Use this to upload a User to Firebase.
     *
     * @param context context
     * @param user user
     * @return id
     */
    public static String uploadToFirebase(Context context, User user) {
        String id = dbUsers.push().getKey();
        uploadToFirebaseRaw(id, user);
        return id;
    }

    /**
     * For testing only, don't use.
     * @param tour tour
     * @return id
     */
    public static String uploadToFirebaseRaw(String id, Tour tour) {
        if (id == null) id = dbTours.push().getKey();
        tour.setId(id);
        if (id != null) dbTours.child(id).setValue(tour);
        return id;
    }

    /**
     * For testing only, don't use.
     * @param place place
     * @return id
     */
    public static String uploadToFirebaseRaw(String id, Place place) {
        if (id == null) id = dbPlaces.push().getKey();
        place.setId(id);
        if (id != null)dbPlaces.child(id).setValue(place);
        return id;
    }

    /**
     * For testing only, don't use.
     * @param comment comment
     * @return id
     */
    public static String uploadToFirebaseRaw(String id, Comment comment) {
        if (id == null) id = dbComments.push().getKey();
        comment.setId(id);
        if (id != null)dbComments.child(id).setValue(comment);
        return id;
    }

    /**
     * For testing only, don't use.
     * @param user user
     * @return id
     */
    public static String uploadToFirebaseRaw(String id, User user) {
        if (id == null) id = dbUsers.push().getKey();
        user.setId(id);
        if (id != null)dbUsers.child(id).setValue(user);
        return id;
    }

    // private helper
    private static void tryUploadTourImage(Context context, String id, Tour tour) {
        if (tour.getPictureFile().length() != 0) {
            uploadFileToFirebase(context, tour.getPictureFile(), "image", tour.getPictureFile(), uri -> {
                tour.setPictureFile(uri.toString());
                uploadToFirebaseRaw(id, tour);
            });
        } else {
            uploadToFirebaseRaw(id, tour);
        }
    }

    // private helper
    private static void tryUploadPlaceImage(Context context, String id, Place place) {
        if (place.getPictureFile().length() != 0) {
            uploadFileToFirebase(context, place.getPictureFile(), "image", place.getPictureFile(), uri -> {
                place.setPictureFile(uri.toString());
                tryUploadPlaceAudio(context, id, place);
            });
        } else {
            tryUploadPlaceAudio(context, id, place);
        }
    }

    // private helper
    private static void tryUploadPlaceAudio(Context context, String id, Place place) {
        if (place.getAudioFile().length() != 0) {
            uploadFileToFirebase(context, place.getAudioFile(), "audio", place.getAudioFile(), uri -> {
                place.setPictureFile(uri.toString());
                tryUploadPlaceVideo(context, id, place);
            });
        } else {
            tryUploadPlaceVideo(context, id, place);
        }
    }

    // private helper
    private static void tryUploadPlaceVideo(Context context, String id, Place place) {
        if (place.getVideoFile().length() != 0) {
            uploadFileToFirebase(context, place.getVideoFile(), "video", place.getVideoFile(), uri -> {
                place.setPictureFile(uri.toString());
                uploadToFirebaseRaw(id, place);
            });
        } else {
            uploadToFirebaseRaw(id, place);
        }
    }

    /**
     * This method will upload a specified file to Firebase.
     * @param filePath path of file to upload from internal storage
     * @param uploadDir name of storage directory on Firebase
     * @param uploadName name of uploaded file on Firebase
     * @param onUriResultListener optional function to invoke when download URI becomes available
     */
    private static void uploadFileToFirebase(String filePath, String uploadDir, String uploadName, OnUriResultListener onUriResultListener) {
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
     * This method will upload a specified file to Firebase and show a progress dialog.
     * @param context app context
     * @param filePath path of file to upload from internal storage
     * @param uploadDir name of storage directory on Firebase
     * @param uploadName name of uploaded file on Firebase
     * @param onUriResultListener optional function to invoke when download URI becomes available
     */
    private static void uploadFileToFirebase(Context context, String filePath, String uploadDir, String uploadName, OnUriResultListener onUriResultListener) {
        ProgressDialog progressDialog = ProgressDialog.show(context, "Uploading", "Please wait...");
        uploadFileToFirebase(filePath, uploadDir, uploadName, uri -> {
            if(progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
            if (onUriResultListener != null) onUriResultListener.onUriResult(uri);
        });
    }

}
