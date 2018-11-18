package edu.umd.cs.cmsc436.location_basedtourguide.Data.DataProvider;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import edu.umd.cs.cmsc436.location_basedtourguide.Firebase.DTO.Comment;
import edu.umd.cs.cmsc436.location_basedtourguide.Firebase.DTO.Place;
import edu.umd.cs.cmsc436.location_basedtourguide.Firebase.DTO.Tour;
import edu.umd.cs.cmsc436.location_basedtourguide.Firebase.DTO.User;
import edu.umd.cs.cmsc436.location_basedtourguide.R;
import edu.umd.cs.cmsc436.location_basedtourguide.Util.Utils;

public final class DataProvider {

    private DataProvider() {}

    private static List<Comment> comments;
    private static List<Place> places;
    private static List<Tour> tours;
    private static List<User> users;

    static {

        comments = new ArrayList<>();
        places = new ArrayList<>();
        tours = new ArrayList<>();
        users = new ArrayList<>();

        // tour 1
        Tour tour1 = new Tour("UMD");
        tour1.setId("-rmtSKxBkNkkeWXOdkgGr");
        tour1.setName("UMD");
        tour1.setLat(38.9869);
        tour1.setLon(-76.9426);
        tour1.setDescription("This is University of Maryland, College Park. It is the largest school in Maryland.");
        tour1.setAuthor("-mXyLdjKWUNEMNslvUPWu");
        tour1.setPictureFile("https://www.provost.umd.edu/images/umdimg10.jpg");
        tour1.setRating(77);
        tour1.setNumVotes(20);
        tour1.setPlaces(Arrays.asList("-vQLJaqfPfLJgObaJpUst", "-XCjumRBOpQaIAISygJMp", "-YSbepfiuBlDRajJUwYpB", "-mWYsAGcLRjeJBCzlDdxd"));
        tour1.setComments(Arrays.asList("-UzlvCqPYYlHDNVdCtGAw", "-cARNyPaVoMDmlwEtCsxW", "-MuAOmNFRTOPrTirfFiXV"));
        tours.add(tour1);

        Place terrapinRow = new Place("Terrapin Row");
        terrapinRow.setId("-vQLJaqfPfLJgObaJpUst");
        terrapinRow.setName("Terrapin Row");
        terrapinRow.setDescription("Students live here.");
        terrapinRow.setLat(38.980367);
        terrapinRow.setLon(-76.942366);
        terrapinRow.setPictureFile("https://img.offcampusimages.com/ksG-bVfAntpM3Y-0eCrDneMZGFw=/960x665/left/top/smart/images/ocgpt7bqm09amm4tieanigg8gw9rsk1nwh0tzenqzdm.jpeg");
        terrapinRow.setAudioFile("https://www.ee.columbia.edu/~dpwe/sounds/music/around_the_world-atc.wav");
        terrapinRow.setVideoFile("https://sample-videos.com/video123/mp4/720/big_buck_bunny_720p_1mb.mp4");
        places.add(terrapinRow);

        Place CSIC = new Place("Computer Science Instructional Center");
        CSIC.setId("-XCjumRBOpQaIAISygJMp");
        CSIC.setName("Computer Science Instructional Center");
        CSIC.setDescription("Students study here.");
        CSIC.setLat(38.990085);
        CSIC.setLon(-76.936182);
        CSIC.setPictureFile("http://www.cs.umd.edu/sites/default/files/csic.jpg");
        CSIC.setAudioFile("https://www.ee.columbia.edu/~dpwe/sounds/music/i_ran_so_far_away-flock_of_seagulls.wav");
        CSIC.setVideoFile("http://techslides.com/demos/sample-videos/small.mp4");
        places.add(CSIC);

        Place stamp = new Place("Stamp Student Union");
        stamp.setId("-YSbepfiuBlDRajJUwYpB");
        stamp.setName("Stamp Student Union");
        stamp.setDescription("Students eat here.");
        stamp.setLat(38.987881);
        stamp.setLon(-76.944855);
        stamp.setPictureFile("http://4.bp.blogspot.com/-qR-DNRpZhT0/VD6V8aae_rI/AAAAAAAAAVw/Cvm7VGLhCAk/s1600/DSC_0544.jpg");
        stamp.setAudioFile("https://www.ee.columbia.edu/~dpwe/sounds/music/africa-toto.wav");
        stamp.setVideoFile("");
        places.add(stamp);

        Place eppley = new Place("Eppley Recreational Center");
        eppley.setId("-mWYsAGcLRjeJBCzlDdxd");
        eppley.setName("Eppley Recreational Center");
        eppley.setDescription("Students exercise here.");
        eppley.setLat(38.993635);
        eppley.setLon(-76.945155);
        eppley.setPictureFile("http://1.bp.blogspot.com/-m6RNHUfcogg/UI1KzblHw1I/AAAAAAAAEDU/CMGB5h6hNLs/s1600/DSC00746.JPG");
        eppley.setAudioFile("");
        eppley.setVideoFile("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4");
        places.add(eppley);

        Comment comment1 = new Comment("-oTPklbIgOHaroQvAsOJT");
        comment1.setAuthor("-oTPklbIgOHaroQvAsOJT");
        comment1.setId("-UzlvCqPYYlHDNVdCtGAw");
        comment1.setText("This is an amazing university!");
        comments.add(comment1);

        Comment comment2 = new Comment("-oMHqmNoDIECTxzCiSVsO");
        comment2.setAuthor("-oMHqmNoDIECTxzCiSVsO");
        comment2.setId("-cARNyPaVoMDmlwEtCsxW");
        comment2.setText("This is an good university!");
        comments.add(comment2);

        Comment comment3 = new Comment("-QXUjhMkcfvlXWSctzZSd");
        comment3.setAuthor("-QXUjhMkcfvlXWSctzZSd");
        comment3.setId("-MuAOmNFRTOPrTirfFiXV");
        comment3.setText("This is an okay university!");
        comments.add(comment3);

        User bob = new User("Bob");
        bob.setId("-mXyLdjKWUNEMNslvUPWu");
        bob.setName("Bob");
        bob.setTours(Collections.singletonList("-rmtSKxBkNkkeWXOdkgGr"));
        users.add(bob);

        User sam = new User("Sam");
        sam.setId("-oTPklbIgOHaroQvAsOJT");
        sam.setName("Sam");
        sam.setTours(Arrays.asList("-jrFHNufNMxrgaSKydVQW", "-TZLeLsitKXOGjhFpPuVz"));
        users.add(sam);

        User alex = new User("Alex");
        alex.setId("-oMHqmNoDIECTxzCiSVsO");
        alex.setName("Alex");
        alex.setTours(new ArrayList<>());
        users.add(alex);

        User joe = new User("Joe");
        joe.setId("-QXUjhMkcfvlXWSctzZSd");
        joe.setName("Joe");
        joe.setTours(new ArrayList<>());
        users.add(joe);

        // tour 2
        Tour tour2 = new Tour("Grand Canyon");
        tour2.setId("-jrFHNufNMxrgaSKydVQW");
        tour2.setName("Grand Canyon");
        tour2.setLat(36.0544);
        tour2.setLon(-112.1401);
        tour2.setDescription("This is the Grand Canyon.");
        tour2.setAuthor("-oTPklbIgOHaroQvAsOJT");
        tour2.setPictureFile("https://www.canyontours.com/wp-content/uploads/2015/01/grand-canyon-south-rim-tour-bright-angel-point.jpg");
        tour2.setRating(87);
        tour2.setNumVotes(20);
        tour2.setPlaces(new ArrayList<>());
        tour2.setComments(new ArrayList<>());
        tours.add(tour2);

        // tour 3
        Tour tour3 = new Tour("Niagara Falls");
        tour3.setId("-TZLeLsitKXOGjhFpPuVz");
        tour3.setName("Niagara Falls");
        tour3.setLat(43.0962);
        tour3.setLon(-79.0377);
        tour3.setDescription("This is Niagara Falls.");
        tour3.setAuthor("-oTPklbIgOHaroQvAsOJT");
        tour3.setPictureFile("http://www.niagarafallslive.com/wp-content/uploads/2013/08/american-fall-night-1.jpg");
        tour3.setRating(97);
        tour3.setNumVotes(20);
        tour3.setPlaces(new ArrayList<>());
        tour3.setComments(new ArrayList<>());
        tours.add(tour3);

    }

