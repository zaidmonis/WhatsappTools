package zaidstudios.whatsapptools;

import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import org.apache.commons.io.FileUtils;
import java.io.File;
import java.io.IOException;

public class ImageActivity extends AppCompatActivity {

    ImageView fullImage;
    private Button downloadButton, cancelButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        fullImage = findViewById(R.id.fullImage);
        //Intent intent = getIntent();
        final String s = getIntent().getStringExtra("path");
        Glide.with(ImageActivity.this).load(new File(s)).into(fullImage);//loading image into imageView

        downloadButton = findViewById(R.id.downloadButton);
        cancelButton = findViewById(R.id.cancelButton);

        downloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                File source = new File(s);
                String descPath = Environment.getExternalStorageDirectory() + "/WA Tools/WAImages";
                File desc = new File(descPath);
                try{
                    desc.mkdir();
                }
                catch (Exception e){

                }
                try
                {
                    FileUtils.copyFileToDirectory(source, desc, true);
                    Toast.makeText(getApplicationContext(), "Image Saved in Gallery", Toast.LENGTH_SHORT).show();
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
    }
}
