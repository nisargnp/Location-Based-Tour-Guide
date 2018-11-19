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
    public static void uploadToFirebase(Context context, Tour tour) {
        if (tour.getPictureFile().length() != 0) {
            uploadFileToFirebase(context, tour.getPictureFile(), "image", tour.getPictureFile(), uri -> {
                tour.setPictureFile(uri.toString());
                uploadToFirebaseRaw(tour);
            });
        } else {
            uploadToFirebaseRaw(tour);
        }
    }

    /**
     * Use this to upload a Place to Firebase.
     * Place.pictureFile, Place.audioFile, and Place.videoFile should be set to the ~local~ path for the data.
     *
     * @param context context
     * @param place tour
     */
    public static void uploadToFirebase(Context context, Place place) {
        tryUploadPlaceImage(context, place);
    }

    /**
     * Use this to upload a Comment to Firebase.
     *
     * @param context context
     * @param comment comment
     */
    public static void uploadToFirebase(Context context, Comment comment) {
        uploadToFirebaseRaw(comment);
    }

    /**
     * Use this to upload a User to Firebase.
     *
     * @param context context
     * @param user user
     */
    public static void uploadToFirebase(Context context, User user) {
        uploadToFirebaseRaw(user);
    }

    /**
     * For testing only, don't use.
     * @param tour tour
     */
    public static void uploadToFirebaseRaw(Tour tour) {
        String id = dbTours.push().getKey();
        tour.setId(id);
        if (id != null) dbTours.child(id).setValue(tour);
    }

    /**
     * For testing only, don't use.
     * @param place place
     */
    public static void uploadToFirebaseRaw(Place place) {
        String id = dbPlaces.push().getKey();
        place.setId(id);
        if (id != null)dbPlaces.child(id).setValue(place);
    }

    /**
     * For testing only, don't use.
     * @param comment comment
     */
    public static void uploadToFirebaseRaw(Comment comment) {
        String id = dbComments.push().getKey();
        comment.setId(id);
        if (id != null)dbComments.child(id).setValue(comment);
    }

    /**
     * For testing only, don't use.
     * @param user user
     */
    public static void uploadToFirebaseRaw(User user) {
        String id = dbUsers.push().getKey();
        user.setId(id);
        if (id != null)dbUsers.child(id).setValue(user);
    }

    // private helper
    private static void tryUploadPlaceImage(Context context, Place place) {
        if (place.getPictureFile().length() != 0) {
            uploadFileToFirebase(context, place.getPictureFile(), "image", place.getPictureFile(), uri -> {
                place.setPictureFile(uri.toString());
                tryUploadPlaceAudio(context, place);
            });
        } else {
            tryUploadPlaceAudio(context, place);
        }
    }

    // private helper
    private static void tryUploadPlaceAudio(Context context, Place place) {
        if (place.getAudioFile().length() != 0) {
            uploadFileToFirebase(context, place.getAudioFile(), "audio", place.getAudioFile(), uri -> {
                place.setPictureFile(uri.toString());
                tryUploadPlaceVideo(context, place);
            });
        } else {
            tryUploadPlaceVideo(context, place);
        }
    }

    // private helper
    private static void tryUploadPlaceVideo(Context context, Place place) {
        if (place.getVideoFile().length() != 0) {
            uploadFileToFirebase(context, place.getVideoFile(), "video", place.getVideoFile(), uri -> {
                place.setPictureFile(uri.toString());
                uploadToFirebaseRaw(place);
            });
        } else {
            uploadToFirebaseRaw(place);
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
            progressDialog.dismiss();
            if (onUriResultListener != null) onUriResultListener.onUriResult(uri);
        });
    }

}
