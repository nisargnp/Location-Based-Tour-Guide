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
        tour1.setPictureFile("https://www.provost.umd.edu/images/umdimg10.jpg");
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
        terrapinRow.setPictureFile("https://img.offcampusimages.com/ksG-bVfAntpM3Y-0eCrDneMZGFw=/960x665/left/top/smart/images/ocgpt7bqm09amm4tieanigg8gw9rsk1nwh0tzenqzdm.jpeg");
        terrapinRow.setAudioFile("https://www.ee.columbia.edu/~dpwe/sounds/music/around_the_world-atc.wav");
        terrapinRow.setVideoFile("https://sample-videos.com/video123/mp4/720/big_buck_bunny_720p_1mb.mp4");
        places.add(terrapinRow);

        Place CSIC = new Place();
        CSIC.setId("-XCjumRBOpQaIAISygJMp");
        CSIC.setName("Computer Science Instructional Center");
        CSIC.setDescription("Students study here.");
        CSIC.setLat(38.990085);
        CSIC.setLon(-76.936182);
        CSIC.setPictureFile("http://www.cs.umd.edu/sites/default/files/csic.jpg");
        CSIC.setAudioFile("https://www.ee.columbia.edu/~dpwe/sounds/music/i_ran_so_far_away-flock_of_seagulls.wav");
        CSIC.setVideoFile("http://techslides.com/demos/sample-videos/small.mp4");
        places.add(CSIC);

        Place stamp = new Place();
        stamp.setId("-YSbepfiuBlDRajJUwYpB");
        stamp.setName("Stamp Student Union");
        stamp.setDescription("Students eat here.");
        stamp.setLat(38.987881);
        stamp.setLon(-76.944855);
        stamp.setPictureFile("http://4.bp.blogspot.com/-qR-DNRpZhT0/VD6V8aae_rI/AAAAAAAAAVw/Cvm7VGLhCAk/s1600/DSC_0544.jpg");
        stamp.setAudioFile("https://www.ee.columbia.edu/~dpwe/sounds/music/africa-toto.wav");
        stamp.setVideoFile("");
        places.add(stamp);

        Place eppley = new Place();
        eppley.setId("-mWYsAGcLRjeJBCzlDdxd");
        eppley.setName("Eppley Recreational Center");
        eppley.setDescription("Students exercise here.");
        eppley.setLat(38.993635);
        eppley.setLon(-76.945155);
        eppley.setPictureFile("http://1.bp.blogspot.com/-m6RNHUfcogg/UI1KzblHw1I/AAAAAAAAEDU/CMGB5h6hNLs/s1600/DSC00746.JPG");
        eppley.setAudioFile("");
        eppley.setVideoFile("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4");
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
        tour2.setPictureFile("https://www.nps.gov/grca/planyourvisit/images/0531fyp.jpg?maxwidth=650&autorotate=false");
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
        tour3.setPictureFile("http://www.niagarafallslive.com/wp-content/uploads/2013/08/american-fall-night-1.jpg");
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
        tour1.setPictureFile("https://www.provost.umd.edu/images/umdimg10.jpg");
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
        terrapinRow.setPictureFile("https://img.offcampusimages.com/ksG-bVfAntpM3Y-0eCrDneMZGFw=/960x665/left/top/smart/images/ocgpt7bqm09amm4tieanigg8gw9rsk1nwh0tzenqzdm.jpeg");
        terrapinRow.setAudioFile("https://www.ee.columbia.edu/~dpwe/sounds/music/around_the_world-atc.wav");
        terrapinRow.setVideoFile("https://sample-videos.com/video123/mp4/720/big_buck_bunny_720p_1mb.mp4");

        Place CSIC = new Place();
        CSIC.setName("Computer Science Instructional Center");
        CSIC.setDescription("Students study here.");
        CSIC.setLat(38.990085);
        CSIC.setLon(-76.936182);
        CSIC.setPictureFile("http://www.cs.umd.edu/sites/default/files/csic.jpg");
        CSIC.setAudioFile("https://www.ee.columbia.edu/~dpwe/sounds/music/i_ran_so_far_away-flock_of_seagulls.wav");
        CSIC.setVideoFile("http://techslides.com/demos/sample-videos/small.mp4");

        Place stamp = new Place();
        stamp.setName("Stamp Student Union");
        stamp.setDescription("Students eat here.");
        stamp.setLat(38.987881);
        stamp.setLon(-76.944855);
        stamp.setPictureFile("http://4.bp.blogspot.com/-qR-DNRpZhT0/VD6V8aae_rI/AAAAAAAAAVw/Cvm7VGLhCAk/s1600/DSC_0544.jpg");
        stamp.setAudioFile("https://www.ee.columbia.edu/~dpwe/sounds/music/africa-toto.wav");
        stamp.setVideoFile("");

        Place eppley = new Place();
        eppley.setName("Eppley Recreational Center");
        eppley.setDescription("Students exercise here.");
        eppley.setLat(38.993635);
        eppley.setLon(-76.945155);
        eppley.setPictureFile("http://1.bp.blogspot.com/-m6RNHUfcogg/UI1KzblHw1I/AAAAAAAAEDU/CMGB5h6hNLs/s1600/DSC00746.JPG");
        eppley.setAudioFile("");
        eppley.setVideoFile("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4");

        Comment comment1 = new Comment();
        comment1.setText("This is an amazing university!");

        Comment comment2 = new Comment();
        comment2.setText("This is an good university!");

        Comment comment3 = new Comment();
        comment3.setText("This is an okay university!");

        User bob = new User();
        bob.setName("Bob");
        bob.setTours(Collections.singletonList("-rmtSKxBkNkkeWXOdkgGr"));

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
        tour2.setPictureFile("https://www.nps.gov/grca/planyourvisit/images/0531fyp.jpg?maxwidth=650&autorotate=false");
        tour2.setRating(87);
        tour2.setNumVotes(20);

        // tour 3
        Tour tour3 = new Tour();
        tour3.setName("Niagara Falls");
        tour3.setLat(43.0962);
        tour3.setLon(-79.0377);
        tour3.setDescription("This is Niagara Falls.");
        tour3.setPictureFile("http://www.niagarafallslive.com/wp-content/uploads/2013/08/american-fall-night-1.jpg");
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
