package com.zh.music.util;

import com.google.gson.Gson;
import com.zh.music.gson.SearchResult;

import org.json.JSONObject;

public class Utility {

    /**
     * 将返回的JSON数据解析成SearchResult实体类
     */
    public static SearchResult handleSearchResultResponse(String response) {
        try {
            return new Gson().fromJson(response, SearchResult.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String handleGetRealUrlResponse(String response, String musicType) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            return jsonObject.getString(musicType);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String handleGetRealImageUrl(String albumMid) {
        try {
            return "https://y.gtimg.cn/music/photo_new/T002R300x300M000" + albumMid + ".jpg?max_age=2592000";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
