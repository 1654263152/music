/**
 * Copyright 2018 bejson.com
 */
package com.zh.music.gson;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Auto-generated: 2018-10-01 19:14:21
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class Song {
    @SerializedName("curnum")
    private int curnum;

    @SerializedName("curpage")
    private int curpage;

    @SerializedName("list")
    private List<ListSong> listSongs;

    @SerializedName("totalnum")
    private int totalnum;

    public int getCurpage() {
        return curpage;
    }

    public void setCurpage(int curpage) {
        this.curpage = curpage;
    }

    public int getCurnum() {
        return curnum;
    }

    public void setCurnum(int curnum) {
        this.curnum = curnum;
    }

    public List<ListSong> getListSongs() {
        return listSongs;
    }

    public void setListSongs(List<ListSong> listSongs) {
        this.listSongs = listSongs;
    }

    public int getTotalnum() {
        return totalnum;
    }

    public void setTotalnum(int totalnum) {
        this.totalnum = totalnum;
    }

}