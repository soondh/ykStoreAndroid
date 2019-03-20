package yokastore.youkagames.com.yokastore.module.mine.activity;


import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.tbruyelle.rxpermissions2.RxPermissions;
import yokastore.youkagames.com.yokastore.R;
import yokastore.youkagames.com.yokastore.base.activity.BaseActivity;
import yokastore.youkagames.com.yokastore.model.BaseModel;
import yokastore.youkagames.com.yokastore.model.eventbus.user.LoginUserInfoUpdateNotify;
import yokastore.youkagames.com.yokastore.module.mine.presenter.UserPresenter;
import yokastore.youkagames.com.yokastore.utils.CommonUtil;
import yokastore.youkagames.com.yokastore.utils.DataCleanManager;
import yokastore.youkagames.com.yokastore.utils.FileSizeUtil;
import yokastore.youkagames.com.yokastore.utils.FileUtils;
import yokastore.youkagames.com.yokastore.utils.PreferenceUtils;
import yokastore.youkagames.com.yokastore.view.CustomToast;
import yokastore.youkagames.com.yokastore.view.IBaseView;
import yokastore.youkagames.com.yokastore.view.TitleBar;

import org.greenrobot.eventbus.EventBus;

import java.io.File;

/**
 * Created by songdehua on 2018/10/8.
 */

public class SettingAndHelpActivity extends BaseActivity implements View.OnClickListener, IBaseView {
    private TitleBar titleBar;
    private TextView tv_login_out;
    private RelativeLayout rl_clear_cache;
    private TextView tv_cache_size;
    private UserPresenter mPresenter;
    private RelativeLayout rl_header_bar;
    private Handler mhandle = new MyHandler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_and_help);

        titleBar = findViewById(R.id.title_bar);
        tv_login_out = findViewById(R.id.tv_login_out);
        rl_clear_cache = findViewById(R.id.rl_clear_cache);
        tv_cache_size = findViewById(R.id.tv_cache_size);

        rl_clear_cache.setOnClickListener(this);
        tv_login_out.setOnClickListener(this);

        rl_header_bar = (RelativeLayout) findViewById(R.id.rl_heade_bar);
        ViewGroup.LayoutParams sp_params;
        sp_params = rl_header_bar.getLayoutParams();
        sp_params.height = CommonUtil.getTitleBarPadding(this);
        rl_header_bar.setLayoutParams(sp_params);
        titleBar.setTitle(getString(R.string.setting_and_help));
        titleBar.setLeftLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mPresenter = new UserPresenter(this);
        initCacheSize();

        if(!CommonUtil.isLogin(SettingAndHelpActivity.this)){
            tv_login_out.setVisibility(View.GONE);
            return;
        }


    }

    private void initCacheSize() {
        /*获取universalImageloader缓存*/
        File universalFile =  FileUtils.getDiskCacheDir(this,"circle_bitmap");

        double universalSize = FileSizeUtil.getFileOrFilesSize(universalFile.getAbsolutePath(),FileSizeUtil.SIZETYPE_MB);
        double totalSize = universalSize;
        tv_cache_size.setText(CommonUtil.doubleToString(totalSize)+"MB");
    }

    @Override
    public void onClick(View view) {
        if (!CommonUtil.fastClick()) {
            int id = view.getId();
            if(id == R.id.tv_login_out){
                //TODO 退出登录的时候取消关联设备
                String userId = CommonUtil.getUid(this);
                mPresenter.logout();
            }else if(id == R.id.rl_clear_cache){
                showProgress();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        File universalFile =  FileUtils.getDiskCacheDir(SettingAndHelpActivity.this,"circle_bitmap");
                        File glideFile = Glide.getPhotoCacheDir(SettingAndHelpActivity.this);
                        File gameplatformFile = FileUtils.getDiskCacheDir(SettingAndHelpActivity.this,"gameplatform");

                        FileUtils.deleteDir(universalFile);
                        FileUtils.deleteDir(glideFile);
                        FileUtils.deleteDir(gameplatformFile);
                        DataCleanManager.cleanInternalCache(SettingAndHelpActivity.this);
                        mhandle.sendEmptyMessage(1);
                    }
                }).start();

            }
        }
    }

    @Override
    public void RequestSuccess(BaseModel data) {
        if (data != null) {
            CommonUtil.exitClearAccount(SettingAndHelpActivity.this);
            EventBus.getDefault().post(new LoginUserInfoUpdateNotify(LoginUserInfoUpdateNotify.QUITLOGIN));
            finish();
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mhandle.removeCallbacksAndMessages(null);
        mhandle = null;
    }

    private class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            HideProgress();
            tv_cache_size.setText("0MB");
            CustomToast.showToast(SettingAndHelpActivity.this,"缓存清除成功", Toast.LENGTH_SHORT);
        }
    }
}
