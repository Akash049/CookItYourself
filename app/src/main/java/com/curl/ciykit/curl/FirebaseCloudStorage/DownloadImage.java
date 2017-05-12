package com.curl.ciykit.curl.FirebaseCloudStorage;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.curl.ciykit.curl.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Akash Chandra on 14-01-2017.
 */

public class DownloadImage extends AppCompatActivity {
    public static final int RC_PHOTO_PICKER =2;
    private StorageReference mNewsPhotoStorageReference;
    @Bind(R.id.upload) Button mPhotoPicker;
    @Bind(R.id.image)
    ImageView imageView;
    @Bind(R.id.Show) Button show;
    private FirebaseStorage mFirebaseStorage;

    String downloadImageURL = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_firebase_cs);
        ButterKnife.bind(this);
        //Initialize the firebase instance
        mFirebaseStorage = FirebaseStorage.getInstance();
        mNewsPhotoStorageReference = mFirebaseStorage.getReference().child("news_img");

        mPhotoPicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: Fire an intent to show an image picker
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/jpeg");
                intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
                startActivityForResult(Intent.createChooser(intent, "Complete action using"), RC_PHOTO_PICKER);
            }
        });

        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isPhoto = downloadImageURL != null;if (isPhoto) {
                    Glide.with(imageView.getContext())
                            .load(downloadImageURL)
                            .into(imageView);
                } else {
                    Toast.makeText(DownloadImage.this,"Unable to Downlaod",Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

         if(requestCode == RC_PHOTO_PICKER && resultCode == RESULT_OK){
            //Toast.makeText(DownloadImage.this,"Wokring",Toast.LENGTH_SHORT).show();
            Uri selectedImageUri = data.getData();

            StorageReference photoRef = mNewsPhotoStorageReference.child(selectedImageUri.getLastPathSegment());
            photoRef.putFile(selectedImageUri).addOnSuccessListener(DownloadImage.this, new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Uri downloadUrl = taskSnapshot.getDownloadUrl();
                    downloadImageURL = downloadUrl.toString();
                    Log.d("ImageUrl",downloadImageURL);
                    Toast.makeText(DownloadImage.this,downloadImageURL,Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
