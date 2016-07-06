package com.simplelecture.main.activities;

import android.app.ProgressDialog;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.MediaController;
import android.widget.VideoView;

import com.simplelecture.main.R;
import com.simplelecture.main.http.ApiService;
import com.simplelecture.main.http.NetworkLayer;
import com.simplelecture.main.util.ConnectionDetector;
import com.simplelecture.main.util.SnackBarManagement;
import com.simplelecture.main.util.Util;

import org.json.JSONObject;

public class VideoPlayerActivity extends AppCompatActivity implements NetworkLayer {

    private int position = 0;
    VideoView videoView;
    // Insert your Video URL
    // String VideoURL = "http://www.androidbegin.com/tutorial/AndroidCommercial.3gp";
    // String VideoURL ="https://player.vimeo.com/video/49462103";
    //https://player.vimeo.com/video/124017989/config
    String videoURL = "";
    private int ctId;
    private ProgressDialog pd;
    private boolean param_get_VideoPlayer = false;
    private SnackBarManagement snack;
    private CoordinatorLayout coordinatorLayout;
    private boolean videoPause;
    private FloatingActionButton floatingActionBack;
    private CoordinatorLayout floatingCoordinatorLayout;
    private String displayView;

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
    protected void onPause() {
        super.onPause();

        if(videoView.isPlaying()){
            videoPause = true;
            videoView.pause();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(videoPause) {
            videoPause = false;
            videoView.resume();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Util.secureScreenShot(VideoPlayerActivity.this);
        setContentView(R.layout.activity_video_player);

        try {
            Bundle bundle = getIntent().getExtras();
            ctId = bundle.getInt("ctId1");
            displayView = bundle.getString("DisplayView");
            videoURL = bundle.getString("videoURL");

            Log.i("ctId", "ctId -" + ctId + " - " + displayView + " - " + videoURL);

            snack = new SnackBarManagement(VideoPlayerActivity.this);
            coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);
            videoView = (VideoView) findViewById(R.id.videoView);
            floatingCoordinatorLayout = (CoordinatorLayout) findViewById(R.id.floatingActionButton);
            floatingActionBack = (FloatingActionButton) floatingCoordinatorLayout.findViewById(R.id.floatingActionBack);

            floatingActionBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });


            if(displayView.equalsIgnoreCase("CourseIndexFragment")) {
                if (new ConnectionDetector(VideoPlayerActivity.this).isConnectingToInternet()) {
                    param_get_VideoPlayer = true;
                    pd = new Util().waitingMessage(VideoPlayerActivity.this, "", getResources().getString(R.string.loading));
                    //My HomeCoursesModel service
                    ApiService.getApiService().doGetVimeoVideoURL(VideoPlayerActivity.this, ctId);
                } else {
                    snack.snackBarNotification(coordinatorLayout, 1, getResources().getString(R.string.noInternetConnection), getResources().getString(R.string.dismiss));
                }
            } else if(displayView.equalsIgnoreCase("SampleVideoFragment")){

                OnPrepareVideoPlay(videoURL);
            }
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }


        //we also set an setOnPreparedListener in order to know when the video file is ready for playback
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

            public void onPrepared(MediaPlayer mediaPlayer) {
              //  Log.i("TAG", "Duration = " + videoView.getDuration());
                try {
                    // close the progress bar and play the video
                    pd.dismiss();
                    //if we have a position on savedInstanceState, the video playback should start from here
                    videoView.seekTo(position);
                    if (position == 0) {
                        videoView.start();
                    } else {
                        //if we come from a resumed activity, video playback will be paused
                        videoView.pause();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

            public void onCompletion(MediaPlayer mp) {
                if (pd.isShowing()) {
                    pd.dismiss();
                }
                finish();
            }
        });

    }

    private void OnPrepareVideoPlay(String videoURLID) {
        try {


            MediaController mediaController = new MediaController(this);
            mediaController.setAnchorView(videoView);
            videoView.setMediaController(mediaController);

            pd = new Util().waitingMessage(VideoPlayerActivity.this, "", getResources().getString(R.string.loading));
            pd.setCanceledOnTouchOutside(false);

            Uri video = Uri.parse(videoURLID);
            //set the uri of the video to be played
            videoView.setVideoURI(video);

            //set the media controller in the VideoView
            videoView.setMediaController(mediaController);

            videoView.requestFocus();


        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }

    }

    @Override
    public void parseResponse(String response) {
        try {
            if (pd.isShowing()) {
                pd.dismiss();
            }
            Log.i("videoURL", "*******************" + response);

            JSONObject jSONObject = new JSONObject(response);
            videoURL = jSONObject.getString("Url");

            OnPrepareVideoPlay(videoURL);


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void showError(String error) {
        try {
            if (pd.isShowing()) {
                pd.dismiss();
            }

            if(error.isEmpty()){
                error = "Error in Video";
            }

            snack.snackBarNotification(coordinatorLayout, 1, error, getResources().getString(R.string.dismiss));


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
