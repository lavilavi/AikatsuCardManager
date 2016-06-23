package com.example.mizutani.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class ImageTest extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_test);

        ImageView imageView2 = (ImageView)findViewById(R.id.image_view_2);
        imageView2.setImageResource(R.drawable.icon_162821_64);
    }
}
