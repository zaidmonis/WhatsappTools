package zaidstudios.whatsapptools;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;
import org.apache.commons.io.FileUtils;
import java.io.File;
import java.io.IOException;

public class VideoActivity extends AppCompatActivity {

    VideoView videoView;
    private Button downloadButton, cancelButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        videoView = findViewById(R.id.videoView);
        downloadButton = findViewById(R.id.downloadButton);
        cancelButton = findViewById(R.id.cancelButton);

        Intent intent = getIntent();
        final String s = getIntent().getStringExtra("path2");
        videoView.setVideoURI(Uri.parse(s));

        downloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                File source = new File(s);
                String descPath = Environment.getExternalStorageDirectory() + "/WA Tools/WAVideos";
                File desc = new File(descPath);
                try{
                    desc.mkdir();
                }
                catch (Exception e){

                }
                try
                {
                    FileUtils.copyFileToDirectory(source, desc, true);
                    Toast.makeText(getApplicationContext(), "Video Saved in Gallery", Toast.LENGTH_SHORT).show();
                    finish();

                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        });


        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
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
}
