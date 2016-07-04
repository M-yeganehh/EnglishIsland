package englishisland.a123english.com.englishisland;

import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.VideoView;

import java.util.Locale;

import co.mobiwise.library.InteractivePlayerView;
import co.mobiwise.library.OnActionClickedListener;

public class Class_Activity extends AppCompatActivity implements OnActionClickedListener {
    private VideoView videoView;
    private int position = 0;
    private MediaController mediaController;
    private MediaPlayer mediaPlayer;
    private int startTime = 0;
    private int finalTime = 0;
    CardView videoDLcard;
    CardView audioDLcard;
    private int forwardTime = 5000;
    private int backwardTime = 5000;
    InteractivePlayerView ipv;
    RelativeLayout AudioLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class);
        Locale locale = new Locale("en");
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        this.setContentView(R.layout.activity_class);
        getBaseContext().getResources().updateConfiguration(config,getBaseContext().getResources().getDisplayMetrics());
        int LessonNumber = getIntent().getIntExtra("Lesson", 0);
        videoView = (VideoView) findViewById(R.id.videoView);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarClass);

        switch (LessonNumber) {
            case 1: {
                toolbar.setTitle(R.string.lesson1);
                break;
            }
        }
        setSupportActionBar(toolbar);

        videoDLcard = (CardView) findViewById(R.id.videoDLcard);
        audioDLcard = (CardView) findViewById(R.id.AudioDLcard);

        // Set the media controller buttons
        if (mediaController == null) {
            mediaController = new MediaController(Class_Activity.this);
            // Set the videoView that acts as the anchor for the MediaController.
            mediaController.setAnchorView(videoView);
            // Set MediaController for VideoView
            videoView.setMediaController(mediaController);
        }


        try {
            // ID of video file.
            int id = this.getRawResIdByName("manooch");
            videoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + id));

        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }



        // When the video file ready for playback.
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

            public void onPrepared(final MediaPlayer mediaPlayer) {


                videoView.seekTo(position);
                if (position == 0) {
                 //   videoView.start();
                }

                // When video Screen change size.
                mediaPlayer.setOnVideoSizeChangedListener(new MediaPlayer.OnVideoSizeChangedListener() {
                    @Override
                    public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {

                        // Re-Set the videoView that acts as the anchor for the MediaController
                        mediaController.setAnchorView(videoView);
                    }
                });
            }
        });

        mediaPlayer = MediaPlayer.create(this, R.raw.music);
        finalTime = mediaPlayer.getDuration();
        startTime = mediaPlayer.getCurrentPosition();

        ipv = (InteractivePlayerView) findViewById(R.id.ipv);
        ipv.setMax(finalTime / 1000);
        ipv.setProgress(startTime / 1000);
        ipv.setOnActionClickedListener(this);
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                ipv.setAction2Selected(false);
                startTime = 0;
            }
        });

        AudioLayout = (RelativeLayout) findViewById(R.id.musicayoutSet);





    }

    // Find ID corresponding to the name of the resource (in the directory raw).
    public int getRawResIdByName(String resName) {
        String pkgName = this.getPackageName();
        // Return 0 if not found.
        int resID = this.getResources().getIdentifier(resName, "raw", pkgName);
        Log.i("AndroidVideoView", "Res Name: " + resName + "==> Res ID = " + resID);
        return resID;
    }

    @Override
    public void onActionClicked(int id) {
        switch (id) {
            case 1:
                startTime = mediaPlayer.getCurrentPosition();
                int backtemp = startTime;

                if ((backtemp - backwardTime) > 0) {
                    startTime = startTime - backwardTime;
                    mediaPlayer.seekTo(startTime);
                    ipv.setProgress(startTime / 1000);

                }                break;
            case 2:
                if (!ipv.isPlaying()) {
                    ipv.setProgress(startTime / 1000);
                    ipv.start();
                    mediaPlayer.start();
                } else {
                    ipv.stop();
                    mediaPlayer.pause();
                }
                break;
            case 3:
                startTime = mediaPlayer.getCurrentPosition();
                int fortemp = startTime;
                if ((fortemp + forwardTime) <= finalTime) {
                    startTime = startTime + forwardTime;
                    mediaPlayer.seekTo(startTime);
                    ipv.setProgress(startTime / 1000);
                }                break;
            default:
                break;
        }
    }

    // When you change direction of phone, this method will be called.
    // It store the state of video (Current position)
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        // Store current position.
        savedInstanceState.putInt("CurrentPosition", videoView.getCurrentPosition());
        videoView.pause();
    }


    // After rotating the phone. This method is called.
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        // Get saved position.
        position = savedInstanceState.getInt("CurrentPosition");
        videoView.seekTo(position);
        videoView.start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void VideoDownloadClick(View view) {
        videoView.setVisibility(View.VISIBLE);
        videoDLcard.setVisibility(View.GONE);
    }

    public void AudioDownloadClick(View view) {
        AudioLayout.setVisibility(View.VISIBLE);
        audioDLcard.setVisibility(View.GONE);
    }
}
