package com.zh.music.adapter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zh.music.MyApplication;
import com.zh.music.R;
import com.zh.music.bean.MusicMedia;
import com.zh.music.fragment.SDMusicFragment;

import java.util.List;

public class SDMusicFragmentAdapter extends RecyclerView.Adapter<SDMusicFragmentAdapter.ViewHolder> {

    String TAG = "SDMusicFragmentAdapter";
    private List<MusicMedia> musicMediaList;
    private SDMusicFragment.OnSDMusicFragmentListener sdMusicFragmentListener;


    public SDMusicFragmentAdapter(SDMusicFragment.OnSDMusicFragmentListener sdMusicFragmentListener1, List<MusicMedia> musicMediaList1) {
        Log.i(TAG, "onBindViewHolder: " + musicMediaList1.size());
        sdMusicFragmentListener = sdMusicFragmentListener1;
        musicMediaList = musicMediaList1;
    }


    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        holder.musicMedia = musicMediaList.get(position);
        Glide.with(MyApplication.getInstance()).load(holder.musicMedia.getAlbumBytes()).into(holder.iv_Image);
        holder.tv_Name.setText(holder.musicMedia.getTitle());
        holder.tv_ArtistAndAlbum.setText(holder.musicMedia.getArtist() + " | " + holder.musicMedia.getAlbumTitle());
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != sdMusicFragmentListener) {
                    sdMusicFragmentListener.getSDMusicItemResult(holder.musicMedia);
                }
            }
        });
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_sdmusic_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return musicMediaList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public MusicMedia musicMedia;
        public View mView;
        public ImageView iv_Image;
        public TextView tv_Name;
        public TextView tv_ArtistAndAlbum;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            iv_Image = view.findViewById(R.id.music_image);
            tv_Name = view.findViewById(R.id.music_name);
            tv_ArtistAndAlbum = view.findViewById(R.id.music_artist_song_album_);
        }
    }
}
