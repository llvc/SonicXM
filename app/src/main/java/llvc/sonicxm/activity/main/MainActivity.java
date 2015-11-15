package llvc.sonicxm.activity.main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.InputType;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import net.simonvt.menudrawer.MenuDrawer;

import java.util.ArrayList;
import java.util.List;

import llvc.sonicxm.R;
import llvc.sonicxm.activity.login.LoginActivity;
import llvc.sonicxm.model.Config;
import llvc.sonicxm.tracklist.CustomListViewAdapter;
import llvc.sonicxm.tracklist.TrackItem;

/**
 * Created by a1 on 11/7/2015.
 */
public class MainActivity extends Activity implements View.OnClickListener, AdapterView.OnItemClickListener, View.OnFocusChangeListener {

    private ImageButton menuExplorerButton;

    private LinearLayout dashboardLayout;
    private GridView albumGridView;
    private AlbumAdapter albumAdapter;
    private EditText searchEditText;

    private RelativeLayout trackListLayout;
    private ListView trackListView;
    private SeekBar musicProgressBar;
    private ImageButton trackListMenuButton;

    private LinearLayout musicPlayerLayout;
    private LinearLayout profileLayout;
    private SeekBar musicSeekbar;
    private SeekBar volumeBar;
    private ImageButton playerMenuButton;
    private ImageButton playerCloseButton;

    private MenuDrawer mDrawer;
    private TextView profileTextView;
    private TextView dashboardTextView;
    private TextView logoutTextView;

    //profile
    private ImageButton menuProfileButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();

