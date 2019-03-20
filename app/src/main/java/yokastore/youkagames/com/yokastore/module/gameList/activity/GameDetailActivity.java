package yokastore.youkagames.com.yokastore.module.gameList.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.app.ProgressDialog;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import android.os.AsyncTask;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import android.os.Environment;
import java.io.File;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.w3c.dom.Text;

import yokastore.youkagames.com.yokastore.view.UpdateDialog;
import yokastore.youkagames.com.support.image.ImageLoaderUtils;
import yokastore.youkagames.com.yokastore.R;
import yokastore.youkagames.com.yokastore.base.activity.BaseFragmentActivity;
import yokastore.youkagames.com.yokastore.client.Contants;
import yokastore.youkagames.com.yokastore.model.BaseModel;
import yokastore.youkagames.com.yokastore.module.gameList.GameListPresenter;
//import yokastore.youkagames.com.yokastore.module.gameList.fragment.*
import yokastore.youkagames.com.yokastore.module.gameList.fragment.GameCusPackFragment;
import yokastore.youkagames.com.yokastore.module.gameList.fragment.GameDevPackFragment;
import yokastore.youkagames.com.yokastore.module.gameList.fragment.GameRelPackFragment;
import yokastore.youkagames.com.yokastore.module.gameList.model.GameDetailModel;
import yokastore.youkagames.com.yokastore.utils.CommonUtil;
import yokastore.youkagames.com.yokastore.utils.DialogFragmentDataCallback;
import yokastore.youkagames.com.yokastore.view.IBaseView;
import yokastore.youkagames.com.support.utils.LogUtil;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Created by songdehua on 2018/6/27.
 * 游戏详情页
 */
public class GameDetailActivity extends BaseFragmentActivity implements IBaseView,View.OnClickListener, DialogFragmentDataCallback{
    final int REQUEST_WRITE_EXTERNAL_STORAGE=1;//申请权限的请求码

    public static final String GAMEDETAIL = "game_detail";
    public static final String PROJKEY = "proj_key";
    public static final String PROJID = "proj_id";
    public static final String GAMENAME = "game_name";

    private ImageView iv_cover,iv_game,iv_back;
    private RelativeLayout rl_score;
    private GameListPresenter mPresenter;
    private TextView tv_game_title_name, tv_download_url, tv_name, tv_update_time, tv_download_button;
    private String projKey;
    private String projId;
    private TabLayout tabLayout;
    private GameDetailModel.GameDetailData mGameDetailData;
    private String download_url;

    private String[] mGameTab;
    private ViewPager mViewPager;
    private Fragment[] mFragments;
    private AppBarLayout appBarLayout;
    private File file;
    private ProgressDialog progressDialog;
    private DownloadAPK mDownloadAPK;

    @Override
    protected void onPause() {
        super.onPause();
        //MobclickAgent.onPause(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        LogUtil.i("on resume");
        //MobclickAgent.onResume(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtil.i("on Create");
        setContentView(R.layout.activity_game_detail);
        //initSystemBar(R.color.transparent);
        initViewId();
        initData();
    }

