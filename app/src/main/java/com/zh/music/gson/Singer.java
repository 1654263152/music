/**
 * Copyright 2018 bejson.com
 */
package com.zh.music.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Auto-generated: 2018-10-01 19:14:21
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class Singer {

    //    private int id;
//    private String mid;
    private String name;

    //    private String title;
//    private String title_hilight;
//    private int type;
//    private int uin;
//    public void setId(int id) {
//         this.id = id;
//     }
//     public int getId() {
//         return id;
//     }
//
//    public void setMid(String mid) {
//         this.mid = mid;
//     }
//     public String getMid() {
//         return mid;
//     }
    @SerializedName("name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

//    public void setName(String title) {
//         this.title = title;
//     }
//     public String getName() {
//         return title;
//     }
//
//    public void setTitle_hilight(String title_hilight) {
//         this.title_hilight = title_hilight;
//     }
//     public String getTitle_hilight() {
//         return title_hilight;
//     }
//
//    public void setType(int type) {
//         this.type = type;
//     }
//     public int getType() {
//         return type;
//     }
//
//    public void setUin(int uin) {
//         this.uin = uin;
//     }
//     public int getUin() {
//         return uin;
//     }

}