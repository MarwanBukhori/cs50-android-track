package com.example.fiftygram;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import android.view.View;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.request.RequestOptions;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.BitSet;

import javax.xml.transform.Transformer;

import jp.wasabeef.glide.transformations.gpu.SepiaFilterTransformation;
import jp.wasabeef.glide.transformations.gpu.SketchFilterTransformation;

public class MainActivity extends AppCompatActivity {

    private ImageView imageView;
    private Bitmap image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //set id
        imageView = findViewById(R.id.image_view);
    }

    //method to allow user select file from phone
    public void choosePhoto(View v){

        //open gallery
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);

        //any type of file as long its image
        intent.setType("image/*");

        startActivityForResult(intent, 1);

    }


    public void apply(Transformation<Bitmap> filter){
        Glide
                .with(this)
                .load(image)
                .apply(RequestOptions.bitmapTransform(filter))
                .into(imageView);
    }

    public void applySephia(View v){
        apply(new SepiaFilterTransformation());
    }

    public void applySketch(View v){
        apply(new SketchFilterTransformation());
    }



    //called by system when exit activity (after selected photo)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK && data != null){

            try {
            Uri uri = data.getData();

            ParcelFileDescriptor parcelFileDescriptor = getContentResolver().openFileDescriptor(uri,"r");

            FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();

            //factory, a class to instantiate
            image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
            parcelFileDescriptor.close();

            //set imageview wth image
            imageView.setImageBitmap(image);

            }catch(IOException e){
                Log.e ("cs50", "Image not found",e);
            }
        }

    }
}