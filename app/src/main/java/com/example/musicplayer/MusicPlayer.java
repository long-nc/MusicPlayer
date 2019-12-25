package com.example.musicplayer;

import android.content.Context;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.provider.MediaStore;

import java.util.ArrayList;
import java.util.List;

public class MusicPlayer {
    private static Context mContext;
    private static List<Song> songs;
    private static int currentPosition;
    private static MediaPlayer player;
    private static Song currentSong;

    public static void initPlayer(Context context) {
        mContext = context;
    }

    public static List<Song> getListSongs() {
        if (songs == null) {
            songs = loadAllSongs();
        }
        return songs;
    }

    private static List<Song> loadAllSongs() {
        String selection = MediaStore.Audio.Media.IS_MUSIC + " != 0";

        String[] projection = {
                MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.DURATION,
                MediaStore.Audio.Media.DATA
        };

        Cursor cursor = mContext.getContentResolver().query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                projection,
                selection,
                null,
                null);

        List<Song> songs = new ArrayList<>();
        while(cursor.moveToNext()){
            songs.add(new Song(cursor.getLong(0),cursor.getString(1), cursor.getString(2), cursor.getInt(3), cursor.getString(4)));
        }
        cursor.close();
        return songs;
    }

    public static Song getCurrentSong() {
        return currentSong;
    }

    public static boolean isPlaying() {
        if (player == null) return false;
        else return player.isPlaying();
    }

    public static int getDuration() {
        if (player == null) return 0;
        return player.getDuration() / 1000;
    }

    public static int getCurrentPosition() {
        if (player == null) return 0;
        return player.getCurrentPosition() / 1000;
    }

    public static void pause() { player.pause(); }
    public static void resume() { player.start(); }
    public static void seek(int pos) { player.seekTo(pos * 1000); }

    public static void playByPosition(int pos) {
        currentPosition = pos;
        if (currentPosition == songs.size()) currentPosition = 0;
        if (currentPosition == -1) currentPosition = songs.size() - 1;
        currentSong = songs.get(currentPosition);
        MediaPlayer newPlayer = MediaPlayer.create(mContext, Uri.parse(currentSong.getData()));
        if (player != null) {
            player.release();
            player = null;
        }
        player = newPlayer;
        player.start();
        player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                next();
            }
        });
    }

    public static void play(long id) {
        for (int i = 0; i < songs.size(); i++) {
            if (songs.get(i).getId() == id) {
                currentSong = songs.get(i);
                currentPosition = i;
                break;
            }
        }

        if (currentPosition == songs.size()) currentPosition = 0;
        if (currentPosition == -1) currentPosition = songs.size() - 1;
        MediaPlayer newPlayer = MediaPlayer.create(mContext, Uri.parse(currentSong.getData()));
        if (player != null) {
            player.release();
            player = null;
        }
        player = newPlayer;
        player.start();
        player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                next();
            }
        });
    }

    public static void next() {
        playByPosition(currentPosition + 1);
    }

    public static void previous() {
        playByPosition(currentPosition - 1);
    }

    public static String getDurationString() {
        return convertSecondsToTime(player.getDuration() / 1000);
    }

    public static String getCurrentPositionString() {
        return convertSecondsToTime(player.getCurrentPosition() / 1000);
    }

    private static String convertSecondsToTime(int totalSecs) {
        int hours = totalSecs / 3600;
        int minutes = (totalSecs % 3600) / 60;
        int seconds = totalSecs % 60;

        if (hours == 0) {
            return String.format("%02d:%02d", minutes, seconds);
        } else {
            return String.format("%02d:%02d:%02d", hours, minutes, seconds);
        }
    }
}
