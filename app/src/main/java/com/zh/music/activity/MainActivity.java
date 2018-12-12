package com.zh.music.activity;

import android.Manifest;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.zh.music.MyApplication;
import com.zh.music.R;
import com.zh.music.adapter.SearchAdapter;
import com.zh.music.bean.MusicMedia;
import com.zh.music.bean.Note;
import com.zh.music.db.NoteLitepal;
import com.zh.music.fragment.SDMusicFragment;
import com.zh.music.gson.ListSong;
import com.zh.music.gson.SearchResult;
import com.zh.music.mInterface.playingInterface;
import com.zh.music.service.MediaService;
import com.zh.music.util.HttpUtil;
import com.zh.music.util.Utility;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MainActivity extends BaseActity
        implements SDMusicFragment.OnSDMusicFragmentListener, playingInterface {
    public static MediaService.MyBinder mMyBinder;
    private static String TAG = "MainActivity";
    private final int REQUEST_CODE = 1;
    private final String[] PERMISSIONS_STORAGE = {
            "android.permission.READ_EXTERNAL_STORAGE"};
    GridLayoutManager layoutManager;
    private SearchAdapter adapter;
    private List<MusicMedia> list;
    private FrameLayout sdFrame;
    private RelativeLayout rr_layout;
    private Intent MediaServiceIntent;
    private View searchLayout;
    private RecyclerView searchRecyclerView;
    private String keyword = "";
    private int curpage;
    private SearchView mSearchView;
    private long mExitTime;
    private Toolbar toolbar;
    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mMyBinder = (MediaService.MyBinder) service;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };
    private ImageView iv_album;
    Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    ib_statu.setImageResource(R.drawable.ic_pause);
                    Glide.with(MainActivity.this).load(msg.obj).into(iv_album);
                    break;
            }
            super.handleMessage(msg);
        }
    };
    private ImageButton ib_statu;
    private TextView tv_name;
    private TextView tv_album;
    private SDMusicFragment sdMusicFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MediaServiceIntent = new Intent(this, MediaService.class);
        bindService(MediaServiceIntent, mServiceConnection, BIND_AUTO_CREATE);
        initView();
        checkPermission();
    }

    public void initFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        sdMusicFragment = new SDMusicFragment();
        transaction.add(R.id.sd_frame, sdMusicFragment);
        transaction.show(sdMusicFragment);
        transaction.commit();
    }

    private void initView() {

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        iv_album = findViewById(R.id.control_album_image);
        tv_name = findViewById(R.id.control_song_name);
        tv_album = findViewById(R.id.control_singerandalbum);
        ib_statu = findViewById(R.id.controler_status);

        LayoutInflater inflater = getLayoutInflater();
        searchLayout = inflater.inflate(R.layout.search_layout, null);
        list = new ArrayList<>();
        rr_layout = findViewById(R.id.rr);
        sdFrame = findViewById(R.id.sd_frame);
        mSearchView = searchLayout.findViewById(R.id.msearch);
        searchRecyclerView = searchLayout.findViewById(R.id.search_recycler_view);
        layoutManager = new GridLayoutManager(getApplicationContext(), 1);
        searchRecyclerView.setLayoutManager(layoutManager);
        adapter = new SearchAdapter(list);
        adapter.setPlayingInterface(this);
        searchRecyclerView.setAdapter(adapter);
        initLoadMoreListener();
        mSearchView.setIconified(false);
        mSearchView.setSubmitButtonEnabled(true);
        mSearchView.setQueryHint("Everything......");
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                setunvisibility();
                curpage = 1;
                list.clear();
                keyword = query;
                requestSearch(keyword, curpage);
                mSearchView.clearFocus();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    void checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, PERMISSIONS_STORAGE, REQUEST_CODE);
            } else {
                initFragment();
            }
        }

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    bindService(MediaServiceIntent, mServiceConnection, BIND_AUTO_CREATE);
                    initFragment();
                } else {
                }
                break;
        }
    }

    private void initLoadMoreListener() {

        searchRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            int lastVisibleItem;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem + 1 == adapter.getItemCount()) {
                    curpage = curpage + 1;
                    requestSearch(keyword, curpage);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                lastVisibleItem = layoutManager.findLastVisibleItemPosition();
            }
        });
    }


    public void play(View view) {
        if (MediaService.mMediaPlayer.isPlaying()) {
            mMyBinder.pauseMusic();
            ib_statu.setImageResource(R.drawable.ic_play);
        } else {
            mMyBinder.playMusic();
            ib_statu.setImageResource(R.drawable.ic_pause);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_main_menu, menu);
        return true;
    }

    void setunvisibility() {
        rr_layout.removeView(sdFrame);
        toolbar.setVisibility(View.GONE);
        rr_layout.removeViewInLayout(searchLayout);
        rr_layout.addView(searchLayout);
    }

    void setvisibility() {
        rr_layout.removeViewInLayout(searchLayout);
        rr_layout.addView(sdFrame);
        toolbar.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.ab_search:
                setunvisibility();
                break;
            case R.id.ab_record:
                Intent intent = new Intent(this, RecordActivity.class);
                startActivity(intent);
                break;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void getSDMusicItemResult(MusicMedia media) {
        mMyBinder.iniMediaPlayer(media.getUrl());
        NoteLitepal.createNewNote(new Note(media.getTitle(), media.getAuthorAlbumName()));
        updateMusicControl(
                media.getTitle(),
                media.getArtist() + " | " + media.getAlbumTitle(),
                media.getAlbumBytes());
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {

            if (toolbar.getVisibility() == View.GONE) {
                setvisibility();
            }
            else if ((System.currentTimeMillis() - mExitTime) > 2000) {
                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                mExitTime = System.currentTimeMillis();
            } else {
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void setUI(String title, String singerAndAlbum, String ImageUrl, String realUrl) {
        mMyBinder.iniMediaPlayer(realUrl);
        NoteLitepal.createNewNote(new Note(title, singerAndAlbum));
        updateMusicControl(title, singerAndAlbum, ImageUrl);
        Log.i(TAG, "setUI: " + ImageUrl);
    }

    @Override
    protected void onDestroy() {
        mMyBinder.closeMedia();
        unbindService(mServiceConnection);
        super.onDestroy();
    }


    public void requestSearch(String keyword, final int page) {
        String searchUrl = "http://118.24.85.15:8090/api/search.php?keyword=" + keyword + "&page=" + String.valueOf(page);
        HttpUtil.sendOkHttpRequest(searchUrl, new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseText = response.body().string();
                Log.i("responseText", "onResponse: " + responseText);
                final SearchResult searchResult = Utility.handleSearchResultResponse(responseText);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (searchResult != null) {
                            showSearchResult(searchResult);
                        } else {
                            Toast.makeText(MainActivity.this, "失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }

            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        toast("网络异常！");
                    }
                });
            }
        });
    }

    void showSearchResult(SearchResult searchResult) {
        for (ListSong song : searchResult.getData().getSong().getListSongs()) {
            MusicMedia musicMedia = new MusicMedia(
                    song.getTitle(),
                    song.getSinger().get(0).getName() + " | " + song.getAlbum().getName(),
                    song.getAlbum().getMid(),
                    song.getKsong().getMid(),
                    song.getMid());
            musicMedia.setArtist(song.getSinger().get(0).getName());
            list.add(musicMedia);
        }
        adapter.notifyDataSetChanged();
    }


    public void updateMusicControl(String title, String albumartist, final String url) {
        tv_name.setText(title);
        tv_album.setText(albumartist);
        Message message = new Message();
        message.obj = url;
        message.what = 1;
        mHandler.sendMessage(message);
    }

    public void updateMusicControl(String title, String albumartist, byte[] bytes) {
        tv_name.setText(title);
        tv_album.setText(albumartist);
        if (bytes.length == 0) {
            Glide.with(this).load(R.drawable.icon).into(iv_album);
        } else
            Glide.with(this).load(bytes).into(iv_album);
        Glide.with(this).load(R.drawable.ic_pause).into(ib_statu);
    }
}
