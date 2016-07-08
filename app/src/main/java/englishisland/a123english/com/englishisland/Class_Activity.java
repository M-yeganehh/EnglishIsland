package englishisland.a123english.com.englishisland;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.NotificationCompat.Builder;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.coolerfall.download.DownloadCallback;
import com.coolerfall.download.DownloadManager;
import com.coolerfall.download.DownloadRequest;

import java.io.File;
import java.util.Locale;

import co.mobiwise.library.InteractivePlayerView;
import co.mobiwise.library.OnActionClickedListener;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;
import util.SecurePreferences;

public class Class_Activity extends AppCompatActivity implements OnActionClickedListener {
    private MediaPlayer mediaPlayer;
    private int startTime = 0;
    private int finalTime = 0;
    CardView videoDLcard;
    CardView pdfDLcard;
    CardView videoPlaycard;
    CardView pdfPlaycard;
    CardView AudioPlaycard;
    CardView videoprogresscard;
    CardView pdfprogresscard;
    CardView Audioprogresscard;
    CardView audioDLcard;
    private int forwardTime = 5000;
    private int backwardTime = 5000;
    InteractivePlayerView ipv;
    ProgressBar videoprogress;
    ProgressBar pdfprogress;
    ProgressBar Audioprogress;
    private NotificationManager mNotifyManager;
    private Builder mBuilder;
    DownloadManager manager = new DownloadManager();
    TextView videoDLPercent;
    TextView pdfDLPercent;
    TextView AudioDLPercent;
    TextView AudioDLstate;
    TextView VideoDLstate;
    TextView pdfDLstate;
    String VideoFile = "/manooch.mp4";
    String AudioFile = "/music.mp3";
    String pdfFile = "/english.pdf";
    String destPath = Environment.getExternalStorageDirectory() + "/EnglishIsland";
    SecurePreferences prefs;
    DownloadRequest pdfrequest;
    DownloadRequest audrequest;
    DownloadRequest vidrequest;
    boolean ISVideoAvailabe;
    boolean ISPdfAvailabe;
    boolean ISAudioAvailabe;
    boolean ISAudioPlaying;
    boolean ISAudioPaused;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class);
        Locale locale = new Locale("fa");
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        this.setContentView(R.layout.activity_class);
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        int LessonNumber = getIntent().getIntExtra("Lesson", 0);
        prefs = SecurePreferences.getInstance(Class_Activity.this, "DL_Prefs");
        ISVideoAvailabe = prefs.getBoolean("ISVideoAvailabe", false);
        ISPdfAvailabe = prefs.getBoolean("ISPdfAvailabe", false);
        ISAudioAvailabe = prefs.getBoolean("ISAudioAvailabe", false);
        ISAudioPlaying = prefs.getBoolean("ISAudioPlaying", false);
        ISAudioPaused = prefs.getBoolean("ISAudioPaused", false);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarClass);

        switch (LessonNumber) {
            case 1: {
                toolbar.setTitle(R.string.session1);
                break;
            }
        }

        setSupportActionBar(toolbar);
        videoprogress = (ProgressBar) findViewById(R.id.videoprogress);
        pdfprogress = (ProgressBar) findViewById(R.id.pdfprogress);
        Audioprogress = (ProgressBar) findViewById(R.id.Audioprogress);
        videoDLcard = (CardView) findViewById(R.id.videoDLcard);
        pdfDLcard = (CardView) findViewById(R.id.pdfDLcard);
        audioDLcard = (CardView) findViewById(R.id.AudioDLcard);
        pdfprogresscard = (CardView) findViewById(R.id.pdfprogresscard);
        videoprogresscard = (CardView) findViewById(R.id.videoprogresscard);
        Audioprogresscard = (CardView) findViewById(R.id.Audioprogresscard);
        videoPlaycard = (CardView) findViewById(R.id.videoPlaycard);
        pdfPlaycard = (CardView) findViewById(R.id.pdfPlaycard);
        AudioPlaycard = (CardView) findViewById(R.id.AudioPlaycard);

        if (!ISVideoAvailabe) {
            File file = new File(destPath + VideoFile);
            if (file.exists()) {
                prefs.putBoolean("ISVideoAvailabe", true);
                prefs.commit();
                videoDLcard.setVisibility(View.GONE);
                videoprogresscard.setVisibility(View.GONE);
                videoPlaycard.setVisibility(View.VISIBLE);
            }
        } else if (ISVideoAvailabe) {
            File file = new File(destPath + VideoFile);
            if (file.exists()) {
                videoDLcard.setVisibility(View.GONE);
                videoprogresscard.setVisibility(View.GONE);
                videoPlaycard.setVisibility(View.VISIBLE);
            } else {
                videoDLcard.setVisibility(View.VISIBLE);
                videoprogresscard.setVisibility(View.GONE);
                videoPlaycard.setVisibility(View.GONE);
            }
        }
        if (!ISAudioAvailabe) {
            File file = new File(destPath + AudioFile);
            if (file.exists()) {
                prefs.putBoolean("ISAudioAvailabe", true);
                prefs.commit();
                audioDLcard.setVisibility(View.GONE);
                Audioprogresscard.setVisibility(View.GONE);
                AudioPlaycard.setVisibility(View.VISIBLE);
                prepareAudio();
            }
        } else if (ISAudioAvailabe) {
            File file = new File(destPath + AudioFile);
            if (file.exists()) {
                audioDLcard.setVisibility(View.GONE);
                Audioprogresscard.setVisibility(View.GONE);
                AudioPlaycard.setVisibility(View.VISIBLE);
                prepareAudio();
            } else {
                audioDLcard.setVisibility(View.VISIBLE);
                Audioprogresscard.setVisibility(View.GONE);
                AudioPlaycard.setVisibility(View.GONE);
            }
        }
        if (!ISPdfAvailabe) {
            File file = new File(destPath + pdfFile);
            if (file.exists()) {
                prefs.putBoolean("ISPdfAvailabe", true);
                prefs.commit();
                pdfDLcard.setVisibility(View.GONE);
                pdfprogresscard.setVisibility(View.GONE);
                pdfPlaycard.setVisibility(View.VISIBLE);
            }
        } else if (ISPdfAvailabe) {
            File file = new File(destPath + pdfFile);
            if (file.exists()) {
                pdfDLcard.setVisibility(View.GONE);
                pdfprogresscard.setVisibility(View.GONE);
                pdfPlaycard.setVisibility(View.VISIBLE);

            } else {
                pdfDLcard.setVisibility(View.VISIBLE);
                pdfprogresscard.setVisibility(View.GONE);
                pdfPlaycard.setVisibility(View.GONE);
            }
        }
        videoDLPercent = (TextView) findViewById(R.id.videoDLpercent);
        pdfDLPercent = (TextView) findViewById(R.id.pdfDLpercent);
        AudioDLPercent = (TextView) findViewById(R.id.AudioDLpercent);
        VideoDLstate = (TextView) findViewById(R.id.videoDLstate);
        pdfDLstate = (TextView) findViewById(R.id.pdfDLstate);
        AudioDLstate = (TextView) findViewById(R.id.AudioDLstate);
        mNotifyManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mBuilder = new NotificationCompat.Builder(Class_Activity.this);
        mBuilder.setContentTitle(getString(R.string.gettingfile))
                .setContentText(getString(R.string.dling))
                .setSmallIcon(R.drawable.ic_get_app_black_24dp);


    }

    private void prepareAudio() {

        mediaPlayer = MediaPlayer.create(this, Uri.fromFile(new File(destPath + AudioFile)));
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
        if (ISAudioPlaying) {
            startTime = prefs.getInt("StartTime", 0);
            ipv.setProgress(startTime / 1000);
            ipv.setAction2Selected(true);
            mediaPlayer.seekTo(startTime);
            ipv.start();
            mediaPlayer.start();
        }
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

                }
                break;
            case 2:

                if (!ipv.isPlaying()) {
                    ipv.setProgress(startTime / 1000);
                    ipv.start();
                    mediaPlayer.start();
                    ISAudioPaused = false;

                } else {
                    startTime = mediaPlayer.getCurrentPosition();
                    ipv.stop();
                    mediaPlayer.pause();
                    ISAudioPaused = true;
                }
                break;
            case 3:
                startTime = mediaPlayer.getCurrentPosition();
                int fortemp = startTime;
                if ((fortemp + forwardTime) <= finalTime) {
                    startTime = startTime + forwardTime;
                    mediaPlayer.seekTo(startTime);
                    ipv.setProgress(startTime / 1000);
                }
                break;
            default:
                break;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.class_menu, menu);
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
        // startActivity(new Intent(Class_Activity.this, VideoPlayerActivity.class));
        videoDLcard.setVisibility(View.GONE);
        videoprogresscard.setVisibility(View.VISIBLE);

        vidrequest = new DownloadRequest()
                .setDownloadId(2)
                .setUrl("http://0up.ir/do.php?downf=manooch.mp4")
                //  .setDestFilePath(destPath)
                .setDestDirectory(destPath)
                .setDownloadCallback(new DownloadCallback() {
                    @Override
                    public void onStart(int downloadId, long totalBytes) {
                        VideoDLstate.setText(R.string.startingdl);
                        videoDLPercent.setText("0/" + totalBytes / 1000000);
                        mNotifyManager.notify(0, mBuilder.build());
                    }

                    @Override
                    public void onRetry(int downloadId) {
                        VideoDLstate.setText(R.string.retry);

                    }

                    @Override
                    public void onProgress(int downloadId, long bytesWritten, long totalBytes) {
                        VideoDLstate.setText(R.string.dling);

                        long MBwritten = bytesWritten / 1000000;
                        long totalMB = totalBytes / 1000000;
                        videoDLPercent.setText(getString(R.string.megabyte) + " " + MBwritten + "/" + totalMB);
                        videoprogress.setMax((int) totalBytes);
                        videoprogress.setProgress((int) bytesWritten);


                    }

                    @Override
                    public void onSuccess(int downloadId, String filePath) {
                        VideoDLstate.setText(R.string.dlsuccess);

                        mBuilder.setContentText(getString(R.string.dlsuccess));
                        mNotifyManager.notify(0, mBuilder.build());

                        videoDLcard.setVisibility(View.GONE);
                        videoPlaycard.setVisibility(View.VISIBLE);
                        prefs.putBoolean("ISVideoAvailabe", true);
                        prefs.commit();
                    }

                    @Override
                    public void onFailure(int downloadId, int statusCode, String errMsg) {
                        VideoDLstate.setText(R.string.dlfail);
                        mBuilder.setContentText(getString(R.string.dlfail));
                        mNotifyManager.notify(0, mBuilder.build());
                    }
                });

        manager.add(vidrequest);
    }

    public void AudioDownloadClick(View view) {
        audioDLcard.setVisibility(View.GONE);
        Audioprogresscard.setVisibility(View.VISIBLE);

        audrequest = new DownloadRequest()
                .setDownloadId(3)
                .setUrl("http://0up.ir/do.php?downf=music_0bb3f.mp3")
                //  .setDestFilePath(destPath)
                .setDestDirectory(destPath)
                .setDownloadCallback(new DownloadCallback() {
                    @Override
                    public void onStart(int downloadId, long totalBytes) {
                        AudioDLstate.setText(R.string.startingdl);
                        AudioDLPercent.setText("0/" + totalBytes / 1000000);
                        mNotifyManager.notify(2, mBuilder.build());
                    }

                    @Override
                    public void onRetry(int downloadId) {
                        AudioDLstate.setText(R.string.retry);

                    }

                    @Override
                    public void onProgress(int downloadId, long bytesWritten, long totalBytes) {
                        AudioDLstate.setText(R.string.dling);

                        long MBwritten = bytesWritten / 1000000;
                        long totalMB = totalBytes / 1000000;
                        AudioDLPercent.setText(getString(R.string.megabyte) + " " + MBwritten + "/" + totalMB);
                        Audioprogress.setMax((int) totalBytes);
                        Audioprogress.setProgress((int) bytesWritten);


                    }

                    @Override
                    public void onSuccess(int downloadId, String filePath) {
                        prepareAudio();
                        AudioDLstate.setText(R.string.dlsuccess);

                        mBuilder.setContentText(getString(R.string.dlsuccess));
                        mNotifyManager.notify(2, mBuilder.build());

                        prepareAudio();
                        audioDLcard.setVisibility(View.GONE);
                        AudioPlaycard.setVisibility(View.VISIBLE);
                        prefs.putBoolean("ISAudioAvailabe", true);
                        prefs.commit();
                    }

                    @Override
                    public void onFailure(int downloadId, int statusCode, String errMsg) {
                        AudioDLstate.setText(R.string.dlfail);
                        mBuilder.setContentText(getString(R.string.dlfail));
                        mNotifyManager.notify(2, mBuilder.build());
                    }
                });

        manager.add(audrequest);
    }


    public void VideoPlayClick(View view) {

        try {
            if (mediaPlayer.isPlaying()) {
                ipv.stop();
                mediaPlayer.pause();
            }
        } catch (Exception e) {

            e.printStackTrace();
        }
        startActivity(new Intent(Class_Activity.this, VideoPlayerActivity.class));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            if (mediaPlayer.isPlaying()) {
                prefs.putBoolean("ISAudioPlaying", true);
                prefs.putInt("StartTime", mediaPlayer.getCurrentPosition());
                mediaPlayer.release();
            } else if (ISAudioPaused) {
                prefs.putBoolean("ISAudioPlaying", true);
                prefs.putInt("StartTime", mediaPlayer.getCurrentPosition());
                mediaPlayer.release();

            } else if (!mediaPlayer.isPlaying()) {
                prefs.putBoolean("ISAudioPlaying", false);
                prefs.putInt("StartTime", 0);

            }
        } catch (Exception e) {
            e.printStackTrace();


        }
    }


    public void PdfDownloadClick(View view) {
        pdfDLcard.setVisibility(View.GONE);
        pdfprogresscard.setVisibility(View.VISIBLE);

        pdfrequest = new DownloadRequest()
                .setDownloadId(4)
                .setUrl("http://0up.ir/do.php?downf=english.pdf")
                .setDestDirectory(destPath)
                .setDownloadCallback(new DownloadCallback() {
                    @Override
                    public void onStart(int downloadId, long totalBytes) {
                        AudioDLstate.setText(R.string.startingdl);
                        AudioDLPercent.setText("0/" + totalBytes);
                        mNotifyManager.notify(3, mBuilder.build());
                    }

                    @Override
                    public void onRetry(int downloadId) {
                        pdfDLstate.setText(R.string.retry);

                    }

                    @Override
                    public void onProgress(int downloadId, long bytesWritten, long totalBytes) {
                        pdfDLstate.setText(R.string.dling);

                        long MBwritten = bytesWritten / 1000;
                        long totalMB = totalBytes / 1000;
                        pdfDLPercent.setText(getString(R.string.kilobyte) + MBwritten + "/" + totalMB);
                        pdfprogress.setMax((int) totalBytes);
                        pdfprogress.setProgress((int) bytesWritten);


                    }

                    @Override
                    public void onSuccess(int downloadId, String filePath) {
                        pdfDLstate.setText(R.string.dlsuccess);

                        mBuilder.setContentText(getString(R.string.dlsuccess));
                        mNotifyManager.notify(3, mBuilder.build());

                        pdfDLcard.setVisibility(View.GONE);
                        pdfPlaycard.setVisibility(View.VISIBLE);
                        prefs.putBoolean("ISPdfAvailabe", true);
                        prefs.commit();
                    }

                    @Override
                    public void onFailure(int downloadId, int statusCode, String errMsg) {
                        AudioDLstate.setText(R.string.dlfail);
                        mBuilder.setContentText(getString(R.string.dlfail));
                        mNotifyManager.notify(3, mBuilder.build());
                    }
                });

        manager.add(pdfrequest);

    }

    public void PdfPlayClick(View view) {
        Uri path = Uri.fromFile(new File(destPath + pdfFile));
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(path, "application/pdf");
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void VideoProgressClicked(View view) {
        if (manager.isDownloading(1)){

        }else {
            VideoDLstate.setText(getString(R.string.retry));
            manager.add(vidrequest);
        }
    }

    public void AudioProgressClick(View view) {
        if (manager.isDownloading(2)){


        }else {
            AudioDLstate.setText(getString(R.string.retry));
            manager.add(audrequest);
        }
    }

    public void PdfProgressClick(View view) {
        if (manager.isDownloading(3)){

        }else {
            pdfDLstate.setText(getString(R.string.retry));
            manager.add(pdfrequest);
        }
    }
}