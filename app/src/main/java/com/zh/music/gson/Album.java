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
public class Album {

    //   private long id;
    @SerializedName("mid")
    private String mid;

    @SerializedName("name")
    private String name;

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    //    private String subtitle;
//    private String title;
//    private String title_hilight;
//    public void setId(long id) {
//         this.id = id;
//     }
//     public long getId() {
//         return id;
//     }
//

//    public void setSubtitle(String subtitle) {
//         this.subtitle = subtitle;
//     }
//     public String getSubtitle() {
//         return subtitle;
//     }
//
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

}