package edu.umd.cs.cmsc436.location_basedtourguide.Data.DataGenerator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import edu.umd.cs.cmsc436.location_basedtourguide.Firebase.DTO.Comment;
import edu.umd.cs.cmsc436.location_basedtourguide.Firebase.DTO.Place;
import edu.umd.cs.cmsc436.location_basedtourguide.Firebase.DTO.Tour;
import edu.umd.cs.cmsc436.location_basedtourguide.Firebase.DTO.User;
import edu.umd.cs.cmsc436.location_basedtourguide.Firebase.Utils.FirebaseUtils;

@SuppressWarnings("unused")
public final class DataGenerator {

    private DataGenerator() {}

    private static List<Comment> comments;
    private static List<Place> places;
    private static List<Tour> tours;
    private static List<User> users;

    public static void generateDataLocal() {

        comments = new ArrayList<>();
        places = new ArrayList<>();
        tours = new ArrayList<>();
        users = new ArrayList<>();

        // tour 1
        Tour tour1 =  new Tour();
        tour1.setId("-rmtSKxBkNkkeWXOdkgGr");
        tour1.setName("UMD");
        tour1.setLat(38.9869);
        tour1.setLon(-76.9426);
        tour1.setDescription("This is University of Maryland, College Park. It is the largest school in Maryland.");
        tour1.setAuthor("-mXyLdjKWUNEMNslvUPWu");
        tour1.setPictureFile("https://firebasestorage.googleapis.com/v0/b/location-based-tour-guid-7c2b9.appspot.com/o/sample_data%2Fimage%2Fimage_umd.jpg?alt=media&token=f9e9725d-9206-45bf-a81f-776398ae595d");
        tour1.setRating(77);
        tour1.setNumVotes(20);
        tour1.setPlaces(Arrays.asList("-vQLJaqfPfLJgObaJpUst", "-XCjumRBOpQaIAISygJMp", "-YSbepfiuBlDRajJUwYpB", "-mWYsAGcLRjeJBCzlDdxd"));
        tour1.setComments(Arrays.asList("-UzlvCqPYYlHDNVdCtGAw", "-cARNyPaVoMDmlwEtCsxW", "-MuAOmNFRTOPrTirfFiXV"));
        tours.add(tour1);

        Place terrapinRow = new Place();
        terrapinRow.setId("-vQLJaqfPfLJgObaJpUst");
        terrapinRow.setName("Terrapin Row");
        terrapinRow.setDescription("Students live here. Located directly across the street from the " +
                "South Campus Commons and the Washington Quad, Terrapin Row offers its residents ultra-modern " +
                "mid-rise apartments steps away from UMD. The center of campus including the McKeldin Library " +
                "are just a few minute walk from Terrapin Row. Come live here and experience your college life " +
                "in style!");
        terrapinRow.setLat(38.980367);
        terrapinRow.setLon(-76.942366);
        terrapinRow.setPictureFile("https://firebasestorage.googleapis.com/v0/b/location-based-tour-guid-7c2b9.appspot.com/o/sample_data%2Fimage%2Fimage_trow.jpeg?alt=media&token=40ae0ace-bd8c-4ead-bf6a-68f0bde28cdf");
        terrapinRow.setAudioFile("https://firebasestorage.googleapis.com/v0/b/location-based-tour-guid-7c2b9.appspot.com/o/sample_data%2Faudio%2Faudio_trow.wav?alt=media&token=add040f2-76f9-4142-a814-333b5a188b63");
        terrapinRow.setVideoFile("https://firebasestorage.googleapis.com/v0/b/location-based-tour-guid-7c2b9.appspot.com/o/sample_data%2Fvideo%2Fvideo_trow.mp4?alt=media&token=ed362870-633d-498f-baac-a0b08f9b863d");
        places.add(terrapinRow);

        Place CSIC = new Place();
        CSIC.setId("-XCjumRBOpQaIAISygJMp");
        CSIC.setName("Computer Science Instructional Center");
        CSIC.setDescription("Students study here.");
        CSIC.setLat(38.990085);
        CSIC.setLon(-76.936182);
        CSIC.setPictureFile("https://firebasestorage.googleapis.com/v0/b/location-based-tour-guid-7c2b9.appspot.com/o/sample_data%2Fimage%2Fimage_csic.jpg?alt=media&token=009b65ac-43f7-4d0d-ade0-0793fa56fe2b");
        CSIC.setAudioFile("https://firebasestorage.googleapis.com/v0/b/location-based-tour-guid-7c2b9.appspot.com/o/sample_data%2Faudio%2Faudio_csic.wav?alt=media&token=2e88ba13-91ac-42e7-bb21-346d7127d3cf");
        CSIC.setVideoFile("https://firebasestorage.googleapis.com/v0/b/location-based-tour-guid-7c2b9.appspot.com/o/sample_data%2Fvideo%2Fvideo_csic.mp4?alt=media&token=56fa41e6-b098-4506-bbc5-bec6465f2b52");
        places.add(CSIC);

        Place stamp = new Place();
        stamp.setId("-YSbepfiuBlDRajJUwYpB");
        stamp.setName("Stamp Student Union");
        stamp.setDescription("Students eat here.");
        stamp.setLat(38.987881);
        stamp.setLon(-76.944855);
        stamp.setPictureFile("https://firebasestorage.googleapis.com/v0/b/location-based-tour-guid-7c2b9.appspot.com/o/sample_data%2Fimage%2Fimage_stamp.jpg?alt=media&token=3afe8d69-3ddb-4074-befe-a53783c0198a");
        stamp.setAudioFile("https://firebasestorage.googleapis.com/v0/b/location-based-tour-guid-7c2b9.appspot.com/o/sample_data%2Faudio%2Faudio_stamp.wav?alt=media&token=2c60764a-67be-4769-a491-97df7882dee9");
        stamp.setVideoFile("");
        places.add(stamp);

        Place eppley = new Place();
        eppley.setId("-mWYsAGcLRjeJBCzlDdxd");
        eppley.setName("Eppley Recreational Center");
        eppley.setDescription("Students exercise here.");
        eppley.setLat(38.993635);
        eppley.setLon(-76.945155);
        eppley.setPictureFile("https://firebasestorage.googleapis.com/v0/b/location-based-tour-guid-7c2b9.appspot.com/o/sample_data%2Fimage%2Fimage_eppley.JPG?alt=media&token=b5fffb90-2da6-40a8-a0ba-c2ef2e9cbe12");
        eppley.setAudioFile("");
        eppley.setVideoFile("https://firebasestorage.googleapis.com/v0/b/location-based-tour-guid-7c2b9.appspot.com/o/sample_data%2Fvideo%2Fvideo_eppley.mp4?alt=media&token=227acf7a-001e-4222-8b64-d9006fac8346");
        places.add(eppley);

        Comment comment1 = new Comment();
        comment1.setId("-UzlvCqPYYlHDNVdCtGAw");
        comment1.setAuthor("-oTPklbIgOHaroQvAsOJT");
        comment1.setText("This is an amazing university!");
        comments.add(comment1);

        Comment comment2 = new Comment();
        comment2.setId("-cARNyPaVoMDmlwEtCsxW");
        comment2.setAuthor("-oMHqmNoDIECTxzCiSVsO");
        comment2.setText("This is an good university!");
        comments.add(comment2);

        Comment comment3 = new Comment();
        comment3.setId("-MuAOmNFRTOPrTirfFiXV");
        comment3.setAuthor("-QXUjhMkcfvlXWSctzZSd");
        comment3.setText("This is an okay university!");
        comments.add(comment3);

        User bob = new User();
        bob.setId("-mXyLdjKWUNEMNslvUPWu");
        bob.setName("Bob");
        bob.setTours(Collections.singletonList("-rmtSKxBkNkkeWXOdkgGr"));
        users.add(bob);

        User sam = new User();
        sam.setId("-oTPklbIgOHaroQvAsOJT");
        sam.setName("Sam");
        sam.setTours(Arrays.asList("-jrFHNufNMxrgaSKydVQW", "-TZLeLsitKXOGjhFpPuVz"));
        users.add(sam);

        User alex = new User();
        alex.setId("-oMHqmNoDIECTxzCiSVsO");
        alex.setName("Alex");
        alex.setTours(new ArrayList<>());
        users.add(alex);

        User joe = new User();
        joe.setId("-QXUjhMkcfvlXWSctzZSd");
        joe.setName("Joe");
        joe.setTours(new ArrayList<>());
        users.add(joe);

        // tour 2
        Tour tour2 = new Tour();
        tour2.setId("-jrFHNufNMxrgaSKydVQW");
        tour2.setName("Grand Canyon");
        tour2.setLat(36.0544);
        tour2.setLon(-112.1401);
        tour2.setDescription("This is the Grand Canyon.");
        tour2.setAuthor("-oTPklbIgOHaroQvAsOJT");
        tour2.setPictureFile("https://firebasestorage.googleapis.com/v0/b/location-based-tour-guid-7c2b9.appspot.com/o/sample_data%2Fimage%2Fimage_grand_canyon.jpg?alt=media&token=7959c6df-aad5-46e4-8ed7-76651a8a3d11");
        tour2.setRating(87);
        tour2.setNumVotes(20);
        tour2.setPlaces(new ArrayList<>());
        tour2.setComments(new ArrayList<>());
        tours.add(tour2);

        // tour 3
        Tour tour3 = new Tour();
        tour3.setId("-TZLeLsitKXOGjhFpPuVz");
        tour3.setName("Niagara Falls");
        tour3.setLat(43.0962);
        tour3.setLon(-79.0377);
        tour3.setDescription("This is Niagara Falls.");
        tour3.setAuthor("-oTPklbIgOHaroQvAsOJT");
        tour3.setPictureFile("https://firebasestorage.googleapis.com/v0/b/location-based-tour-guid-7c2b9.appspot.com/o/sample_data%2Fimage%2Fimage_niagara.jpg?alt=media&token=87502432-8d31-47ff-ad3e-11fceb760c22");
        tour3.setRating(97);
        tour3.setNumVotes(20);
        tour3.setPlaces(new ArrayList<>());
        tour3.setComments(new ArrayList<>());
        tours.add(tour3);

    }

