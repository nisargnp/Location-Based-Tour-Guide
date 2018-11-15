package edu.umd.cs.cmsc436.location_basedtourguide.AudioVideo;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.VideoView;

import edu.umd.cs.cmsc436.location_basedtourguide.R;

/*
 * Based off of:
 * https://developer.android.com/guide/topics/ui/dialogs#java
 *
 * Plays a video in a DialogFragment. Pause and Play buttons implemented.
 * Pass in the video's uri name as a string by calling setArguments(Bundle) in the Fragment's host.
 *
 * Clicking out of the Dialog stops the video. Rotating the screen resets the video.
 */
public class VideoDialogFragment extends DialogFragment {

    VideoView videoView;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View v = inflater.inflate(R.layout.fragment_video_dialog, null);
        builder.setView(v);

        VideoView videoView = v.findViewById(R.id.videoview);

        Bundle b = getArguments(); // get name of uri from passed in bundle
        String uriName = b.getString("uri");
        Uri uri = Uri.parse(uriName);

        videoView.setVideoURI(uri);
        videoView.start();

        Button buttonPlayPause = v.findViewById(R.id.play_pause_vid);
        buttonPlayPause.setText("||");
        buttonPlayPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (videoView.isPlaying()) {
                    videoView.pause();
                    buttonPlayPause.setText("\u25B6");
                } else {
                    videoView.start();
                    buttonPlayPause.setText("||");
                }

            }
        });

        return builder.create();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (videoView != null)
            videoView.stopPlayback();
    }
}
