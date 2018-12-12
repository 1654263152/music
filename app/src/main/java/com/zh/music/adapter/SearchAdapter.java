package com.zh.music.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.zh.music.MyApplication;
import com.zh.music.R;
import com.zh.music.bean.MusicMedia;
import com.zh.music.util.HttpUtil;
import com.zh.music.util.Utility;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {

    private static final String TAG = "SearchAdapter";
    private com.zh.music.mInterface.playingInterface playingInterface;
    private Context mContext;
    private List<MusicMedia> musicMediaList;
    private String msuicSingerAndAlbum = "";
    private String musicTitle = "";
    private String musicImageUrl = "";
    private String msuicRealUrl = "";


    public SearchAdapter(List<MusicMedia> musicMediaList1) {
        this.musicMediaList = musicMediaList1;
    }

    public void setPlayingInterface(com.zh.music.mInterface.playingInterface Interface) {
        this.playingInterface = Interface;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.search_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                MusicMedia musicMedia = musicMediaList.get(position);
                msuicSingerAndAlbum = musicMedia.getAuthorAlbumName();
                musicTitle = musicMedia.getTitle();
                musicImageUrl = Utility.handleGetRealImageUrl(musicMedia.getAlbumMid());

                //逻辑
                requestMusicRealUrl(musicMedia.getMusicMid(), musicMedia.getMid(), "mp3_128k");//mp3_128k
            }
        });
        holder.view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                int position = holder.getAdapterPosition();
                MusicMedia musicMedia = musicMediaList.get(position);
                requestMusicRealUrl2(musicMedia, musicMedia.getMusicMid(), musicMedia.getMid(), "mp3_128k");//mp3_128k
                return true;
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MusicMedia musicMedia = musicMediaList.get(position);
        Glide.with(mContext).load(Utility.handleGetRealImageUrl(musicMedia.getAlbumMid())).into(holder.MusicAlbumImage);
        holder.MusicTitle.setText(musicMedia.getTitle());
        holder.MusicAuthorAlbumName.setText(musicMedia.getAuthorAlbumName());//beizhu
    }

    @Override
    public int getItemCount() {
        return musicMediaList.size();
    }

    public void requestMusicRealUrl(String songsMid, String mediaMid, final String musicType) {
        final String searchUrl = "http://118.24.85.15:8090/api/getRealUrl.php?songsMid="
                + songsMid + "&mediaMid=" + mediaMid + "&musicType=" + musicType;
        HttpUtil.sendOkHttpRequest(searchUrl, new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseText = response.body().string();
                Log.i("responseText", "onResponse: " + responseText);
                final String realurl = Utility.handleGetRealUrlResponse(responseText, musicType);
                Log.i("responseText", "onResponse: " + realurl);

                if (realurl != null && !realurl.equals("")) {
                    msuicRealUrl = realurl;
                    playingInterface.setUI(musicTitle, msuicSingerAndAlbum, musicImageUrl, msuicRealUrl);
                }
            }

            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }
        });
    }

    public void requestMusicRealUrl2(final MusicMedia musicMedia, String songsMid, String mediaMid, final String musicType) {
        final String searchUrl = "http://118.24.85.15:8090/api/getRealUrl.php?songsMid="
                + songsMid + "&mediaMid=" + mediaMid + "&musicType=" + musicType;
        HttpUtil.sendOkHttpRequest(searchUrl, new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseText = response.body().string();
                Log.i("responseText", "onResponse: " + responseText);
                final String realurl = Utility.handleGetRealUrlResponse(responseText, musicType);
                Log.i("responseText", "onResponse: " + realurl);

                if (realurl != null && !realurl.equals("")) {
                    msuicRealUrl = realurl;
                    Intent textIntent = new Intent(Intent.ACTION_SEND);
                    textIntent.setType("text/plain");
                    textIntent.putExtra(Intent.EXTRA_TEXT, "我正在听" + musicMedia.getArtist() + "的" + musicMedia.getTitle() + "\n" +
                            "来和我一起来听吧:" + "\n" + msuicRealUrl);
                    mContext.startActivity(Intent.createChooser(textIntent, "Music"));
                }
            }

            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                Toast.makeText(mContext, "播放失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        View view;
        ImageView MusicAlbumImage;
        TextView MusicTitle;
        TextView MusicAuthorAlbumName;

        public ViewHolder(View view) {
            super(view);
            this.view = view;
            MusicAlbumImage = view.findViewById(R.id.music_search_image);
            MusicTitle = view.findViewById(R.id.music_search_title);
            MusicAuthorAlbumName = view.findViewById(R.id.music_search_artist_album_);
        }
    }

}