    public static void generateDataFirebase() {

        // tour 1
        Tour tour1 =  new Tour();
        tour1.setName("UMD");
        tour1.setLat(38.9869);
        tour1.setLon(-76.9426);
        tour1.setDescription("This is University of Maryland, College Park. It is the largest school in Maryland.");
        tour1.setPictureFile("https://firebasestorage.googleapis.com/v0/b/location-based-tour-guid-7c2b9.appspot.com/o/sample_data%2Fimage%2Fimage_umd.jpg?alt=media&token=f9e9725d-9206-45bf-a81f-776398ae595d");
        tour1.setRating(77);
        tour1.setNumVotes(20);

        Place terrapinRow = new Place();
        terrapinRow.setName("Terrapin Row");
        terrapinRow.setDescription("Students live here. Located directly across the street from the " +
                "South Campus Commons and the Washington Quad, Terrapin Row offers its residents ultra-modern " +
                "mid-rise apartments steps away from UMD. The center of campus including the McKeldin Library " +
                "are just a few minute walk from Terrapin Row. Come live here and experience your college life " +
                "in style!");
        terrapinRow.setLat(38.980367);
        terrapinRow.setLon(-76.942366);
        terrapinRow.setPictureFile("https://firebasestorage.googleapis.com/v0/b/location-based-tour-guid-7c2b9.appspot.com/o/sample_data%2Fimage%2Fimage_trow.jpeg?alt=media&token=40ae0ace-bd8c-4ead-bf6a-68f0bde28cdf");
        terrapinRow.setAudioFile("https://firebasestorage.googleapis.com/v0/b/location-based-tour-guid-7c2b9.appspot.com/o/sample_data%2Faudio%2Faudio_trow.wav?alt=media&token=add040f2-76f9-4142-a814-333b5a188b63");
        terrapinRow.setVideoFile("https://firebasestorage.googleapis.com/v0/b/location-based-tour-guid-7c2b9.appspot.com/o/sample_data%2Fvideo%2Fvideo_trow.mp4?alt=media&token=ed362870-633d-498f-baac-a0b08f9b863d");

        Place CSIC = new Place();
        CSIC.setName("Computer Science Instructional Center");
        CSIC.setDescription("Students study here.");
        CSIC.setLat(38.990085);
        CSIC.setLon(-76.936182);
        CSIC.setPictureFile("https://firebasestorage.googleapis.com/v0/b/location-based-tour-guid-7c2b9.appspot.com/o/sample_data%2Fimage%2Fimage_csic.jpg?alt=media&token=009b65ac-43f7-4d0d-ade0-0793fa56fe2b");
        CSIC.setAudioFile("https://firebasestorage.googleapis.com/v0/b/location-based-tour-guid-7c2b9.appspot.com/o/sample_data%2Faudio%2Faudio_csic.wav?alt=media&token=2e88ba13-91ac-42e7-bb21-346d7127d3cf");
        CSIC.setVideoFile("https://firebasestorage.googleapis.com/v0/b/location-based-tour-guid-7c2b9.appspot.com/o/sample_data%2Fvideo%2Fvideo_csic.mp4?alt=media&token=56fa41e6-b098-4506-bbc5-bec6465f2b52");

        Place stamp = new Place();
        stamp.setName("Stamp Student Union");
        stamp.setDescription("Students eat here.");
        stamp.setLat(38.987881);
        stamp.setLon(-76.944855);
        stamp.setPictureFile("https://firebasestorage.googleapis.com/v0/b/location-based-tour-guid-7c2b9.appspot.com/o/sample_data%2Fimage%2Fimage_stamp.jpg?alt=media&token=3afe8d69-3ddb-4074-befe-a53783c0198a");
        stamp.setAudioFile("https://firebasestorage.googleapis.com/v0/b/location-based-tour-guid-7c2b9.appspot.com/o/sample_data%2Faudio%2Faudio_stamp.wav?alt=media&token=2c60764a-67be-4769-a491-97df7882dee9");
        stamp.setVideoFile("");

        Place eppley = new Place();
        eppley.setName("Eppley Recreational Center");
        eppley.setDescription("Students exercise here.");
        eppley.setLat(38.993635);
        eppley.setLon(-76.945155);
        eppley.setPictureFile("https://firebasestorage.googleapis.com/v0/b/location-based-tour-guid-7c2b9.appspot.com/o/sample_data%2Fimage%2Fimage_eppley.JPG?alt=media&token=b5fffb90-2da6-40a8-a0ba-c2ef2e9cbe12");
        eppley.setAudioFile("");
        eppley.setVideoFile("https://firebasestorage.googleapis.com/v0/b/location-based-tour-guid-7c2b9.appspot.com/o/sample_data%2Fvideo%2Fvideo_eppley.mp4?alt=media&token=227acf7a-001e-4222-8b64-d9006fac8346");

        Comment comment1 = new Comment();
        comment1.setText("This is an amazing university!");

        Comment comment2 = new Comment();
        comment2.setText("This is an good university!");

        Comment comment3 = new Comment();
        comment3.setText("This is an okay university!");

        User bob = new User();
        bob.setName("Bob");

        User sam = new User();
        sam.setName("Sam");

        User alex = new User();
        alex.setName("Alex");

        User joe = new User();
        joe.setName("Joe");

        // tour 2
        Tour tour2 = new Tour();
        tour2.setName("Grand Canyon");
        tour2.setLat(36.0544);
        tour2.setLon(-112.1401);
        tour2.setDescription("This is the Grand Canyon.");
        tour2.setPictureFile("https://firebasestorage.googleapis.com/v0/b/location-based-tour-guid-7c2b9.appspot.com/o/sample_data%2Fimage%2Fimage_grand_canyon.jpg?alt=media&token=7959c6df-aad5-46e4-8ed7-76651a8a3d11");
        tour2.setRating(87);
        tour2.setNumVotes(20);

        // tour 3
        Tour tour3 = new Tour();
        tour3.setName("Niagara Falls");
        tour3.setLat(43.0962);
        tour3.setLon(-79.0377);
        tour3.setDescription("This is Niagara Falls.");
        tour3.setPictureFile("https://firebasestorage.googleapis.com/v0/b/location-based-tour-guid-7c2b9.appspot.com/o/sample_data%2Fimage%2Fimage_niagara.jpg?alt=media&token=87502432-8d31-47ff-ad3e-11fceb760c22");
        tour3.setRating(97);
        tour3.setNumVotes(20);

        // clear firebase before starting upload
        FirebaseUtils.clearFirebaseDB();

        // upload users
        String bobID = FirebaseUtils.uploadToFirebaseRaw(null, bob);
        String samID = FirebaseUtils.uploadToFirebaseRaw(null, sam);
        String alexID = FirebaseUtils.uploadToFirebaseRaw(null, alex);
        String joeID = FirebaseUtils.uploadToFirebaseRaw(null, joe);

        // upload comments
        comment1.setAuthor(samID);
        String comment1ID = FirebaseUtils.uploadToFirebaseRaw(null, comment1);
        comment2.setAuthor(alexID);
        String comment2ID = FirebaseUtils.uploadToFirebaseRaw(null, comment2);
        comment3.setAuthor(joeID);
        String comment3ID = FirebaseUtils.uploadToFirebaseRaw(null, comment3);

        // upload places
        String trowID = FirebaseUtils.uploadToFirebaseRaw(null, terrapinRow);
        String csicID = FirebaseUtils.uploadToFirebaseRaw(null, CSIC);
        String stampID = FirebaseUtils.uploadToFirebaseRaw(null, stamp);
        String epplyID = FirebaseUtils.uploadToFirebaseRaw(null, eppley);

        // upload tours
        tour1.setAuthor(bobID);
        tour1.setPlaces(Arrays.asList(trowID, csicID, stampID, epplyID));
        tour1.setComments(Arrays.asList(comment1ID, comment2ID, comment3ID));
        String tour1ID = FirebaseUtils.uploadToFirebaseRaw(null, tour1);

        tour2.setAuthor(samID);
        String tour2ID = FirebaseUtils.uploadToFirebaseRaw(null, tour2);

        tour3.setAuthor(samID);
        String tour3ID = FirebaseUtils.uploadToFirebaseRaw(null, tour3);

    }

    public static List<Comment> getComments() {
        return comments;
    }

    public static List<Place> getPlaces() {
        return places;
    }

    public static List<Tour> getTours() {
        return tours;
    }

    public static List<User> getUsers() {
        return users;
    }

}
