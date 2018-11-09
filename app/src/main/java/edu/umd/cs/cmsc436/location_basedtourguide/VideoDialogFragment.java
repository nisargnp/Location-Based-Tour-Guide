package edu.umd.cs.cmsc436.location_basedtourguide;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.MediaController;
import android.widget.VideoView;

/*
 * Based off of:
 * https://developer.android.com/guide/topics/ui/dialogs#java
 */
public class VideoDialogFragment extends DialogFragment {
    Button pause, play;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View v = inflater.inflate(R.layout.fragment_video, null);
        builder.setView(v);

        VideoView videoView = v.findViewById(R.id.videoview);


        Uri uri = Uri.parse("android.resource://"+getActivity().getPackageName()+"/"+R.raw.teapot);

        videoView.setVideoURI(uri);
        videoView.start();

        pause = v.findViewById(R.id.pause_vid);
        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (videoView.isPlaying())
                    videoView.pause();
            }
        });
        play = v.findViewById(R.id.play_vid);
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!videoView.isPlaying())
                    videoView.start();
            }
        });
        return builder.create();
    }
}
