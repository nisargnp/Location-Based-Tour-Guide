package edu.umd.cs.cmsc436.location_basedtourguide;

import android.app.Activity;
import android.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.VideoView;

public class VideoFragment extends Fragment {
    VideoView videoView;
    MediaController mediaController;
    Activity host;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        host = getActivity();
        videoView = host.findViewById(R.id.videoview);
        mediaController = new MediaController(host);
        mediaController.setMediaPlayer(videoView);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_video, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        initializePlayer();
    }

    @Override
    public void onStop() {
        super.onStop();
        releasePlayer();
    }
    // For finding the video file
    // Code from this tutorial:
    //https://google-developer-training.gitbooks.io/android-developer-advanced-course-practicals/content/unit-5-advanced-graphics-and-views/lesson-13-media/13-1-p-playing-video-with-videoview/13-1-p-playing-video-with-videoview.html
    private Uri getMedia(String mediaName) {
        return Uri.parse("android.resource://" + host.getPackageName() +
                "/raw/" + mediaName);
    }

    private void initializePlayer() {
        Uri videoUri = getMedia("NAME");
        videoView.setVideoURI(videoUri);
        videoView.start();
    }

    private void releasePlayer() {
        videoView.stopPlayback();
    }
}
