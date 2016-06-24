package com.example.mizutani.myapplication;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class ImageTest extends AppCompatActivity {
    private ImageView image1;
    private int[] imageArray;
    private int currentIndex;
    private int startIndex;
    private int endIndex;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_test);

        ImageView imageView2 = (ImageView)findViewById(R.id.image1);
        imageView2.setImageResource(R.drawable.icon_162821_64);
//        imageView.setImageResource(R.drawable.icon_162821_64);
    }
}
