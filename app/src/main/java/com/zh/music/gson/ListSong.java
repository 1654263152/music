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
public class ListSong {//list

    //    private Action action;
    @SerializedName("album")
    private Album album;//专辑
    //    private int chinesesinger;
//    private String desc;
//    private String desc_hilight;
//    private String docid;
//    private File file;
//    private int fnote;
//    private int genre;
//    private List<String> grp;
//    private long id;
//    private int index_album;
//    private int index_cd;
//    private int interval;
//    private int isonly;
    @SerializedName("ksong")
    private Ksong ksong;//songmid
    //    private int language;
//    private String lyric;
//    private String lyric_hilight;
    @SerializedName("mid")
    private String mid;//mid
    //    private Mv mv;
//    private String name;
//    private int newStatus;
//    private int nt;
//    private Pay pay;
//    private int pure;
    @SerializedName("singer")
    private List<Singer> singer;
    //    private int status;
//    private String subtitle;
//    private int t;
//    private int tag;
//    private Date time_public;
    @SerializedName("title")
    private String title;//歌名
//    private String title_hilight;
//    private int type;
//    private String url;
//    private int ver;
//    private Volume volume;

    //    public Action getAction() {
//        return action;
//    }
//
//    public void setAction(Action action) {
//        this.action = action;
//    }
//
    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    //
//    public int getChinesesinger() {
//        return chinesesinger;
//    }
//
//    public void setChinesesinger(int chinesesinger) {
//        this.chinesesinger = chinesesinger;
//    }
//
//    public String getDesc() {
//        return desc;
//    }
//
//    public void setDesc(String desc) {
//        this.desc = desc;
//    }
//
//    public String getDesc_hilight() {
//        return desc_hilight;
//    }
//
//    public void setDesc_hilight(String desc_hilight) {
//        this.desc_hilight = desc_hilight;
//    }
//
//    public String getDocid() {
//        return docid;
//    }
//
//    public void setDocid(String docid) {
//        this.docid = docid;
//    }
//
//    public File getFile() {
//        return file;
//    }
//
//    public void setFile(File file) {
//        this.file = file;
//    }
//
//    public int getFnote() {
//        return fnote;
//    }
//
//    public void setFnote(int fnote) {
//        this.fnote = fnote;
//    }
//
//    public int getGenre() {
//        return genre;
//    }
//
//    public void setGenre(int genre) {
//        this.genre = genre;
//    }
//
//    public List<String> getGrp() {
//        return grp;
//    }
//
//    public void setGrp(List<String> grp) {
//        this.grp = grp;
//    }
//
//    public long getId() {
//        return id;
//    }
//
//    public void setId(long id) {
//        this.id = id;
//    }
//
//    public int getIndex_album() {
//        return index_album;
//    }
//
//    public void setIndex_album(int index_album) {
//        this.index_album = index_album;
//    }
//
//    public int getIndex_cd() {
//        return index_cd;
//    }
//
//    public void setIndex_cd(int index_cd) {
//        this.index_cd = index_cd;
//    }
//
//    public int getInterval() {
//        return interval;
//    }
//
//    public void setInterval(int interval) {
//        this.interval = interval;
//    }
//
//    public int getIsonly() {
//        return isonly;
//    }
//
//    public void setIsonly(int isonly) {
//        this.isonly = isonly;
//    }
//
    public Ksong getKsong() {
        return ksong;
    }

    public void setKsong(Ksong ksong) {
        this.ksong = ksong;
    }

    //
//    public int getLanguage() {
//        return language;
//    }
//
//    public void setLanguage(int language) {
//        this.language = language;
//    }
//
//    public String getLyric() {
//        return lyric;
//    }
//
//    public void setLyric(String lyric) {
//        this.lyric = lyric;
//    }
//
//    public String getLyric_hilight() {
//        return lyric_hilight;
//    }
//
//    public void setLyric_hilight(String lyric_hilight) {
//        this.lyric_hilight = lyric_hilight;
//    }
//
    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    //
//    public Mv getMv() {
//        return mv;
//    }
//
//    public void setMv(Mv mv) {
//        this.mv = mv;
//    }
//
//    public String getSongName() {
//        return name;
//    }
//
//    public void setSongName(String name) {
//        this.name = name;
//    }
//
//    public int getNewStatus() {
//        return newStatus;
//    }
//
//    public void setNewStatus(int newStatus) {
//        this.newStatus = newStatus;
//    }
//
//    public int getNt() {
//        return nt;
//    }
//
//    public void setNt(int nt) {
//        this.nt = nt;
//    }
//
//    public Pay getPay() {
//        return pay;
//    }
//
//    public void setPay(Pay pay) {
//        this.pay = pay;
//    }
//
//    public int getPure() {
//        return pure;
//    }
//
//    public void setPure(int pure) {
//        this.pure = pure;
//    }
//
    public List<Singer> getSinger() {
        return singer;
    }

    public void setSinger(List<Singer> singer) {
        this.singer = singer;
    }

    //
//    public int getStatus() {
//        return status;
//    }
//
//    public void setStatus(int status) {
//        this.status = status;
//    }
//
//    public String getSubtitle() {
//        return subtitle;
//    }
//
//    public void setSubtitle(String subtitle) {
//        this.subtitle = subtitle;
//    }
//
//    public int getT() {
//        return t;
//    }
//
//    public void setT(int t) {
//        this.t = t;
//    }
//
//    public int getTag() {
//        return tag;
//    }
//
//    public void setTag(int tag) {
//        this.tag = tag;
//    }
//
//    public Date getTime_public() {
//        return time_public;
//    }
//
//    public void setTime_public(Date time_public) {
//        this.time_public = time_public;
//    }
//
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
//
//    public String getTitle_hilight() {
//        return title_hilight;
//    }
//
//    public void setTitle_hilight(String title_hilight) {
//        this.title_hilight = title_hilight;
//    }
//
//    public int getType() {
//        return type;
//    }
//
//    public void setType(int type) {
//        this.type = type;
//    }
//
//    public String getRealUrl() {
//        return url;
//    }
//
//    public void setRealUrl(String url) {
//        this.url = url;
//    }
//
//    public int getVer() {
//        return ver;
//    }
//
//    public void setVer(int ver) {
//        this.ver = ver;
//    }
//
//    public Volume getVolume() {
//        return volume;
//    }
//
//    public void setVolume(Volume volume) {
//        this.volume = volume;
//    }

}