package com.zh.music.bean;

import android.graphics.Bitmap;

import org.litepal.crud.DataSupport;

/**
 * 音乐信息
 */
public class MusicMedia extends DataSupport {
    private int id;
    private String title;
    private String artist;
    private String url;
    private String time;
    private String size;
    private int albumId;
    private String albumTitle;
    private byte[] albumBytes;
    private String albumMid;
    private String musicMid;
    private String mid;
    private String authorAlbumName;
    private Bitmap thumbBitmap;

    public MusicMedia() {
    }

    public MusicMedia(String title, String authorAlbumName, String albumMid, String musicMid, String mid) {
        this.title = title;
        this.albumMid = albumMid;
        this.musicMid = musicMid;
        this.mid = mid;
        this.authorAlbumName = authorAlbumName;
    }

    public Bitmap getThumbBitmap() {
        return thumbBitmap;
    }

    public void setThumbBitmap(Bitmap thumbBitmap) {
        this.thumbBitmap = thumbBitmap;
    }

    public String getAuthorAlbumName() {
        return authorAlbumName;
    }

    public void setAuthorAlbumName(String authorAlbumName) {
        this.authorAlbumName = authorAlbumName;
    }

    public String getAlbumTitle() {
        return albumTitle;
    }

    public void setAlbumTitle(String albumTitle) {
        this.albumTitle = albumTitle;
    }

    public String getMusicMid() {
        return musicMid;
    }

    public void setMusicMid(String musicMid) {
        this.musicMid = musicMid;
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public String getAlbumMid() {
        return albumMid;
    }

    public void setAlbumMid(String albumMid) {
        this.albumMid = albumMid;
    }

    public byte[] getAlbumBytes() {
        return albumBytes;
    }

    public void setAlbumBytes(byte[] albumBytes) {
        this.albumBytes = albumBytes;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setSize(String size) {
        this.size = size;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTime() {
        return time;
    }

    //格式化时间
    public void setTime(int time) {
        time /= 1000;
        int minute = time / 60;
        int hour = minute / 60;
        int second = time % 60;
        minute %= 60;
        this.time = String.format("%02d:%02d", minute, second);
    }

    public String getSize() {
        return size;
    }

    public void setSize(Long size) {
        long kb = 1024;
        long mb = kb * 1024;
        long gb = mb * 1024;
        if (size >= gb) {
            this.size = String.format("%.1f GB", (float) size / gb);
        } else if (size >= mb) {
            float f = (float) size / mb;
            this.size = String.format(f > 100 ? "%.0f MB" : "%.1f MB", f);
        } else if (size >= kb) {
            float f = (float) size / kb;
            this.size = String.format(f > 100 ? "%.0f KB" : "%.1f KB", f);
        } else
            this.size = String.format("%d B", size);
    }

    public int getAlbumId() {
        return albumId;
    }

    public void setAlbumId(int albumId) {
        this.albumId = albumId;
    }

}

