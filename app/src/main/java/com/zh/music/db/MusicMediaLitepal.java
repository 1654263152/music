package com.zh.music.db;

import android.util.Log;


import com.zh.music.bean.MusicMedia;

import org.litepal.crud.DataSupport;

import java.util.Iterator;
import java.util.List;

public class MusicMediaLitepal {

    static String TAG = "MusicMediaLitepal";

    public static void createNewMusicMedia(MusicMedia musicmedia) {
        Log.i(TAG, "createNewMusicMedia: " + "添加一条记录" + musicmedia.getTitle());
        musicmedia.save();
    }

    public static void createMusicMediaList(List<MusicMedia> musicMediaList) {

        Iterator<MusicMedia> iterator = musicMediaList.iterator();
        while (iterator.hasNext()) {
            iterator.next().save();
        }
    }


    public static List<MusicMedia> queryMusicMediasAll() {
        List<MusicMedia> musicmediaList = DataSupport.findAll(MusicMedia.class);
        Log.i(TAG, "queryMusicMediasAll: " + "查询到" + musicmediaList.size());
        return musicmediaList;
    }

    public static void updateMusicMedia(MusicMedia musicmedia) {
        musicmedia.updateAll("id = ?", musicmedia.getId() + "");

    }
    public static int deleteMusicMedia(int musicmediaId) {
        int ret = 1;
        try {
            DataSupport.deleteAll(MusicMedia.class, "id = ?", musicmediaId + "");
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
        return ret;
    }

    public static void deleteMusicMedia() {
        DataSupport.deleteAll(MusicMedia.class);
    }
}