    @SuppressWarnings("unused")
    public static List<Comment> getComments() {
        return comments;
    }

    @SuppressWarnings("unused")
    public static List<Place> getPlaces() {
        return places;
    }

    @SuppressWarnings("unused")
    public static List<Tour> getTours() {
        return tours;
    }

    @SuppressWarnings("unused")
    public static List<User> getUsers() {
        return users;
    }

    // call this if you want your tours to have images
    @SuppressWarnings("unused")
    public static void generateTourImages(Context context) {
        Bitmap bitmapGrandCanyon = BitmapFactory.decodeStream(context.getResources().openRawResource(R.raw.grand_canyon));
        Bitmap bitmapNiagaraFalls = BitmapFactory.decodeStream(context.getResources().openRawResource(R.raw.niagara_falls));
        Bitmap bitmapUMD = BitmapFactory.decodeStream(context.getResources().openRawResource(R.raw.umd));

        String path;
        path = Utils.putImageToInternalStorage(context, bitmapUMD, "images", "umd.jpg");
        tours.get(0).setPictureFile(path);
        path = Utils.putImageToInternalStorage(context, bitmapGrandCanyon, "images", "grand_canyon.jpg");
        tours.get(1).setPictureFile(path);
        path = Utils.putImageToInternalStorage(context, bitmapNiagaraFalls, "images", "niagara_falls.jpg");
        tours.get(2).setPictureFile(path);
    }

}
