package com.simplelecture.main.activities;

import android.app.ProgressDialog;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.MediaController;
import android.widget.VideoView;

import com.simplelecture.main.R;
import com.simplelecture.main.fragments.DashboardFragment;
import com.simplelecture.main.http.ApiService;
import com.simplelecture.main.util.ConnectionDetector;
import com.simplelecture.main.util.SnackBarManagement;
import com.simplelecture.main.util.Util;

public class VideoPlayerActivity extends AppCompatActivity {

    private int position = 0;
    private ProgressDialog progressDialog;
    VideoView videoView;
    // Insert your Video URL
   // String VideoURL = "http://www.androidbegin.com/tutorial/AndroidCommercial.3gp";
   // String VideoURL ="https://player.vimeo.com/video/49462103";
    //https://player.vimeo.com/video/124017989/config
    String VideoURL = "https://skyfiregcs-a.akamaihd.net/exp=1456228769~acl=%2A%2F417392989.mp4%2A~hmac=72b702c5c083e14535952aa82d25ffd2f4b5634242451fdba3485a58159e851c/2tierchgci/vimeo-prod-skyfire-std-us/01/4803/4/124017989/417392989.mp4";
    private String videoId;
    private ProgressDialog pd;
    private boolean param_get_VideoPlayer = false;
    private SnackBarManagement snack;
    private CoordinatorLayout coordinatorLayout;

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

        Bundle bundle = getIntent().getExtras();
        videoId = (String) bundle.get("videoId");
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);

        Log.i("videoId", videoId);

        videoView = (VideoView) findViewById(R.id.videoView);


        if (new ConnectionDetector(VideoPlayerActivity.this).isConnectingToInternet()) {
            param_get_VideoPlayer = true;
            pd = new Util().waitingMessage(VideoPlayerActivity.this, "", getResources().getString(R.string.loading));
            pd.setCanceledOnTouchOutside(false);
            //My Courses service
            ApiService.getApiService().doGetVimeoVideoURL(VideoPlayerActivity.this, null, videoId);
        } else {
            snack.snackBarNotification(coordinatorLayout, 1, getResources().getString(R.string.noInternetConnection), getResources().getString(R.string.dismiss));
        }
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

            Uri video = Uri.parse(VideoURL);
            //set the uri of the video to be played
            videoView.setVideoURI(video);

            //set the media controller in the VideoView
            videoView.setMediaController(mediaController);

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

        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

        public void onCompletion(MediaPlayer mp) {
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
            finish();
        }
    });

    }
}
