package zaidstudios.whatsapptools;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.fenjuly.library.ArrowDownloadButton;

import org.apache.commons.io.FileUtils;
import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import zaidstudios.rawderm.whatsapptools.R;

public class VideoActivity extends AppCompatActivity {

    VideoView videoView;
    int progress = 0;
    private ArrowDownloadButton downloadButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        videoView = findViewById(R.id.videoView);
        downloadButton = findViewById(R.id.downloadButton);
        Intent intent = getIntent();
        final String s = getIntent().getStringExtra("path2");
        videoView.setVideoURI(Uri.parse(s));

        downloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                File source = new File(s);
                String descPath = Environment.getExternalStorageDirectory() + "/Pictures/WA Tools/WAVideos";
                File desc = new File(descPath);
                try{
                    desc.mkdir();
                }
                catch (Exception e){
                    Toast.makeText(VideoActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                }
                try
                {
                    FileUtils.copyFileToDirectory(source, desc, true);
                    downloadButton.startAnimating();
                    Timer timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    progress = progress + 1;
                                    downloadButton.setProgress(progress);
                                }
                            });
                        }
                    }, 800, 5);
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            finish();
                        }
                    }, 2000);
                    scan(getApplicationContext(), "external");
                }
                catch (IOException e)
                {
                    Toast.makeText(VideoActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });


        // create an object of media controller
        MediaController mediaController = new MediaController(this);
// initiate a video view
        videoView = findViewById(R.id.videoView);
// set media controller object for a video view
        videoView.setMediaController(mediaController);
        videoView.start();




    }
    private static void scan(Context context, String volume) {
        Bundle args = new Bundle();
        args.putString("volume", volume);
        context.startService(new Intent().setComponent(new ComponentName("com.android.providers.media", "com.android.providers.media.MediaScannerService")).putExtras(args));
    }



}
