package yokastore.youkagames.com.yokastore.module.mine.activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.view.ViewGroup;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;

import yokastore.youkagames.com.yokastore.R;
import yokastore.youkagames.com.yokastore.MainActivity;
import yokastore.youkagames.com.yokastore.base.activity.BaseActivity;
import yokastore.youkagames.com.yokastore.db.MyDatabase;
import yokastore.youkagames.com.yokastore.model.BaseModel;
import yokastore.youkagames.com.yokastore.model.eventbus.user.LoginUserInfoUpdateNotify;
import yokastore.youkagames.com.yokastore.module.mine.model.AuthorizationModel;
import yokastore.youkagames.com.yokastore.module.mine.model.UserModel;
import yokastore.youkagames.com.yokastore.module.mine.presenter.UserPresenter;
import yokastore.youkagames.com.support.utils.LogUtil;
import yokastore.youkagames.com.yokastore.utils.CommonUtil;
import yokastore.youkagames.com.yokastore.utils.PreferenceUtils;
import yokastore.youkagames.com.yokastore.view.CustomProgressDialog;
import yokastore.youkagames.com.yokastore.view.CustomToast;
import yokastore.youkagames.com.yokastore.view.IBaseView;
import yokastore.youkagames.com.yokastore.view.TitleBar;

/**
 * Created by songdehua on 2018/10/8.
 */

public class LoginActivity extends BaseActivity implements IBaseView {
    public static final String IS_SET_PWD = "is_set_pwd";
    private EditText username;
    private EditText password;
    private UserPresenter mPresenter;
    private LinearLayout ll_layout;
    private RelativeLayout rl_header_bar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if (CommonUtil.isLogin(this)) {
            CommonUtil.exitClearAccount(this);
            EventBus.getDefault().post(new LoginUserInfoUpdateNotify(LoginUserInfoUpdateNotify.QUITLOGIN));
        }
        Button login = findViewById(R.id.login);
        ll_layout = findViewById(R.id.ll_layout);
        rl_header_bar = findViewById(R.id.rl_heade_bar);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        password.setTypeface(Typeface.DEFAULT);
        password.setTransformationMethod(new PasswordTransformationMethod());

        mPresenter = new UserPresenter(this);
        //全屏模式下title bar会把status bar遮挡住，可以把title顶部空出来，不遮挡
        ViewGroup.LayoutParams sp_params;
        sp_params = rl_header_bar.getLayoutParams();
        sp_params.height = CommonUtil.getTitleBarPadding(this);
        rl_header_bar.setLayoutParams(sp_params);
        TitleBar titleBar = findViewById(R.id.title_bar);
        titleBar.setTitle(R.string.login);
        titleBar.setLeftLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!CommonUtil.fastClick()) {
                    String number = username.getText().toString().trim();
                    if (TextUtils.isEmpty(number)) {
                        CustomToast.showToast(LoginActivity.this, R.string.please_input_username, Toast.LENGTH_SHORT);
                        return;
                    }
                    String pass = password.getText().toString().trim();
                    if (TextUtils.isEmpty(pass)) {
                        CustomToast.showToast(LoginActivity.this,R.string.please_input_password, Toast.LENGTH_SHORT);
                        return;
                    }
                    mPresenter.login(number,pass);
                }
            }
        });

        ll_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonUtil.hideSoftKeyboard(LoginActivity.this, ll_layout);
            }
        });
    }

    /*
    *回调
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        CustomProgressDialog.getInstance().disMissDialog();
    }


    @Override
    public void RequestSuccess(BaseModel data) {
        if (data.code == 0) {
            if (data != null && data instanceof AuthorizationModel) {
                AuthorizationModel model = (AuthorizationModel) data;
                if (!TextUtils.isEmpty(model.data.token)) {
                    PreferenceUtils.put(this, PreferenceUtils.TOKEN, model.data.token);
                    //PreferenceUtils.put(this, PreferenceUtils.USER_ID, model.data.user_id);
                    //PreferenceUtils.put(this, PreferenceUtils.IMG_URL, model.data.img_url);
                    PreferenceUtils.put(this, PreferenceUtils.NICKNAME, model.data.username);
                    //TODO 当用户使用自有账号登录时，可以这样统计：
                    MyDatabase.createInstance(this);
                    EventBus.getDefault().post(new LoginUserInfoUpdateNotify(LoginUserInfoUpdateNotify.LOGINSUCCESS));
                    finish();
                }
            } else {
                CustomToast.showToast(this, data.errMsg, Toast.LENGTH_SHORT);
            }
        }
        else if (data.code == 1){
            CustomToast.showToast(this, data.errMsg, Toast.LENGTH_SHORT);
        }
    }
}
