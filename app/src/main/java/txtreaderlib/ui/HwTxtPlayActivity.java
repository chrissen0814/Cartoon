package txtreaderlib.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.chrissen.cartoon.R;
import com.chrissen.cartoon.dao.greendao.Txt;
import com.chrissen.cartoon.dao.manager.TxtDaoManager;
import com.chrissen.cartoon.util.AnimUtil;

import java.io.File;
import java.util.Calendar;

import es.dmoral.toasty.Toasty;
import txtreaderlib.bean.TxtMsg;
import txtreaderlib.interfaces.IChapter;
import txtreaderlib.interfaces.ILoadListener;
import txtreaderlib.interfaces.IPageChangeListener;
import txtreaderlib.interfaces.IViewListener;
import txtreaderlib.main.TxtConfig;
import txtreaderlib.main.TxtReaderView;

/**
 * Created by bifan-wei
 * on 2017/12/8.
 */

public class HwTxtPlayActivity extends AppCompatActivity {
    private Handler mHandler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hwtxtpaly);
        hideBottomUIMenu();
        getIntentData();
        init();
        loadFile();
        registerListener();
    }

    private void hideBottomUIMenu() {
        //隐藏虚拟按键，并且全屏
        if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) { // lower api
            View v = this.getWindow().getDecorView();
            v.setSystemUiVisibility(View.GONE);
        } else if (Build.VERSION.SDK_INT >= 19) {
            //for new api versions.
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
        }
    }

    public static void LoadTxtFile(Context context, String FilePath) {
        LoadTxtFile(context, FilePath, FilePath);
    }

    public static void LoadTxtFile(Context context, String FilePath, String FileName) {
        Intent intent = new Intent();
        intent.putExtra("FilePath", FilePath);
        intent.putExtra("FileName", FileName);
        intent.setClass(context, HwTxtPlayActivity.class);
        context.startActivity(intent);
    }

    public static void loadTxt(Context context , Txt txt){
        Intent intent = new Intent();
        intent.putExtra("TXT",txt);
        intent.setClass(context,HwTxtPlayActivity.class);
        context.startActivity(intent);
    }

    private View mTopDecoration, mBottomDecoration;
    private TextView mChapterNameText;
    private TextView mChapterMenuText;
    private TextView mProgressText;
    private TextView mSettingText;
    private TxtReaderView mTxtReaderView;
    private MenuHolder mMenuHolder = new MenuHolder();
    private View mTopMenu;
    private View mBottomMenu;
    private View mCoverView;
    private ChapterList mChapterListPop;

    private void init() {
        mHandler = new Handler();
        mTopDecoration = findViewById(R.id.activity_hwtxtplay_top);
        mBottomDecoration = findViewById(R.id.activity_hwtxtplay_bottom);
        mTxtReaderView = (TxtReaderView) findViewById(R.id.activity_hwtxtplay_readerView);
        mChapterNameText = (TextView) findViewById(R.id.activity_hwtxtplay_chaptername);
        mChapterMenuText = (TextView) findViewById(R.id.activity_hwtxtplay_chapter_menutext);
        mProgressText = (TextView) findViewById(R.id.activity_hwtxtplay_progress_text);
        mSettingText = (TextView) findViewById(R.id.activity_hwtxtplay_setting_text);
        mTopMenu = findViewById(R.id.activity_hwtxtplay_menu_top);
        mBottomMenu = findViewById(R.id.activity_hwtxtplay_menu_bottom);
        mCoverView = findViewById(R.id.activity_hwtxtplay_cover);

        mMenuHolder.mTitle = (TextView) findViewById(R.id.txtreadr_menu_title);
        mMenuHolder.mPreChapter = (TextView) findViewById(R.id.txtreadr_menu_chapter_pre);
        mMenuHolder.mNextChapter = (TextView) findViewById(R.id.txtreadr_menu_chapter_next);
        mMenuHolder.mSeekBar = (SeekBar) findViewById(R.id.txtreadr_menu_seekbar);
        mMenuHolder.mTextSizeDel = (TextView) findViewById(R.id.txtreadr_menu_textsize_del);
        mMenuHolder.mTextSize = (TextView) findViewById(R.id.txtreadr_menu_textsize);
        mMenuHolder.mTextSizeAdd = (TextView) findViewById(R.id.txtreadr_menu_textsize_add);
        mMenuHolder.mTextSize = (TextView) findViewById(R.id.txtreadr_menu_textsize);
        mMenuHolder.mStyle1 = findViewById(R.id.hwtxtreader_menu_style1);
        mMenuHolder.mStyle2 = findViewById(R.id.hwtxtreader_menu_style2);
        mMenuHolder.mStyle3 = findViewById(R.id.hwtxtreader_menu_style3);
        mMenuHolder.mStyle5 = findViewById(R.id.hwtxtreader_menu_style5);
        setTopAndBottomLayout();
    }

    private final int[] StyleTextColors = new int[]{
            Color.parseColor("#000000"),
            Color.parseColor("#505550"),
            Color.parseColor("#453e33"),
            Color.parseColor("#ffffff")
    };

    private Txt mTxt;
    private String FilePath = null;
    private String FileName = null;

    private void getIntentData() {
        FilePath = getIntent().getStringExtra("FilePath");
        FileName = getIntent().getStringExtra("FileName");
        mTxt = (Txt) getIntent().getSerializableExtra("TXT");
    }

    private void loadFile() {

        if (mTxt == null) {
            if (TextUtils.isEmpty(FilePath) || !(new File(FilePath).exists())) {
                Toasty.error(this,"文件不存在",Toast.LENGTH_SHORT,false).show();
            }else {
                mTxtReaderView.loadTxtFile(FilePath, new ILoadListener() {
                    @Override
                    public void onSuccess() {
                        if (TextUtils.isEmpty(FileName)) {
                            FileName = mTxtReaderView.getTxtReaderContext().getFileMsg().FileName;
                        }
                        setBookName(FileName);
                        initWhenLoadDone();
                    }

                    @Override
                    public void onFail(TxtMsg txtMsg) {
                        toast(txtMsg + "");
                    }

                    @Override
                    public void onMessage(String message) {
                    }
                });
            }

        }else {
            if (!new File(mTxt.getFilePath()).exists()) {
                Toasty.error(this,"文件不存在",Toast.LENGTH_SHORT,false).show();
                new TxtDaoManager().deleteTxt(mTxt);
                finish();
            }else {
                mTxtReaderView.loadTxtFile(mTxt.getFilePath(), new ILoadListener() {
                    @Override
                    public void onSuccess() {
                        setBookName(mTxt.getName());
                        initWhenLoadDone();
                        initProgress();
                    }

                    @Override
                    public void onFail(TxtMsg txtMsg) {

                    }

                    @Override
                    public void onMessage(String message) {

                    }
                });
            }

        }

    }

    private void initProgress() {
        mTxtReaderView.loadFromProgress(mTxt.getCurrentParagraphIndex(),mTxt.getCharIndex());
    }

    private void initWhenLoadDone() {
        FileName = mTxtReaderView.getTxtReaderContext().getFileMsg().FileName;
        mMenuHolder.mTextSize.setText(mTxtReaderView.getTextSize() + "");
        mTopDecoration.setBackgroundColor(mTxtReaderView.getBackgroundColor());
        mBottomDecoration.setBackgroundColor(mTxtReaderView.getBackgroundColor());
        //章节设置
        if (mTxtReaderView.getChapters() != null) {
            WindowManager m = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
            DisplayMetrics metrics = new DisplayMetrics();
            m.getDefaultDisplay().getMetrics(metrics);
            int ViewHeight = metrics.heightPixels - mTopDecoration.getHeight();
            mChapterListPop = new ChapterList(this, ViewHeight, mTxtReaderView.getChapters(), mTxtReaderView.getTxtReaderContext().getParagraphData().getCharNum());

            mChapterListPop.getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    IChapter chapter = (IChapter) mChapterListPop.getAdapter().getItem(i);
                    mChapterListPop.dismiss();
                    mTxtReaderView.loadFromProgress(chapter.getStartParagraphIndex(), 0);
                }
            });
        }
    }

    private void registerListener() {
        mTopMenu.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });
        mBottomMenu.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });
        mCoverView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                Gone(mTopMenu, mBottomMenu, mCoverView);
                return true;
            }
        });

        mSettingText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Show(mTopMenu, mBottomMenu, mCoverView);
            }
        });

        mChapterMenuText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mChapterListPop.isShowing()) {
                    mChapterListPop.showAsDropDown(mTopDecoration);
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            IChapter currentChapter = mTxtReaderView.getCurrentChapter();
                            if (currentChapter != null) {
                                mChapterListPop.setCurrentIndex(currentChapter.getIndex());
                                mChapterListPop.notifyDataSetChanged();
                            }
                        }
                    }, 300);
                } else {
                    mChapterListPop.dismiss();
                }
            }
        });

        mMenuHolder.mSeekBar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    mTxtReaderView.loadFromProgress(mMenuHolder.mSeekBar.getProgress());
                }
                return false;
            }
        });

        mTxtReaderView.setPageChangeListener(new IPageChangeListener() {
            @Override
            public void onCurrentPage(float progress) {
                int p = (int) (progress * 1000);
                mProgressText.setText(((float) p / 10) + "%");
                mMenuHolder.mSeekBar.setProgress((int) (progress * 100));
                IChapter currentChapter = mTxtReaderView.getCurrentChapter();
                if (currentChapter != null) {
                    mChapterNameText.setText((currentChapter.getTitle() + "").trim());
                } else {
                    mChapterNameText.setText("无章节");
                }
            }
        });

        mTxtReaderView.setOnViewClickListener(new IViewListener() {
            @Override
            public void onViewClick() {
                setTopAndBottomLayout();
            }
        });


        mTopMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mChapterListPop.isShowing()) {
                    mChapterListPop.dismiss();
                }
            }
        });


        mMenuHolder.mPreChapter.setOnClickListener(new ChapterChangeClickListener(true));
        mMenuHolder.mNextChapter.setOnClickListener(new ChapterChangeClickListener(false));
        mMenuHolder.mTextSizeAdd.setOnClickListener(new TextChangeClickListener(true));
        mMenuHolder.mTextSizeDel.setOnClickListener(new TextChangeClickListener(false));
        mMenuHolder.mStyle1.setOnClickListener(new StyleChangeClickListener(ContextCompat.getColor(this, R.color.hwtxtreader_styleclor1), StyleTextColors[0]));
        mMenuHolder.mStyle2.setOnClickListener(new StyleChangeClickListener(ContextCompat.getColor(this, R.color.hwtxtreader_styleclor2), StyleTextColors[1]));
        mMenuHolder.mStyle3.setOnClickListener(new StyleChangeClickListener(ContextCompat.getColor(this, R.color.hwtxtreader_styleclor3), StyleTextColors[2]));
        mMenuHolder.mStyle5.setOnClickListener(new StyleChangeClickListener(ContextCompat.getColor(this, R.color.hwtxtreader_styleclor5), StyleTextColors[3]));

    }


    private void setTopAndBottomLayout(){
        if (mTopDecoration.getVisibility() == View.VISIBLE && mBottomDecoration.getVisibility() == View.VISIBLE) {
            AnimUtil.slideOutFromBottom(mBottomDecoration,HwTxtPlayActivity.this);
            AnimUtil.slideOutFromUp(mTopDecoration,HwTxtPlayActivity.this);
        }else {
            AnimUtil.slideInFromUp(mTopDecoration,HwTxtPlayActivity.this);
            AnimUtil.slideInFromBottom(mBottomDecoration,HwTxtPlayActivity.this);
        }
    }



    private class ChapterChangeClickListener implements View.OnClickListener {
        private Boolean Pre = false;

        public ChapterChangeClickListener(Boolean pre) {
            Pre = pre;
        }

        @Override
        public void onClick(View view) {
            if (Pre) {
                mTxtReaderView.jumpToPreChapter();
            } else {
                mTxtReaderView.jumpToNextChapter();
            }
        }
    }

    private class TextChangeClickListener implements View.OnClickListener {
        private Boolean Add = false;

        public TextChangeClickListener(Boolean pre) {
            Add = pre;
        }

        @Override
        public void onClick(View view) {
            int textSize = mTxtReaderView.getTextSize();
            if (Add) {
                if (textSize + 2 <= TxtConfig.MAX_TEXT_SIZE) {
                    mTxtReaderView.setTextSize(textSize + 2);
                    mMenuHolder.mTextSize.setText(textSize + 2 + "");
                }
            } else {
                if (textSize - 2 >= TxtConfig.MIN_TEXT_SIZE) {
                    mTxtReaderView.setTextSize(textSize - 2);
                    mMenuHolder.mTextSize.setText(textSize - 2 + "");
                }
            }

        }
    }

    private class StyleChangeClickListener implements View.OnClickListener {
        private int BgColor;
        private int TextColor;

        public StyleChangeClickListener(int bgColor, int textColor) {
            BgColor = bgColor;
            TextColor = textColor;
        }

        @Override
        public void onClick(View view) {
            mTxtReaderView.setStyle(BgColor, TextColor);
            mTopDecoration.setBackgroundColor(BgColor);
            mBottomDecoration.setBackgroundColor(BgColor);
        }
    }

    private void setBookName(String name) {
        mMenuHolder.mTitle.setText(name + "");
    }

    private void Show(View... views) {
        for (View v : views) {
            v.setVisibility(View.VISIBLE);
        }
    }

    private void Gone(View... views) {
        for (View v : views) {
            v.setVisibility(View.GONE);
        }
    }


    private Toast t;

    private void toast(final String msg) {
        if (t != null) {
            t.cancel();
        }
        t = Toast.makeText(HwTxtPlayActivity.this, msg, Toast.LENGTH_SHORT);
        t.show();
    }

    private class MenuHolder {
        TextView mTitle;
        TextView mPreChapter;
        TextView mNextChapter;
        SeekBar mSeekBar;
        TextView mTextSizeDel;
        TextView mTextSizeAdd;
        TextView mTextSize;
        View mStyle1;
        View mStyle2;
        View mStyle3;
        View mStyle5;
    }

    @Override
    protected void onPause() {
        super.onPause();
        mTxtReaderView.saveProgress();
        mTxt.setUpdatedTime(Calendar.getInstance().getTimeInMillis());
        mTxt.setCurrentParagraphIndex(mTxtReaderView.getTxtReaderContext().getFileMsg().CurrentParagraphIndex);
        mTxt.setCharIndex(mTxtReaderView.getTxtReaderContext().getFileMsg().CurrentCharIndex);
        new TxtDaoManager().updateTxt(mTxt);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mTxtReaderView.getTxtReaderContext().Clear();
    }

    public void BackClick(View view) {
        finish();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
