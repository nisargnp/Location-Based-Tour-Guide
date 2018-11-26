package edu.umd.cs.cmsc436.location_basedtourguide.AudioVideo;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.VideoView;

import edu.umd.cs.cmsc436.location_basedtourguide.R;

/*
This tutorial helped in implementing the seekbar
https://www.youtube.com/watch?v=7Z3fAAZezZk
 */
public class AudioDialogFragment extends DialogFragment {

    private MediaPlayer mediaPlayer;
    private Handler handler;
    private SeekBar seekBar;
    private boolean isPlaying = true;
    private String title;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        handler = new Handler();

        View v = inflater.inflate(R.layout.fragment_audio_dialog, null);
        builder.setView(v);

        if (title != null) {
            TextView titleTextView = v.findViewById(R.id.play_pause_text);
            titleTextView.setText(title);
        }

        Bundle b = getArguments(); // get name of uri from passed in bundle
        String uriName = b.getString("uri");
        Uri uri = Uri.parse(uriName);

        seekBar = v.findViewById(R.id.audio_seekbar);
        mediaPlayer = MediaPlayer.create(getActivity(),uri);
        mediaPlayer.setOnPreparedListener(mp -> {
            seekBar.setMax(mediaPlayer.getDuration());
            mediaPlayer.start();
            changeSeekBar();
        });


        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    mediaPlayer.seekTo(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        Button play = v.findViewById(R.id.play_audio);
        play.setText("||");
        play.setOnClickListener(v1 -> {
            if (isPlaying) {
                // Pause audio
                mediaPlayer.pause();
                isPlaying = false;
                play.setText("\u25B6");
            } else {
                // Play audio
                mediaPlayer.start();
                changeSeekBar();
                isPlaying = true;
                play.setText("||");
            }
        });
        // When audio is done, have button display  play icon
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                isPlaying = false;
                play.setText("\u25B6");
            }
        });

        return builder.create();
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mediaPlayer.stop();
        mediaPlayer.release();
    }

    private synchronized void changeSeekBar() {
        try {
            seekBar.setProgress(mediaPlayer.getCurrentPosition());
            if (mediaPlayer.isPlaying()) {
                handler.postDelayed(this::changeSeekBar, 1000);
            }
        } catch (IllegalStateException e) {
            seekBar.setProgress(0);
        }
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
