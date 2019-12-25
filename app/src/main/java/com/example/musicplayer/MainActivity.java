package com.example.musicplayer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        askPermission();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        MusicPlayer.initPlayer(getApplicationContext());
    }

    private void askPermission() {
        ActivityCompat.requestPermissions(this,
                new String[]{ Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.ACCESS_NETWORK_STATE,
                        Manifest.permission.INTERNET }, 1);
    }

    private void initView() {
        List<Song> songs = MusicPlayer.getListSongs();
        RecyclerView rcl = findViewById(R.id.listSong);
        rcl.setLayoutManager(new LinearLayoutManager(this));
        ListSongAdapter adapter = new ListSongAdapter(this, songs);
        rcl.setAdapter(adapter);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        initView();
    }

    @Override
    protected void onResume() {
        if (MusicPlayer.getCurrentSong() != null) {
            ((CardView) findViewById(R.id.current)).setVisibility(View.VISIBLE);
            ((TextView) findViewById(R.id.currentTitle)).setText(MusicPlayer.getCurrentSong().getTitle());
            ((TextView) findViewById(R.id.currentArtist)).setText(MusicPlayer.getCurrentSong().getArtist());
            ProgressBar progress = findViewById(R.id.currentProgress);
            progress.setMax(MusicPlayer.getDuration());
            progress.setProgress(MusicPlayer.getCurrentPosition());

            final MaterialButton btnCurrentPlayPause = findViewById(R.id.btnCurrentPlayPause);
            if (MusicPlayer.isPlaying()) {
                btnCurrentPlayPause.setIcon(getDrawable(R.drawable.ic_pause_black_48dp));
            } else {
                btnCurrentPlayPause.setIcon(getDrawable(R.drawable.ic_play_arrow_black_48dp));
            }
            btnCurrentPlayPause.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (MusicPlayer.isPlaying()) {
                        MusicPlayer.pause();
                        btnCurrentPlayPause.setIcon(getDrawable(R.drawable.ic_play_arrow_black_48dp));
                    } else {
                        MusicPlayer.resume();
                        btnCurrentPlayPause.setIcon(getDrawable(R.drawable.ic_pause_black_48dp));
                    }
                }
            });
        }
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (MusicPlayer.isPlaying()) {
                    ((ProgressBar) findViewById(R.id.currentProgress)).setProgress(MusicPlayer.getCurrentPosition());
                }
                handler.postDelayed(this, 1000);
            }
        }, 1000);
        super.onResume();
    }
}
