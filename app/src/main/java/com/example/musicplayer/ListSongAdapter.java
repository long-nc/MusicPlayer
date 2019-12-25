package com.example.musicplayer;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ListSongAdapter extends RecyclerView.Adapter<ListSongAdapter.ViewHolder> {

    private Context mContext;
    private List<Song> songs;

    public ListSongAdapter(Context context, List<Song> songs) {
        this.mContext = context;
        this.songs = songs;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewHolder vh = new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.song_item, parent, false));
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Song song = songs.get(position);
        holder.title.setText(song.getTitle());
        holder.artist.setText(song.getArtist());
        holder.pos = position;
    }

    @Override
    public int getItemCount() {
        return songs.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        int pos;
        TextView title;
        TextView artist;
        ImageButton more;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.songTitle);
            artist = itemView.findViewById(R.id.songArtist);
            more = itemView.findViewById(R.id.btnMore);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            MusicPlayer.play(pos);
            mContext.startActivity(new Intent(mContext, PlayingActivity.class));
        }
    }
}