        switchView();
        setupAction();
    }

    private void initView() {
        //setup menu
        mDrawer = MenuDrawer.attach(this);
        mDrawer.setContentView(R.layout.main_layout);
        mDrawer.setMenuView(R.layout.outsidemenu_layout);

        profileTextView = (TextView) mDrawer.findViewById(R.id.profileTextView);
        dashboardTextView = (TextView) mDrawer.findViewById(R.id.dashboardTextView);
        logoutTextView = (TextView) mDrawer.findViewById(R.id.logoutTextView);

        //dashboard
        dashboardLayout = (LinearLayout) findViewById(R.id.dashboardLayout);
        menuExplorerButton = (ImageButton) findViewById(R.id.menuExplorerButton);
        searchEditText = (EditText) findViewById(R.id.searchEditText);

        albumGridView = (GridView) findViewById(R.id.albumGridView);
        albumAdapter = new AlbumAdapter(this);
        albumGridView.setAdapter(albumAdapter);

        //track list
        trackListLayout = (RelativeLayout) findViewById(R.id.trackListLayout);
        trackListView = (ListView) findViewById(R.id.trackListView);
        musicProgressBar = (SeekBar) findViewById(R.id.musicProgressbar);
        trackListMenuButton = (ImageButton) findViewById(R.id.trackListMenuButton);

        CustomListViewAdapter customListViewAdapter = new CustomListViewAdapter(this, R.layout.track_cell, getTrackList());
        trackListView.setAdapter(customListViewAdapter);

        //music player
        musicPlayerLayout = (LinearLayout) findViewById(R.id.musicPlayerLayout);
        musicSeekbar = (SeekBar) findViewById(R.id.musicSeekbar);
        volumeBar = (SeekBar) findViewById(R.id.volumeBar);
        playerMenuButton = (ImageButton) findViewById(R.id.playerMenuButton);
        playerCloseButton = (ImageButton) findViewById(R.id.playerCloseButton);

        //profile
        profileLayout = (LinearLayout) findViewById(R.id.profileLayout);
        menuProfileButton = (ImageButton) findViewById(R.id.menuProfileButton);
    }

    private List<TrackItem> getTrackList() {
        List<TrackItem> items = new ArrayList<TrackItem>();

        for (int i = 0; i < 20; i++) {
            TrackItem item = new TrackItem();
            item.trackTitle = "Stay with me";
            item.artist = "Sam smith";
            if (i == 2 || i == 5) {
                item.canDownload = true;
            } else {
                item.canDownload = false;
            }

            items.add(item);
        }

        return items;
    }

    private void switchView() {
        if (this.getIntent().getIntExtra(Config.VIEW, Config.DASHBOARD) == Config.DASHBOARD) {
            showDashboard();
        }
    }

    private void showDashboard() {
        dashboardLayout.setVisibility(View.VISIBLE);
        trackListLayout.setVisibility(View.GONE);
        musicPlayerLayout.setVisibility(View.GONE);
        profileLayout.setVisibility(View.GONE);
    }

    private void showProfile() {
        dashboardLayout.setVisibility(View.GONE);
        trackListLayout.setVisibility(View.GONE);
        musicPlayerLayout.setVisibility(View.GONE);
        profileLayout.setVisibility(View.VISIBLE);

        hideSoftKeyboard(searchEditText);
    }

    private void showTrackList() {
        dashboardLayout.setVisibility(View.GONE);
        trackListLayout.setVisibility(View.VISIBLE);
        musicPlayerLayout.setVisibility(View.GONE);
        profileLayout.setVisibility(View.GONE);

        musicProgressBar.setProgress(50);
        hideSoftKeyboard(searchEditText);
    }

    private void showMusicPlayer() {
        dashboardLayout.setVisibility(View.GONE);
        trackListLayout.setVisibility(View.GONE);
        musicPlayerLayout.setVisibility(View.VISIBLE);
        profileLayout.setVisibility(View.GONE);

        musicSeekbar.setProgress(50);
        volumeBar.setProgress(50);
        hideSoftKeyboard(searchEditText);
    }

    private void setupAction() {
        //dashboard action
        menuExplorerButton.setOnClickListener(this);
        searchEditText.setOnFocusChangeListener(this);

        //menu action
        profileTextView.setOnClickListener(this);
        dashboardTextView.setOnClickListener(this);
        logoutTextView.setOnClickListener(this);

        //profile
        menuProfileButton.setOnClickListener(this);

        //track list
        trackListView.setOnItemClickListener(this);
        trackListMenuButton.setOnClickListener(this);

        //music
        playerMenuButton.setOnClickListener(this);
        playerCloseButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == menuExplorerButton || v == menuProfileButton) {
            hideSoftKeyboard(searchEditText);
            mDrawer.openMenu();
        } else if (v == profileTextView) {
            mDrawer.closeMenu();
            showProfile();
        } else if (v == dashboardTextView) {
            mDrawer.closeMenu();
            showDashboard();
        } else if (v == logoutTextView) {
            finish();
            Intent i = new Intent(getBaseContext(), LoginActivity.class);
            startActivity(i);
        } else if (v == playerMenuButton) {
            mDrawer.openMenu();
        } else if (v == playerCloseButton) {
            showTrackList();
        } else if (v == trackListMenuButton) {
            mDrawer.openMenu();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        showMusicPlayer();
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (v == searchEditText) {
            int inType = searchEditText.getInputType(); // backup the input type
            searchEditText.setInputType(InputType.TYPE_NULL); // disable soft input
            searchEditText.onWindowFocusChanged(hasFocus);
            searchEditText.setInputType(inType);
        }
    }

    public void hideSoftKeyboard(View v) {
        InputMethodManager inputMethodManager = (InputMethodManager)  getBaseContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    //Album Adapter
    public class AlbumAdapter extends BaseAdapter {
        private LayoutInflater mInflater;
        private ImageView detailPhotoView;

        public AlbumAdapter(Context c) {
            mContext = c;
        }

        public int getCount() {
            return 20;
        }

        public Object getItem(int position) {
            return position;
        }

        public long getItemId(int position) {
            return position;
        }

        public float convertPixelsToDp(float px, Context context){
            Resources resources = context.getResources();
            DisplayMetrics metrics = resources.getDisplayMetrics();
            float dp = px / (metrics.densityDpi / 160f);
            return dp;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView photoView;

            if (convertView == null) {
                photoView = new ImageView(mContext);
            } else {
                photoView = (ImageView) convertView;
            }

            photoView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    showTrackList();
                }
            });

//            photoView.setBackgroundResource(R.drawable.album_bg);
            photoView.setImageResource(R.drawable.album_bg);
            photoView.setAdjustViewBounds(true);
//            photoView.setMinimumHeight(100);

            return photoView;
        }

        class PhotoNum {
            int num;
        }

        private Context mContext;
    }

//    public class TrackListAdapter extends

}
