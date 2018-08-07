package com.example.medell.videoplayer;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    static VideoView vp;
    ListView lv;
    String []items;
    ArrayList<File> myvideo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lv=(ListView)findViewById(R.id.lvPlaylist);
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)  {
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                //This is called if user has denied the permission before
                //In this case I am just asking the permission again
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},0);
            } else {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},0);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.videomenu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.videomenu:
            {
                myvideo=findvideo(Environment.getExternalStorageDirectory());
                items=new String[myvideo.size()];
                for(int i=0;i<myvideo.size();i++)
                {
                    // tost(mysong.get(i).getName().toString());
                    items[i]=myvideo.get(i).getName().toString().replace(".mp4","");

                }
                ArrayAdapter<String> adp= new ArrayAdapter<>(getApplicationContext(), R.layout.video_layout,R.id.textView, items);
                lv.setAdapter(adp);
                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        startActivity(new Intent(getApplicationContext(),Player.class).putExtra("pos",position).putExtra("videolist",myvideo));
                    }
                });
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    public ArrayList<File> findvideo(File root)
    {
        ArrayList<File> f1=new ArrayList<File>();
        File [] f=root.listFiles();
        for(File singlefiles:f)
        {
            if(singlefiles.isDirectory() && !singlefiles.isHidden())
            {
                f1.addAll(findvideo(singlefiles));
            }else
            {
                if(singlefiles.getName().endsWith(".mp4"))
                {
                    f1.add(singlefiles);
                }
            }
        }
        return  f1;
    }

}
