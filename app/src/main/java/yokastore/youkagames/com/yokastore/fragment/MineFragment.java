package yokastore.youkagames.com.yokastore.fragment;


import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.w3c.dom.Text;

import java.util.ArrayList;

import yokastore.youkagames.com.support.utils.LogUtil;
import yokastore.youkagames.com.yokastore.R;
import yokastore.youkagames.com.yokastore.base.activity.BaseFragment;
import yokastore.youkagames.com.yokastore.view.IBaseView;
import yokastore.youkagames.com.yokastore.module.mine.presenter.UserPresenter;
import yokastore.youkagames.com.yokastore.utils.CommonUtil;
import yokastore.youkagames.com.support.image.ImageLoaderUtils;
import yokastore.youkagames.com.yokastore.utils.PreferenceUtils;
import yokastore.youkagames.com.yokastore.model.BaseModel;
import yokastore.youkagames.com.yokastore.client.apis.UserApi;
import yokastore.youkagames.com.yokastore.client.UserClient;
import yokastore.youkagames.com.yokastore.module.mine.model.UserModel;
import yokastore.youkagames.com.yokastore.view.CustomToast;
import yokastore.youkagames.com.yokastore.model.eventbus.user.LoginUserInfoUpdateNotify;
import yokastore.youkagames.com.yokastore.module.mine.activity.LoginActivity;
import yokastore.youkagames.com.yokastore.module.mine.activity.SettingAndHelpActivity;
import yokastore.youkagames.com.yokastore.model.eventbus.user.LoginUserInfoUpdateNotify;
import yokastore.youkagames.com.yokastore.module.mine.model.AuthorizationModel;

/**
 * Created by songdehua on 2018/9/29.
 */

public class MineFragment extends BaseFragment implements IBaseView, View.OnClickListener {
    private RelativeLayout rl_my_layout_me;
    private RelativeLayout rl_setting_help;
    private ImageView iv_header;
    private TextView tv_username;
    private UserPresenter mPresenter;

    @Override
    public View initView(LayoutInflater inflater,ViewGroup container) {
        View view = inflater.inflate(R.layout.fragment_mine,null);
        return view;
    }
    @Override
    protected void initFindViewById(View view) {
        rl_my_layout_me = view.findViewById(R.id.rl_my_layout_me);
        rl_setting_help = view.findViewById(R.id.rl_setting_help);
        iv_header = view.findViewById(R.id.iv_header);
        tv_username = view.findViewById(R.id.tv_username);

        mPresenter = new UserPresenter(getActivity(),this,this);
        rl_my_layout_me.setOnClickListener(this);
        rl_setting_help.setOnClickListener(this);

    }

    @Override
    public void initDataFragment() {
        if (!CommonUtil.isLogin(getActivity())) {
            tv_username.setText(R.string.not_login);
            String imgUrl = PreferenceUtils.getString(getActivity(), PreferenceUtils.IMG_URL,"");
            ImageLoaderUtils.loadRoundImg(getActivity(), imgUrl, iv_header);
        } else {
            String username = PreferenceUtils.getString(getActivity(),PreferenceUtils.NICKNAME,"");
            tv_username.setText(username);
            String imgUrl = PreferenceUtils.getString(getActivity(), PreferenceUtils.IMG_URL,"");
            ImageLoaderUtils.loadRoundImg(getActivity(), imgUrl, iv_header);
            mPresenter.getUserInfo();
        }
    }

    @Override
    public void RequestSuccess(BaseModel data) {
        if (data.code == 0) {
            if (data instanceof AuthorizationModel) {
                AuthorizationModel model = (AuthorizationModel) data;

                tv_username.setText(model.data.username);
                //ImageLoaderUtils.loadRoundImg(getActivity(), model.data.img_url, iv_header);
            }
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (!CommonUtil.fastClick()) {
            switch (id) {
                case R.id.rl_my_layout_me:
                    if (!CommonUtil.isLogin(getActivity())){
                        startLoginActivity();
                    }
                    break;
                case R.id.rl_setting_help:
                    startSettingAndHelpActivity();
                    break;
            }
        }
    }

    public void startLoginActivity() {
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivityAnim(intent);
    }

    /*
    *跳转到设置界面
     */
    public void startSettingAndHelpActivity() {
        Intent intent = new Intent(getActivity(),SettingAndHelpActivity.class);
        startActivityAnim(intent);
    }

    /*
    *登录成功，通知需要刷新的页面刷新
    * @param loginUserInfoUpdateNotify
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(LoginUserInfoUpdateNotify loginUserInfoUpdateNotify){
        if (loginUserInfoUpdateNotify.getLoginStatus() == LoginUserInfoUpdateNotify.LOGINSUCCESS) {
            mPresenter.getUserInfo();
        }else if (loginUserInfoUpdateNotify.getLoginStatus() == LoginUserInfoUpdateNotify.QUITLOGIN) {
            initDataFragment();
        }
    }
}
