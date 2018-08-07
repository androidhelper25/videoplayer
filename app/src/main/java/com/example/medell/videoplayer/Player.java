package com.example.medell.videoplayer;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.SeekBar;
import android.widget.VideoView;

import java.io.File;
import java.util.ArrayList;

public class Player extends AppCompatActivity {
    VideoView videoView;
    ArrayList<File> mysongs;
    int position;
    Uri ui;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        videoView =(VideoView)findViewById(R.id.videoView1);
        MediaController mediaController= new MediaController(this);
        mediaController.setAnchorView(videoView);
        Intent i=getIntent();
        Bundle b=i.getExtras();
        mysongs=(ArrayList) b.getParcelableArrayList("videolist");
        position=b.getInt("pos",0);
        ui=Uri.parse(mysongs.get(position).toString());
        videoView.setMediaController(mediaController);
        videoView.setVideoURI(ui);
        videoView.requestFocus();
        videoView.start();
    }


}
