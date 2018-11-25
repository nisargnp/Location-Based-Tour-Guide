package edu.umd.cs.cmsc436.location_basedtourguide.AudioVideo;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
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
 *
 * Seekbar implementation adapted from:
 * https://stackoverflow.com/questions/15987317/syncing-video-view-with-seekbar-with-onstoptrackingtouch
 */
public class VideoDialogFragment extends DialogFragment {

    private VideoView videoView;
    private Handler handler = new Handler();
    private SeekBar seekBar;

    Runnable updateTimeTask = new Runnable() {
        public void run() {
            Log.d("UpdateTimeTask", "Current: " + videoView.getCurrentPosition() + " " + seekBar.getProgress());
            seekBar.setProgress(videoView.getCurrentPosition());
            seekBar.setMax(videoView.getDuration());
            handler.postDelayed(this, 100); // runs runnable again after 100 millis
        }
    };
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View v = inflater.inflate(R.layout.fragment_video_dialog, null);
        builder.setView(v);

        videoView = v.findViewById(R.id.videoview);

        Bundle b = getArguments(); // get name of uri from passed in bundle
        String uriName = b.getString("uri");
        Uri uri = Uri.parse(uriName);

        //videoView.setVideoURI(uri);
        videoView.setVideoURI(Uri.parse("android.resource://" + getActivity().getPackageName() + "/" + R.raw.teapot));

        videoView.start();

        updateProgressBar();

        Button buttonPlayPause = v.findViewById(R.id.play_pause_vid);
        buttonPlayPause.setText("||");
        buttonPlayPause.setOnClickListener(v1 -> {
            if (videoView.isPlaying()) {
                videoView.pause();
                buttonPlayPause.setText("\u25B6");
            } else {
                videoView.start();
                buttonPlayPause.setText("||");
            }

        });

        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                buttonPlayPause.setText("\u25B6");
            }
        });

        seekBar = v.findViewById(R.id.video_seekbar);
/*        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onProgressChanged(SeekBar seekbar, int progress, boolean fromTouch) {
                if (fromTouch) {
                    videoView.seekTo(progress);
                    //Log.d("VideoDebug", "Seeking to " + progress);
                }
            }
            public void onStartTrackingTouch(SeekBar seekbar) {
            }
            public void onStopTrackingTouch(SeekBar seekbar) {
            }
        });*/
        return builder.create();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (videoView != null)
            videoView.stopPlayback();
    }

    private void updateProgressBar() {
        handler.postDelayed(updateTimeTask, 100);
    }




}
