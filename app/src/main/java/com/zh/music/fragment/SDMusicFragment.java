package com.zh.music.fragment;


import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.zh.music.MyApplication;
import com.zh.music.R;
import com.zh.music.adapter.SDMusicFragmentAdapter;
import com.zh.music.bean.MusicMedia;
import com.zh.music.db.MusicMediaLitepal;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;


public class SDMusicFragment extends Fragment {

    protected TextView scan;
    protected RecyclerView recyclerView;
    String TAG = "SDMusicFragment";
    List<MusicMedia> mylist = new ArrayList<>();
    View view;
    long start;
    private OnSDMusicFragmentListener mListener;
    private SDMusicFragmentAdapter sdMusicFragmentAdapter;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    Log.i(TAG, "handleMessage: " + "扫描结束");
                    scan.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);//恢复
                    sdMusicFragmentAdapter.notifyDataSetChanged();
                    // Toast.makeText(getContext(), "扫描到" + mylist.size() + "首音乐", Toast.LENGTH_SHORT).show();
                    Log.i(TAG, "handleMessage: " + "扫描到" + mylist.size() + "首音乐");
                    Log.i(TAG, "handleMessage: +耗时" + String.valueOf((System.currentTimeMillis() - start) / 1000) + "秒");
                    break;
            }
        }
    };


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_sdmusic, container, false);
        recyclerView = view.findViewById(R.id.list);
        scan = view.findViewById(R.id.scan);
        mylist = MusicMediaLitepal.queryMusicMediasAll();
        sdMusicFragmentAdapter = new SDMusicFragmentAdapter(mListener, mylist);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(sdMusicFragmentAdapter);
        if (mylist.size() == 0) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    scanAllAudioFiles();
                }
            }).start();
        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnSDMusicFragmentListener) {
            mListener = (OnSDMusicFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnSDMusicFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /*加载媒体库里的音频*/
    public void scanAllAudioFiles() {
        scan.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        start = System.currentTimeMillis();
        mylist.clear();
        MusicMediaLitepal.deleteMusicMedia();
        Cursor cursor = MyApplication.getInstance().getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null, MediaStore.Audio.Media.DEFAULT_SORT_ORDER);
        if (cursor != null && cursor.getCount() == 0) {
            Log.i(TAG, "scanAllAudioFiles: " + "没有数据");
        } else if (cursor != null && cursor.moveToFirst()) {
            Log.i(TAG, "scanAllAudioFiles: " + "开始扫描");
            while (!cursor.isAfterLast()) {
                //歌曲标题
                String tilte = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE));
                //歌曲的专辑名：MediaStore.Audio.Media.ALBUM
                String album = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM));
                //歌曲的歌手名： MediaStore.Audio.Media.ARTIST
                String artist = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST));
                //歌曲文件的路径 ：MediaStore.Audio.Media.DATA
                String url = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA));
                //歌曲的总播放时长 ：MediaStore.Audio.Media.DURATION
                int duration = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION));
                //歌曲文件的大小 ：MediaStore.Audio.Media.SIZE
                Long size = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.SIZE));
                ////获取专辑ID
                int albumId = cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID));

                if (size > 1024 * 800) {//大于800K
                    MusicMedia musicMedia = new MusicMedia();
                    musicMedia.setArtist(artist);
                    musicMedia.setSize(size);
                    musicMedia.setTitle(tilte);
                    musicMedia.setTime(duration);
                    musicMedia.setUrl(url);
                    musicMedia.setAlbumTitle(album);
                    musicMedia.setAlbumId(albumId);
                    //  musicMedia.setThumbBitmap(getAlbumArtBitmap(musicMedia.getAlbumId()));
                    musicMedia.setAlbumBytes(getAlbumArtBytes(musicMedia.getAlbumId()));
                    mylist.add(musicMedia);
                    // Log.i(TAG, "scanAllAudioFiles: " + tilte);
                    MusicMediaLitepal.createNewMusicMedia(musicMedia);
                }
                cursor.moveToNext();
            }
        }
        if (cursor != null) {
            cursor.close();
        }
        Message message = new Message();
        message.what = 1;
        handler.sendMessage(message);
    }

    /**
     * 根据专辑ID获取专辑封面图
     *
     * @param album_id 专辑ID
     * @return
     */
    private byte[] getAlbumArtBytes(int album_id) {
        byte[] bytes = new byte[0];
        String mUriAlbums = "content://media/external/audio/albums";
        String[] projection = new String[]{"album_art"};
        Cursor cur = MyApplication.getInstance().getContentResolver().query(Uri.parse(mUriAlbums + "/" + Integer.toString(album_id)), projection, null, null, null);
        String album_art = null;
        if (cur != null && cur.getCount() > 0 && cur.getColumnCount() > 0) {
            cur.moveToNext();
            album_art = cur.getString(0);
        }
        cur.close();
        if (album_art != null) {
            Bitmap bm = BitmapFactory.decodeFile(album_art);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream(bm.getByteCount());
            bm.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
            return outputStream.toByteArray();
        } else {
            return bytes;
        }
    }

    public interface OnSDMusicFragmentListener {
        void getSDMusicItemResult(MusicMedia media);
    }

}


