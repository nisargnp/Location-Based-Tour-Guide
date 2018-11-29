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
     * Update the tour object. Does NOT upload image.
     * @param id id
     * @param tour tour
     * @return id
     */
    public static String updateFirebaseRaw(String id, Tour tour) {
        dbTours.child(id).setValue(tour);
        return id;
    }

    /**
     * Update the place object. Does NOT upload image, video, or audio.
     * @param id id
     * @param place place
     * @return id
     */
    public static String updateFirebaseRaw(String id, Place place) {
        dbPlaces.child(id).setValue(place);
        return id;
    }

    /**
     * Update the comment object.
     * @param id id
     * @param comment comment
     * @return id
     */
    public static String updateFirebaseRaw(String id, Comment comment) {
        dbComments.child(id).setValue(comment);
        return id;
    }

    /**
     * Update the user object.
     * @param id id
     * @param user user
     * @return id
     */
    public static String updateFirebaseRaw(String id, User user) {
        dbUsers.child(id).setValue(user);
        return id;
    }

    /**
     * Use this to upload a Tour to Firebase.
     * Tour.pictureFile should be set to the ~local~ path for the data.
     *
     * @param context context
     * @param tour tour
     */
    public static String uploadToFirebase(Context context, Tour tour, Runnable onFinish) {
        String id = dbTours.push().getKey();
        tryUploadTourImage(context, id, tour, onFinish);
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
    public static String uploadToFirebase(Context context, Place place, Runnable onFinish) {
        String id = dbPlaces.push().getKey();
        tryUploadPlaceImage(context, id, place, onFinish);
        return id;
    }

    /**
     * Use this to upload a Comment to Firebase.
     *
     * @param context context
     * @param comment comment
     * @return id
     */
    public static String uploadToFirebase(Context context, Comment comment, Runnable onFinish) {
        String id = dbComments.push().getKey();
        uploadToFirebaseRaw(id, comment);
        if (onFinish != null) onFinish.run();
        return id;
    }

    /**
     * Use this to upload a User to Firebase.
     *
     * @param context context
     * @param user user
     * @return id
     */
    public static String uploadToFirebase(Context context, User user, Runnable onFinish) {
        String id = dbUsers.push().getKey();
        uploadToFirebaseRaw(id, user);
        if (onFinish != null) onFinish.run();
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
    private static void tryUploadTourImage(Context context, String id, Tour tour, Runnable onFinish) {
        if (tour.getPictureFile().length() != 0) {
            uploadFileToFirebase(context, tour.getPictureFile(), "image", tour.getPictureFile(), uri -> {
                tour.setPictureFile(uri.toString());
                uploadToFirebaseRaw(id, tour);
            }, onFinish);
        } else {
            uploadToFirebaseRaw(id, tour);
        }
    }

    // private helper
    private static void tryUploadPlaceImage(Context context, String id, Place place, Runnable onFinish) {
        if (place.getPictureFile() != null && place.getPictureFile().length() != 0) {
            uploadFileToFirebase(context, place.getPictureFile(), "image", place.getPictureFile(), uri -> {
                place.setPictureFile(uri.toString());
                tryUploadPlaceAudio(context, id, place, onFinish);
            }, onFinish);
        } else {
            tryUploadPlaceAudio(context, id, place, onFinish);
        }
    }

    // private helper
    private static void tryUploadPlaceAudio(Context context, String id, Place place, Runnable onFinish) {
        if (place.getAudioFile() != null && place.getAudioFile().length() != 0) {
            uploadFileToFirebase(context, place.getAudioFile(), "audio", place.getAudioFile(), uri -> {
                place.setAudioFile(uri.toString());
                tryUploadPlaceVideo(context, id, place, onFinish);
            }, onFinish);
        } else {
            tryUploadPlaceVideo(context, id, place, onFinish);
        }
    }

    // private helper
    private static void tryUploadPlaceVideo(Context context, String id, Place place, Runnable onFinish) {
        if (place.getVideoFile() != null && place.getVideoFile().length() != 0) {
            uploadFileToFirebase(context, place.getVideoFile(), "video", place.getVideoFile(), uri -> {
                place.setVideoFile(uri.toString());
                uploadToFirebaseRaw(id, place);
            }, onFinish);
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
    private static void uploadFileToFirebase(Context context, String filePath, String uploadDir, String uploadName, OnUriResultListener onUriResultListener, Runnable onFinish) {
        ProgressDialog progressDialog = ProgressDialog.show(context, "Uploading", "Please wait...");
        File file = new File(uploadName);
        uploadFileToFirebase(filePath, uploadDir, file.getName(), uri -> {
            if(progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
            if (onUriResultListener != null) onUriResultListener.onUriResult(uri);
            if (onFinish != null) onFinish.run();
        });
    }

}
