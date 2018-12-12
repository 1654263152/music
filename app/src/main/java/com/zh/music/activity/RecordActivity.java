package com.zh.music.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.zh.music.R;
import com.zh.music.adapter.RecordAdapter;
import com.zh.music.bean.Note;
import com.zh.music.db.NoteLitepal;

import java.util.ArrayList;
import java.util.List;


public class RecordActivity extends BaseActity {

    private List<Note> noteList = new ArrayList<Note>();
    private RecordAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);
        RecyclerView recyclerView = findViewById(R.id.record_recycler);
        LinearLayoutManager layoutManager = new
                LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        noteList = NoteLitepal.queryNoteAll();
        adapter = new RecordAdapter(this, noteList);
        recyclerView.setAdapter(adapter);
    }

}
