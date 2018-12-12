package com.zh.music.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.zh.music.R;
import com.zh.music.bean.Note;

import java.util.List;

public class RecordAdapter extends RecyclerView.Adapter<RecordAdapter.ViewHolder> {
    private static final String TAG = "RecordAdapter";
    private Context mContext;
    private List<Note> mNoteList;

    public RecordAdapter(Context mContext, List<Note> mNoteList) {
        this.mContext = mContext;
        this.mNoteList = mNoteList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view = LayoutInflater.from(mContext).inflate(R.layout.record_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Note note = mNoteList.get(position);
        holder.itemView.setTag(note);
        holder.tv_title.setText(note.getTitle());
        holder.tv_artist.setText(note.getSingerAndAlbum());
    }

    @Override
    public int getItemCount() {
        if (mNoteList != null && mNoteList.size() > 0) {
            return mNoteList.size();
        }
        return 0;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_title;
        public TextView tv_artist;

        public ViewHolder(View view) {
            super(view);
            tv_title = view.findViewById(R.id.title);
            tv_artist = view.findViewById(R.id.artist_album);
        }
    }
}
