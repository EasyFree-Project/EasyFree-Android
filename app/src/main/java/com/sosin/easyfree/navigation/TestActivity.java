package com.sosin.easyfree.navigation;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.sosin.easyfree.R;

public class TestActivity extends AppCompatActivity {
    CameraSurfaceView surfaceView;
    ImageView imageView;

    /* getActivity().getWindowManager().getDefaultDisplay() */ // in Fragment

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test2);

        surfaceView = findViewById(R.id.surfaceview);
        imageView = findViewById(R.id.pictureimage);

        Button button = findViewById(R.id.capturebutton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                capture();
            }
        });
    }
    public void capture(){
        surfaceView.capture(new Camera.PictureCallback() {
            @Override
            public void onPictureTaken(byte[] data, Camera camera) {
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 8;
                Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                imageView.setImageBitmap(bitmap);

                camera.startPreview();
            }
        });
    }
}