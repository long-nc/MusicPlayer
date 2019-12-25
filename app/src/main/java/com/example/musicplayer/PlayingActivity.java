package com.example.musicplayer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;

public class PlayingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playing);

        initView();
        setToolbar();
    }

    private void initView() {
        setSongInfo();
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (MusicPlayer.isPlaying()) {
                    ((TextView) findViewById(R.id.playingMusicTitle)).setText(MusicPlayer.getCurrentSong().getTitle());
                    ((TextView) findViewById(R.id.playingMusicArtist)).setText(MusicPlayer.getCurrentSong().getArtist());
                    ((TextView) findViewById(R.id.current)).setText(MusicPlayer.getCurrentPositionString() + " - " + MusicPlayer.getDurationString());
                    ((SeekBar) findViewById(R.id.playingProgress)).setProgress(MusicPlayer.getCurrentPosition());
                }
                handler.postDelayed(this, 1000);
            }
        }, 1000);

        final MaterialButton btnPlayPause = findViewById(R.id.btnPlayPause);
        if (MusicPlayer.isPlaying()) {
            btnPlayPause.setIcon(getDrawable(R.drawable.ic_pause_black_48dp));
        } else {
            btnPlayPause.setIcon(getDrawable(R.drawable.ic_play_arrow_black_48dp));
        }
        btnPlayPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MusicPlayer.isPlaying()) {
                    MusicPlayer.pause();
                    btnPlayPause.setIcon(getDrawable(R.drawable.ic_play_arrow_black_48dp));
                } else {
                    MusicPlayer.resume();
                    btnPlayPause.setIcon(getDrawable(R.drawable.ic_pause_black_48dp));
                }
            }
        });

        findViewById(R.id.btnNext).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MusicPlayer.next();
                setSongInfo();
            }
        });

        findViewById(R.id.btnPrevious).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MusicPlayer.previous();
                setSongInfo();
            }
        });

        ((SeekBar) findViewById(R.id.playingProgress)).setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                MusicPlayer.seek(seekBar.getProgress());
            }
        });
    }

    private void setSongInfo() {
        Song song = MusicPlayer.getCurrentSong();
        ((TextView) findViewById(R.id.playingMusicTitle)).setText(song.getTitle());
        ((TextView) findViewById(R.id.playingMusicArtist)).setText(song.getArtist());
        SeekBar playingProgress = findViewById(R.id.playingProgress);
        playingProgress.setMax(MusicPlayer.getDuration());
        playingProgress.setProgress(MusicPlayer.getCurrentPosition());
    }

    private void setToolbar() {
        Toolbar toolbar = findViewById(R.id.playingToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");

        findViewById(R.id.btnBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}
