package zaidstudios.whatsapptools;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.fenjuly.library.ArrowDownloadButton;

import org.apache.commons.io.FileUtils;
import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class ImageActivity extends AppCompatActivity {

    ImageView fullImage;
    ArrowDownloadButton downloadButton;
    int progress = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        fullImage = findViewById(R.id.fullImage);
        final String s = getIntent().getStringExtra("path");
        Glide.with(ImageActivity.this).load(new File(s)).into(fullImage);//loading image into imageView

        downloadButton = findViewById(R.id.downloadButton);

        downloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                File source = new File(s);
                String descPath = Environment.getExternalStorageDirectory() + "/Pictures/WA Tools/WAImages";
                File desc = new File(descPath);
                try{
                    desc.mkdir();
                }
                catch (Exception e){
                    Toast.makeText(ImageActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
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
                    e.printStackTrace();
                }
            }
        });
    }



    //To start the Media Scanning, so that the saved images could appear in Gallery.
    private static void scan(Context context, String volume) {
        Bundle args = new Bundle();
        args.putString("volume", volume);
        context.startService(new Intent().setComponent(new ComponentName("com.android.providers.media", "com.android.providers.media.MediaScannerService")).putExtras(args));
    }
}
