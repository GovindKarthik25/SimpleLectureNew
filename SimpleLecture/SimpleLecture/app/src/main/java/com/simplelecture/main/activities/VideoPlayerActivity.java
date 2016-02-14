package com.simplelecture.main.activities;

import android.app.ProgressDialog;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.MediaController;
import android.widget.VideoView;

import com.simplelecture.main.R;

public class VideoPlayerActivity extends AppCompatActivity {

    private int position = 0;
    private ProgressDialog progressDialog;
    VideoView videoView;

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        //we use onSaveInstanceState in order to store the video playback position for orientation change
        savedInstanceState.putInt("Position", videoView.getCurrentPosition());
        videoView.pause();
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        //we use onRestoreInstanceState in order to play the video playback from the stored position
        position = savedInstanceState.getInt("Position");
        videoView.seekTo(position);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);

        videoView = (VideoView) findViewById(R.id.videoView);

        videoView.setVideoPath("http://www.ebookfrenzy.com/android_book/movie.mp4");


        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);

        // create a progress bar while the video file is loading
        progressDialog = new ProgressDialog(VideoPlayerActivity.this);
        // set a message for the progress bar
        progressDialog.setMessage("Loading...");
        //set the progress bar not cancelable on users' touch
        progressDialog.setCancelable(false);
        // show the progress bar
        progressDialog.show();

        try {
            //set the media controller in the VideoView
            videoView.setMediaController(mediaController);

            //set the uri of the video to be played
            videoView.setVideoURI(Uri.parse("https://www.youtube.com/embed/Li-SqK-pt50"));

        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }

        videoView.requestFocus();
        //we also set an setOnPreparedListener in order to know when the video file is ready for playback
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

            public void onPrepared(MediaPlayer mediaPlayer) {
                Log.i("TAG", "Duration = " + videoView.getDuration());
                // close the progress bar and play the video
                progressDialog.dismiss();
                //if we have a position on savedInstanceState, the video playback should start from here
                videoView.seekTo(position);
                if (position == 0) {
                    videoView.start();
                } else {
                    //if we come from a resumed activity, video playback will be paused
                    videoView.pause();
                }
            }
        });






    }
}
