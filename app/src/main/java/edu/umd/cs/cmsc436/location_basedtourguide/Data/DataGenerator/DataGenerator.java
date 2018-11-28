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

//    public static void generateDataLocal() {
//
//        comments = new ArrayList<>();
//        places = new ArrayList<>();
//        tours = new ArrayList<>();
//        users = new ArrayList<>();
//
//        // tour 1
//        Tour tour1 =  new Tour();
//        tour1.setId("-rmtSKxBkNkkeWXOdkgGr");
//        tour1.setName("UMD");
//        tour1.setLat(38.9869);
//        tour1.setLon(-76.9426);
//        tour1.setDescription("This is University of Maryland, College Park. It is the largest school in Maryland.");
//        tour1.setAuthor("-mXyLdjKWUNEMNslvUPWu");
//        tour1.setPictureFile("https://firebasestorage.googleapis.com/v0/b/location-based-tour-guid-7c2b9.appspot.com/o/sample_data%2Fimage%2Fimage_umd.jpg?alt=media&token=f9e9725d-9206-45bf-a81f-776398ae595d");
//        tour1.setRating(77);
//        tour1.setNumVotes(20);
//        tour1.setPlaces(Arrays.asList("-vQLJaqfPfLJgObaJpUst", "-XCjumRBOpQaIAISygJMp", "-YSbepfiuBlDRajJUwYpB", "-mWYsAGcLRjeJBCzlDdxd"));
//        tour1.setComments(Arrays.asList("-UzlvCqPYYlHDNVdCtGAw", "-cARNyPaVoMDmlwEtCsxW", "-MuAOmNFRTOPrTirfFiXV"));
//        tours.add(tour1);
//
//        Place terrapinRow = new Place();
//        terrapinRow.setId("-vQLJaqfPfLJgObaJpUst");
//        terrapinRow.setName("Terrapin Row");
//        terrapinRow.setDescription("Students live here. Located directly across the street from the " +
//                "South Campus Commons and the Washington Quad, Terrapin Row offers its residents ultra-modern " +
//                "mid-rise apartments steps away from UMD. The center of campus including the McKeldin Library " +
//                "are just a few minute walk from Terrapin Row. Come live here and experience your college life " +
//                "in style!");
//        terrapinRow.setLat(38.980367);
//        terrapinRow.setLon(-76.942366);
//        terrapinRow.setPictureFile("https://firebasestorage.googleapis.com/v0/b/location-based-tour-guid-7c2b9.appspot.com/o/sample_data%2Fimage%2Fimage_trow.jpeg?alt=media&token=40ae0ace-bd8c-4ead-bf6a-68f0bde28cdf");
//        terrapinRow.setAudioFile("https://firebasestorage.googleapis.com/v0/b/location-based-tour-guid-7c2b9.appspot.com/o/sample_data%2Faudio%2Faudio_trow.wav?alt=media&token=add040f2-76f9-4142-a814-333b5a188b63");
//        terrapinRow.setVideoFile("https://firebasestorage.googleapis.com/v0/b/location-based-tour-guid-7c2b9.appspot.com/o/sample_data%2Fvideo%2Fvideo_trow.mp4?alt=media&token=ed362870-633d-498f-baac-a0b08f9b863d");
//        places.add(terrapinRow);
//
//        Place CSIC = new Place();
//        CSIC.setId("-XCjumRBOpQaIAISygJMp");
//        CSIC.setName("Computer Science Instructional Center");
//        CSIC.setDescription("Students study here.");
//        CSIC.setLat(38.990085);
//        CSIC.setLon(-76.936182);
//        CSIC.setPictureFile("https://firebasestorage.googleapis.com/v0/b/location-based-tour-guid-7c2b9.appspot.com/o/sample_data%2Fimage%2Fimage_csic.jpg?alt=media&token=009b65ac-43f7-4d0d-ade0-0793fa56fe2b");
//        CSIC.setAudioFile("https://firebasestorage.googleapis.com/v0/b/location-based-tour-guid-7c2b9.appspot.com/o/sample_data%2Faudio%2Faudio_csic.wav?alt=media&token=2e88ba13-91ac-42e7-bb21-346d7127d3cf");
//        CSIC.setVideoFile("https://firebasestorage.googleapis.com/v0/b/location-based-tour-guid-7c2b9.appspot.com/o/sample_data%2Fvideo%2Fvideo_csic.mp4?alt=media&token=56fa41e6-b098-4506-bbc5-bec6465f2b52");
//        places.add(CSIC);
//
//        Place stamp = new Place();
//        stamp.setId("-YSbepfiuBlDRajJUwYpB");
//        stamp.setName("Stamp Student Union");
//        stamp.setDescription("Students eat here.");
//        stamp.setLat(38.987881);
//        stamp.setLon(-76.944855);
//        stamp.setPictureFile("https://firebasestorage.googleapis.com/v0/b/location-based-tour-guid-7c2b9.appspot.com/o/sample_data%2Fimage%2Fimage_stamp.jpg?alt=media&token=3afe8d69-3ddb-4074-befe-a53783c0198a");
//        stamp.setAudioFile("https://firebasestorage.googleapis.com/v0/b/location-based-tour-guid-7c2b9.appspot.com/o/sample_data%2Faudio%2Faudio_stamp.wav?alt=media&token=2c60764a-67be-4769-a491-97df7882dee9");
//        stamp.setVideoFile("");
//        places.add(stamp);
//
//        Place eppley = new Place();
//        eppley.setId("-mWYsAGcLRjeJBCzlDdxd");
//        eppley.setName("Eppley Recreational Center");
//        eppley.setDescription("Students exercise here.");
//        eppley.setLat(38.993635);
//        eppley.setLon(-76.945155);
//        eppley.setPictureFile("https://firebasestorage.googleapis.com/v0/b/location-based-tour-guid-7c2b9.appspot.com/o/sample_data%2Fimage%2Fimage_eppley.JPG?alt=media&token=b5fffb90-2da6-40a8-a0ba-c2ef2e9cbe12");
//        eppley.setAudioFile("");
//        eppley.setVideoFile("https://firebasestorage.googleapis.com/v0/b/location-based-tour-guid-7c2b9.appspot.com/o/sample_data%2Fvideo%2Fvideo_eppley.mp4?alt=media&token=227acf7a-001e-4222-8b64-d9006fac8346");
//        places.add(eppley);
//
//        Comment comment1 = new Comment();
//        comment1.setId("-UzlvCqPYYlHDNVdCtGAw");
//        comment1.setAuthor("-oTPklbIgOHaroQvAsOJT");
//        comment1.setText("This is an amazing university!");
//        comments.add(comment1);
//
//        Comment comment2 = new Comment();
//        comment2.setId("-cARNyPaVoMDmlwEtCsxW");
//        comment2.setAuthor("-oMHqmNoDIECTxzCiSVsO");
//        comment2.setText("This is an good university!");
//        comments.add(comment2);
//
//        Comment comment3 = new Comment();
//        comment3.setId("-MuAOmNFRTOPrTirfFiXV");
//        comment3.setAuthor("-QXUjhMkcfvlXWSctzZSd");
//        comment3.setText("This is an okay university!");
//        comments.add(comment3);
//
//        User bob = new User();
//        bob.setId("-mXyLdjKWUNEMNslvUPWu");
//        bob.setName("Bob");
//        bob.setTours(Collections.singletonList("-rmtSKxBkNkkeWXOdkgGr"));
//        users.add(bob);
//
//        User sam = new User();
//        sam.setId("-oTPklbIgOHaroQvAsOJT");
//        sam.setName("Sam");
//        sam.setTours(Arrays.asList("-jrFHNufNMxrgaSKydVQW", "-TZLeLsitKXOGjhFpPuVz"));
//        users.add(sam);
//
//        User alex = new User();
//        alex.setId("-oMHqmNoDIECTxzCiSVsO");
//        alex.setName("Alex");
//        alex.setTours(new ArrayList<>());
//        users.add(alex);
//
//        User joe = new User();
//        joe.setId("-QXUjhMkcfvlXWSctzZSd");
//        joe.setName("Joe");
//        joe.setTours(new ArrayList<>());
//        users.add(joe);
//
//        // tour 2
//        Tour tour2 = new Tour();
//        tour2.setId("-jrFHNufNMxrgaSKydVQW");
//        tour2.setName("Grand Canyon");
//        tour2.setLat(36.0544);
//        tour2.setLon(-112.1401);
//        tour2.setDescription("This is the Grand Canyon.");
//        tour2.setAuthor("-oTPklbIgOHaroQvAsOJT");
//        tour2.setPictureFile("https://firebasestorage.googleapis.com/v0/b/location-based-tour-guid-7c2b9.appspot.com/o/sample_data%2Fimage%2Fimage_grand_canyon.jpg?alt=media&token=7959c6df-aad5-46e4-8ed7-76651a8a3d11");
//        tour2.setRating(87);
//        tour2.setNumVotes(20);
//        tour2.setPlaces(new ArrayList<>());
//        tour2.setComments(new ArrayList<>());
//        tours.add(tour2);
//
//        // tour 3
//        Tour tour3 = new Tour();
//        tour3.setId("-TZLeLsitKXOGjhFpPuVz");
//        tour3.setName("Niagara Falls");
//        tour3.setLat(43.0962);
//        tour3.setLon(-79.0377);
//        tour3.setDescription("This is Niagara Falls.");
//        tour3.setAuthor("-oTPklbIgOHaroQvAsOJT");
//        tour3.setPictureFile("https://firebasestorage.googleapis.com/v0/b/location-based-tour-guid-7c2b9.appspot.com/o/sample_data%2Fimage%2Fimage_niagara.jpg?alt=media&token=87502432-8d31-47ff-ad3e-11fceb760c22");
//        tour3.setRating(97);
//        tour3.setNumVotes(20);
//        tour3.setPlaces(new ArrayList<>());
//        tour3.setComments(new ArrayList<>());
//        tours.add(tour3);
//
//    }

    public static void generateDataFirebase() {

        // tour 1
        Tour tour1 =  new Tour();
        tour1.setName("UMD");
        tour1.setLat(38.9869);
        tour1.setLon(-76.9426);
        tour1.setDescription("This is University of Maryland, College Park. It is the largest school in Maryland.");
        tour1.setPictureFile("https://firebasestorage.googleapis.com/v0/b/lbtgproject123.appspot.com/o/sampleData%2Fimage_umd.jpg?alt=media&token=10919bde-d915-4312-ad3f-6736a7881228");
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
        terrapinRow.setPictureFile("https://firebasestorage.googleapis.com/v0/b/lbtgproject123.appspot.com/o/sampleData%2Fimage_trow.jpeg?alt=media&token=3a82b2ad-efbe-48ed-8d75-399cf454b2d4");
        terrapinRow.setAudioFile("https://firebasestorage.googleapis.com/v0/b/lbtgproject123.appspot.com/o/sampleData%2Faudio_trow.wav?alt=media&token=5a21ae88-099e-4758-bc57-f48eb7a542af");
        terrapinRow.setVideoFile("https://firebasestorage.googleapis.com/v0/b/lbtgproject123.appspot.com/o/sampleData%2Fvideo_trow.mp4?alt=media&token=d34e3719-a4f5-47a6-9720-d496163d60dc");

        Place CSIC = new Place();
        CSIC.setName("Computer Science Instructional Center");
        CSIC.setDescription("Students study here.");
        CSIC.setLat(38.990085);
        CSIC.setLon(-76.936182);
        CSIC.setPictureFile("https://firebasestorage.googleapis.com/v0/b/lbtgproject123.appspot.com/o/sampleData%2Fimage_csic.jpg?alt=media&token=4418c52e-a883-43e3-956d-a636058ccfca");
        CSIC.setAudioFile("https://firebasestorage.googleapis.com/v0/b/lbtgproject123.appspot.com/o/sampleData%2Faudio_csic.wav?alt=media&token=cd570980-237c-42e9-94a8-a0b32582f10a");
        CSIC.setVideoFile("https://firebasestorage.googleapis.com/v0/b/lbtgproject123.appspot.com/o/sampleData%2Fvideo_csic.mp4?alt=media&token=421d3cdb-9558-4fad-b340-e90246599394");

        Place stamp = new Place();
        stamp.setName("Stamp Student Union");
        stamp.setDescription("Students eat here.");
        stamp.setLat(38.987612);
        stamp.setLon(-76.944860);
        stamp.setPictureFile("https://firebasestorage.googleapis.com/v0/b/lbtgproject123.appspot.com/o/sampleData%2Fimage_stamp.jpg?alt=media&token=0f811fca-b2b5-4238-bcbb-3b5ad7800447");
        stamp.setAudioFile("https://firebasestorage.googleapis.com/v0/b/lbtgproject123.appspot.com/o/sampleData%2Faudio_stamp.wav?alt=media&token=62c4adc7-b0d9-48f1-90b4-f3eb255a8f92");
        stamp.setVideoFile("");

        Place eppley = new Place();
        eppley.setName("Eppley Recreational Center");
        eppley.setDescription("Students exercise here.");
        eppley.setLat(38.993635);
        eppley.setLon(-76.945155);
        eppley.setPictureFile("https://firebasestorage.googleapis.com/v0/b/lbtgproject123.appspot.com/o/sampleData%2Fimage_eppley.JPG?alt=media&token=fca2b3d4-0953-4c1e-9060-dd9095d0a259");
        eppley.setAudioFile("");
        eppley.setVideoFile("https://firebasestorage.googleapis.com/v0/b/lbtgproject123.appspot.com/o/sampleData%2Fvideo_eppley.mp4?alt=media&token=b5943722-23cb-4a8f-98c2-b96df6a1ad1b");

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
        tour2.setPictureFile("https://firebasestorage.googleapis.com/v0/b/lbtgproject123.appspot.com/o/sampleData%2Fimage_grand_canyon.jpg?alt=media&token=18dda9b7-2be9-41c1-a6af-716fad1357b5");
        tour2.setRating(87);
        tour2.setNumVotes(20);

        // tour 3
        Tour tour3 = new Tour();
        tour3.setName("Niagara Falls");
        tour3.setLat(43.0962);
        tour3.setLon(-79.0377);
        tour3.setDescription("This is Niagara Falls.");
        tour3.setPictureFile("https://firebasestorage.googleapis.com/v0/b/lbtgproject123.appspot.com/o/sampleData%2Fimage_niagara.jpg?alt=media&token=165ddf42-d839-40ed-b6f8-8c065edce7e9");
        tour3.setRating(97);
        tour3.setNumVotes(20);

        // tour 4
        Tour tour4 =  new Tour();
        tour4.setName("Mckeldin Mall Tour");
        tour4.setLat(38.9860);
        tour4.setLon(-76.9428);
        tour4.setDescription("The Mckeldin mall is nice and tight for a smooth demo.");
        tour4.setPictureFile("https://firebasestorage.googleapis.com/v0/b/lbtgproject123.appspot.com/o/sampleData%2F350px-Mckeldin_Mall.jpg?alt=media&token=25da531d-88e1-4714-9c43-36b4dd25093a");
        tour4.setRating(10);
        tour4.setNumVotes(15);

        Place esj = new Place();
        esj.setName("Edward Saint John Learning Center");
        esj.setDescription("This is a pretty new building with big classes for big knowledge.");
        esj.setLat(38.986664);
        esj.setLon(-76.941921);
        esj.setPictureFile("https://firebasestorage.googleapis.com/v0/b/lbtgproject123.appspot.com/o/sampleData%2Fumd_esj_building.jpg?alt=media&token=8acef363-c8c2-46ae-bc58-e59a1bc05a09");
        esj.setAudioFile("");
        esj.setVideoFile("");

        Place hornbakePlaza = new Place();
        hornbakePlaza.setName("Hornbake Plaza");
        hornbakePlaza.setDescription("A small place that I pass by on my way to nap time in astro");
        hornbakePlaza.setLat(38.988075);
        hornbakePlaza.setLon(-76.942614);
        hornbakePlaza.setPictureFile("https://firebasestorage.googleapis.com/v0/b/lbtgproject123.appspot.com/o/sampleData%2Fumd_hornbake_plaza.jpg?alt=media&token=f2f5ac9f-25ab-4b57-84b0-e2ea5d8d1682");
        hornbakePlaza.setAudioFile("");
        hornbakePlaza.setVideoFile("");

        Place mckeldin = new Place();
        mckeldin.setName("Mckeldin Library");
        mckeldin.setDescription("It's a pretty nice place to study. But there is a high risk of getting flashed.");
        mckeldin.setLat(38.985917);
        mckeldin.setLon(-76.944757);
        mckeldin.setPictureFile("https://firebasestorage.googleapis.com/v0/b/lbtgproject123.appspot.com/o/sampleData%2Fmckeldin_library.jpg?alt=media&token=546cedd9-c8fa-429b-ba56-7206df0a51f0");
        mckeldin.setAudioFile("");
        mckeldin.setVideoFile("");

        Comment mallComment1 = new Comment();
        mallComment1.setText("Pretty good tour... But I finished it in 5 mintes");

        Comment mallComment2 = new Comment();
        mallComment2.setText("What a great experience! Small and quick enough for my first time!");

        Comment mallComment3 = new Comment();
        mallComment3.setText("Trash.");

        User aporter = new User();
        aporter.setName("aporter");

        // clear firebase before starting upload
        FirebaseUtils.clearFirebaseDB();

        // upload users
        String bobID = FirebaseUtils.uploadToFirebaseRaw(null, bob);
        String samID = FirebaseUtils.uploadToFirebaseRaw(null, sam);
        String alexID = FirebaseUtils.uploadToFirebaseRaw(null, alex);
        String joeID = FirebaseUtils.uploadToFirebaseRaw(null, joe);
        String aporterID = FirebaseUtils.uploadToFirebaseRaw(null, aporter);

        // upload comments
        comment1.setAuthor(samID);
        String comment1ID = FirebaseUtils.uploadToFirebaseRaw(null, comment1);
        comment2.setAuthor(alexID);
        String comment2ID = FirebaseUtils.uploadToFirebaseRaw(null, comment2);
        comment3.setAuthor(joeID);
        String comment3ID = FirebaseUtils.uploadToFirebaseRaw(null, comment3);
        mallComment1.setAuthor(samID);
        String mallComment1ID = FirebaseUtils.uploadToFirebaseRaw(null, mallComment1);
        mallComment2.setAuthor(alexID);
        String mallComment2ID = FirebaseUtils.uploadToFirebaseRaw(null, mallComment2);
        mallComment3.setAuthor(aporterID);
        String mallComment3ID = FirebaseUtils.uploadToFirebaseRaw(null, mallComment3);

        // upload places
        String trowID = FirebaseUtils.uploadToFirebaseRaw(null, terrapinRow);
        String csicID = FirebaseUtils.uploadToFirebaseRaw(null, CSIC);
        String stampID = FirebaseUtils.uploadToFirebaseRaw(null, stamp);
        String epplyID = FirebaseUtils.uploadToFirebaseRaw(null, eppley);
        String esjID = FirebaseUtils.uploadToFirebaseRaw(null, esj);
        String hornbakeID = FirebaseUtils.uploadToFirebaseRaw(null, hornbakePlaza);
        String mckeldinID = FirebaseUtils.uploadToFirebaseRaw(null, mckeldin);

        // upload tours
        tour1.setAuthor(bobID);
        tour1.setPlaces(Arrays.asList(csicID, trowID, stampID, epplyID));
        tour1.setComments(Arrays.asList(comment1ID, comment2ID, comment3ID));
        String tour1ID = FirebaseUtils.uploadToFirebaseRaw(null, tour1);

        tour2.setAuthor(samID);
        String tour2ID = FirebaseUtils.uploadToFirebaseRaw(null, tour2);

        tour3.setAuthor(samID);
        String tour3ID = FirebaseUtils.uploadToFirebaseRaw(null, tour3);

        tour4.setAuthor(joeID);
        tour4.setPlaces(Arrays.asList(esjID, hornbakeID, stampID, mckeldinID));
        tour4.setComments(Arrays.asList(mallComment1ID, mallComment2ID, mallComment3ID));
        String tour4ID = FirebaseUtils.uploadToFirebaseRaw(null, tour4);
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
