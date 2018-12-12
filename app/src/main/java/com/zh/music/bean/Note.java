package com.zh.music.bean;

import android.graphics.Bitmap;

import org.litepal.crud.DataSupport;

import java.io.Serializable;

public class Note extends DataSupport implements Serializable {
    private int id;
    private String title;
    private String singerAndAlbum;

    public Note() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Note(String title, String singerAndAlbum) {
        this.title = title;
        this.singerAndAlbum = singerAndAlbum;
    }

    public String getSingerAndAlbum() {
        return singerAndAlbum;
    }

    public void setSingerAndAlbum(String singerAndAlbum) {
        this.singerAndAlbum = singerAndAlbum;
    }
}