    private void initViewId() {
        initSystemBar(R.color.transparent);

        iv_cover = findViewById(R.id.iv_cover);
        iv_game = findViewById(R.id.iv_game);
        iv_back = findViewById(R.id.iv_back);
        tv_game_title_name = findViewById(R.id.tv_game_title_name);
        tv_name = findViewById(R.id.tv_name);
        tv_update_time = findViewById(R.id.tv_game_publish_year);
        //tv_download_url = findViewById(R.id.tv_link);
        tv_download_button = findViewById(R.id.tv_download_btn);
        appBarLayout = findViewById(R.id.appbar);

        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (verticalOffset <= -(appBarLayout.getHeight() / 2)){
                    tv_game_title_name.setVisibility(View.VISIBLE);
                }else{
                    tv_game_title_name.setVisibility(View.GONE);
                }
            }
        });
    }

    private void initData(){
        mGameTab = getResources().getStringArray(R.array.game_title);
        String name = getIntent().getStringExtra(GameDetailActivity.GAMENAME);
        iv_back.setOnClickListener((View v) -> {
            finish();
            overridePendingTransition(R.anim.in_from_right,R.anim.out_from_left);
        });

        tv_game_title_name.setText(name);
        tv_game_title_name.setVisibility(View.GONE);

        tv_download_button.setText("下载");
        tv_download_button.setOnClickListener(this);
        //projKey = getIntent().getStringExtra(GameDetailActivity.PROJKEY);
        projId = getIntent().getStringExtra(GameDetailActivity.PROJID);
        /*
        if (TextUtils.isEmpty(projKey)){
            return;
        }
        */
        if (TextUtils.isEmpty(PROJID)){
            return;
        }
        mPresenter = new GameListPresenter(this);
        mPresenter.getGameDetailDatas(projId);

    }

    @Override
    public void RequestSuccess(BaseModel data) {
        if (data.code == 0) {
            if (data != null && data instanceof  GameDetailModel) {
                mGameDetailData = ((GameDetailModel) data).data;
                initDetailData();
            }
        }
    }

    private void initDetailData() {
        if (mGameDetailData != null) {
            ImageLoaderUtils.loadImg(this,mGameDetailData.icon, iv_game, R.drawable.ic_img_loading);
            ImageLoaderUtils.loadImg(this,mGameDetailData.icon, iv_cover,R.drawable.cover);
            tv_game_title_name.setText(mGameDetailData.title);
            tv_name.setText(mGameDetailData.latestAndroidName);
            //tv_download_url.setText(mGameDetailData.latest_package_url);
            download_url = mGameDetailData.latestAndroidUrlOfDownload;

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            long lt = new Long(mGameDetailData.latestAndroidGmtCreate);
            Date date = new Date(lt);
            String update_time = sdf.format(date);
            tv_update_time.setText(update_time);

            if (mFragments == null) {
                mFragments = new Fragment[mGameTab.length];
                tabLayout = findViewById(R.id.tabLayout);
                mViewPager = findViewById(R.id.viewPager);
                mViewPager.setAdapter(new MainViewPagerAdapter((getSupportFragmentManager())));
                mViewPager.setOffscreenPageLimit(mGameTab.length);
                tabLayout.setupWithViewPager(mViewPager);
                for (int i = 0; i < tabLayout.getTabCount(); i++) {
                    TabLayout.Tab tab = tabLayout.getTabAt(i);
                    tab.setCustomView(getTabView(i));
                }
            }
        }
    }

    public View getTabView(int position) {
        View view = LayoutInflater.from(this).inflate(R.layout.tab_news_item_view,null);
        TextView tv = view.findViewById(R.id.textView);
        tv.setText(mGameTab[position]);
        return view;
    }

    @Override
    public void onClick(View v) {
        if (!CommonUtil.fastClick()) {
            switch (v.getId()){
                case R.id.tv_download_btn:
                    if (!CommonUtil.fastClick()) {
                        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                            if (ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                                Toast.makeText(this,"请打开相关权限，否则无法安装已下载应用！",Toast.LENGTH_SHORT).show();
                            }
                            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},REQUEST_WRITE_EXTERNAL_STORAGE);
                        }
                        /*
                        else {
                            Toast.makeText(this,"授权成功！",Toast.LENGTH_SHORT).show();
                        }
                        */

                        //下载最新的稳定安装包
                        //download_url = "http://10.225.136.151:8000/murdermystery/Android/youka_08011029.apk";
                        ProgressDialog progressDialog = new ProgressDialog(this);
                        progressDialog.setTitle("提示");
                        progressDialog.setMessage("正在下载...");
                        progressDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mDownloadAPK.cancel(true);
                            }
                        });
                        progressDialog.setButton(DialogInterface.BUTTON_NEUTRAL, "浏览器下载", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mDownloadAPK.cancel(true);

                                CommonUtil.openBrowser(v.getContext(),download_url);
                            }
                        });
                        progressDialog.setIndeterminate(false);
                        progressDialog.setMax(100);
                        progressDialog.setCancelable(false);                    //设置不可点击界面之外的区域让对话框消失
                        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);         //进度条类型
                        progressDialog.show();
                        String downloadUrl = download_url; //这里写你的apk url地址
                        mDownloadAPK = new DownloadAPK(progressDialog);
                        mDownloadAPK.execute(downloadUrl);

                    }
            }

        }
    }

