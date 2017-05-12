package com.curl.ciykit.curl.utils;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.curl.ciykit.curl.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class SaveImage extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Get the view from saveimage
		setContentView(R.layout.saveimage);
        Button save = (Button)findViewById(R.id.save);
        ImageView myImage = (ImageView)findViewById(R.id.image);
        myImage.setImageResource(R.drawable.wallpaper);

        save.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Bitmap bitmap;
                OutputStream output;

                // Retrieve the image from the res folder
                bitmap = BitmapFactory.decodeResource(getResources(),
                        R.drawable.wallpaper);

                // Find the SD Card path
                File filepath = Environment.getExternalStorageDirectory();

                // Create a new folder in SD Card
                File dir = new File(filepath.getAbsolutePath()
                        + "/MyImage/");
                dir.mkdirs();

                // Create a name for the saved image
                File file = new File(dir, "myimage.png");

                // Show a toast message on successful save
                Toast.makeText(SaveImage.this, "Image Saved to :"+file.getAbsolutePath().toString(),
                        Toast.LENGTH_SHORT).show();
                try {

                    output = new FileOutputStream(file);

                    // Compress into png format image from 0% - 100%
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, output);
                    output.flush();
                    output.close();
                }

                catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });

		// Locate ImageView in saveimage.xmlmageView myimage = (ImageView) findViewById(R.id.image);

		// Attach image into ImageView


	}
}
