package com.zh.music.db;


import com.zh.music.bean.Note;

import org.litepal.crud.DataSupport;

import java.util.List;


public class NoteLitepal {//记录信息存储


    /**
     * 新建记录
     */
    public static void createNewNote(Note note) {
        Note newGroup = note;
        newGroup.save();
    }

    /**
     * 查询所有记录
     */
    public static List<Note> queryNoteAll() {
        List<Note> noteList = DataSupport.findAll(Note.class);
        return noteList;
    }
}