/*
    @Override
    public void onClick(View v) {
        if (!CommonUtil.fastClick()) {
            switch (v.getId()){
                case R.id.tv_download_btn:
                    if (!CommonUtil.fastClick()) {
                        UpdateDialog dialog = new UpdateDialog(GameDetailActivity.this , getString(R.string.confirm_download) , "安装包:" + mGameDetailData.latestAndroidName ,
                                getString(R.string.dialog_notify_positive) ,
                                getString(R.string.dialog_notify_negative));
                        dialog.setListener(new UpdateDialog.OnNormalDialogClickListener() {
                            @Override
                            public void onPositive() {
                                CommonUtil.openBrowser(GameDetailActivity.this, download_url);
                                dialog.dismiss();
                            }

                            @Override
                            public void onNegative() {
                                dialog.dismiss();
                            }
                        });
                        dialog.show();

                    }
            }

        }
    }
*/

    //@Override
    //public void NetWorkError() {
    //    isSending = false;
    //}

    class MainViewPagerAdapter extends FragmentPagerAdapter {

        public MainViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public android.support.v4.app.Fragment getItem(int position) {
            LogUtil.i("getItem = "+ position + "mFragments[0] = " + mFragments[0]);
            switch (position) {
                case 0:
                    mFragments[0] = new GameDevPackFragment();
                    break;
                case 1:
                    mFragments[1] = new GameRelPackFragment();
                    break;
                case 2:
                    mFragments[2] = new GameCusPackFragment();
                    break;
            }
            Bundle bundle = new Bundle();
            bundle.putParcelable(GAMEDETAIL, mGameDetailData);
            mFragments[position].setArguments(bundle);
            return mFragments[position];
        }

        @Override
        public int getCount() {
            return mGameTab.length;
        }
    }


    /*
    *跳转到游戏详情页
     */
    public static void startGameDetailActivity(Context context, String pkId, String name) {
        Intent intent = getNewIntent(context, GameDetailActivity.class);
        intent.putExtra(GameDetailActivity.PROJID,pkId);
        intent.putExtra(GameDetailActivity.GAMENAME,name);
        context.startActivity(intent);
        ((Activity)context).overridePendingTransition(R.anim.activity_enter,R.anim.activity_exit);
    }

    @Override
    public String getCommentText() {
        return "";
    }

    @Override
    public void setCommentText(String commentTextTemp) {
    }


    /*
    *异步下载安装包，显示下载进度条
     */
    private class DownloadAPK extends AsyncTask<String, Integer, String> {
        ProgressDialog progressDialog;
        File file;

        public DownloadAPK(ProgressDialog progressDialog) {
            this.progressDialog = progressDialog;
        }

        @Override
        protected String doInBackground(String... params) {
            URL url;
            HttpURLConnection conn;
            BufferedInputStream bis = null;
            FileOutputStream fos = null;

            try {
                url = new URL(params[0]);
                conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setConnectTimeout(5000);

                int fileLength = conn.getContentLength();
                LogUtil.i(String.valueOf(fileLength));
                bis = new BufferedInputStream(conn.getInputStream());
                String fileName = Environment.getExternalStorageDirectory().getPath() + "/magkare/action.apk";
                file = new File(fileName);
                if (!file.exists()) {
                    if (!file.getParentFile().exists()) {
                        file.getParentFile().mkdirs();
                    }
                    file.createNewFile();
                }
                fos = new FileOutputStream(file);
                byte data[] = new byte[4 * 1024];
                long total = 0;
                int count;
                while ((count = bis.read(data)) != -1) {
                    total += count;
                    publishProgress((int) (total * 100 / fileLength));
                    fos.write(data, 0, count);
                    fos.flush();
                }
                fos.flush();

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (fos != null) {
                        fos.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    if (bis != null) {
                        bis.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            super.onProgressUpdate(progress);
            progressDialog.setProgress(progress[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            openFile(file);                 //打开安装apk文件操作
            progressDialog.dismiss();     //关闭对话框
        }

        private void openFile(File file) {
            if (file!=null){
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
                startActivity(intent);
            }
        }

    }

}

