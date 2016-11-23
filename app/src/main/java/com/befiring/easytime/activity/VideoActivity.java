package com.befiring.easytime.activity;

import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.VideoView;

import com.befiring.easytime.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/11/11.
 */

public class VideoActivity extends AppCompatActivity{
    @Bind(R.id.video_view)VideoView videoView;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        ButterKnife.bind(this);

        videoView.setVideoPath(Environment.getExternalStorageDirectory().getAbsolutePath()+"/DCIM/Camera/test.mp4");
        videoView.start();

    }
}
