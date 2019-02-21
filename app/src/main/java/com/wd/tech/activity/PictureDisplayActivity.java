package com.wd.tech.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.facebook.drawee.view.SimpleDraweeView;
import com.wd.tech.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PictureDisplayActivity extends AppCompatActivity {

    @BindView(R.id.picture_display_image)
    SimpleDraweeView pictureDisplayImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_display);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        String mHeadPic = intent.getExtras().getString("mHeadPic");
        pictureDisplayImage.setImageURI(mHeadPic);
    }
}
