package edu.umd.cs.cmsc436.location_basedtourguide;

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
import android.widget.VideoView;

/*
This tutorial helped in implementing the seekbar
https://www.youtube.com/watch?v=7Z3fAAZezZk
 */
public class AudioDialogFragment extends DialogFragment {
    Button play;
    MediaPlayer mediaPlayer;
    Runnable runnable;
    Handler handler;
    SeekBar seekBar;
    boolean isPlaying = true;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        handler = new Handler();

        View v = inflater.inflate(R.layout.fragment_audio_dialog, null);
        builder.setView(v);


        Bundle b = getArguments(); // get name of uri from passed in bundle
        String uriName = b.getString("uri");
        Uri uri = Uri.parse(uriName);

        seekBar = v.findViewById(R.id.audio_seekbar);
        mediaPlayer = MediaPlayer.create(getActivity(),uri);
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                seekBar.setMax(mediaPlayer.getDuration());
                mediaPlayer.start();
                changeSeekBar();
            }
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

        play = v.findViewById(R.id.play_audio);
        play.setText("||");
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                runnable = new Runnable() {
                    @Override
                    public void run() {
                        changeSeekBar();
                    }
                };

                handler.postDelayed(runnable, 1000);
            }
        } catch (IllegalStateException e) {
            seekBar.setProgress(0);
        }
    }
}
