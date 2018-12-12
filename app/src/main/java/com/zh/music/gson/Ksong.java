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
public class Ksong {

//    private long id;
@SerializedName("mid")
    private String mid;
//    public void setId(long id) {
//         this.id = id;
//     }
//     public long getId() {
//         return id;
//     }

    public void setMid(String mid) {
         this.mid = mid;
     }
     public String getMid() {
         return mid;
     }

}