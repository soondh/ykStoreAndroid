package yokastore.youkagames.com.yokastore.module.mine.presenter;

import android.content.Context;

import com.google.gson.Gson;

import yokastore.youkagames.com.yokastore.module.mine.model.AuthorizationModel;
import yokastore.youkagames.com.yokastore.utils.CommonUtil;
import yokastore.youkagames.com.yokastore.utils.NetWorkUtils;
import yokastore.youkagames.com.yokastore.utils.PreferenceUtils;
import yokastore.youkagames.com.yokastore.utils.SystemUtils;
import yokastore.youkagames.com.yokastore.view.IBaseControl;
import yokastore.youkagames.com.yokastore.view.IBaseView;
import yokastore.youkagames.com.yokastore.module.mine.model.UserModel;
import yokastore.youkagames.com.yokastore.client.apis.UserApi;
import yokastore.youkagames.com.yokastore.client.UserClient;
import yokastore.youkagames.com.yokastore.model.BaseModel;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by songdehua on 2018/9/30.
 */

public class UserPresenter {
    private IBaseView iBaseView;
    private IBaseControl iBaseControl;
    private UserApi mUserApi;
    private WeakReference<Context> mContext;

    public UserPresenter(Context context, IBaseView iBaseView, IBaseControl iBaseControl){
        this.iBaseView = iBaseView;
        this.iBaseControl = iBaseControl;
        mContext = new WeakReference<>(context);
        mUserApi = UserClient.getInstance(mContext.get()).getUserApi();
    }
    public UserPresenter(Context context){
        this.iBaseView = (IBaseView)context;
        this.iBaseControl = (IBaseControl)context;
        mContext = new WeakReference<>(context);
        mUserApi = UserClient.getInstance(mContext.get()).getUserApi();
    }

    /**
     * 登录
     * @param username
     * @param password
     */
    public void login(String username, String password) {
        iBaseControl.showProgress();
        HashMap mapName = new HashMap<>();
        mapName.put("username",username);
        mapName.put("password",password);
        mUserApi.login("true",mapName)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<AuthorizationModel>() {
                    @Override
                    public void onCompleted() {
                        iBaseControl.HideProgress();
                    }

                    @Override
                    public void onError(Throwable e) {
                        iBaseControl.RequestError(e);
                        iBaseControl.HideProgress();
                    }

                    @Override
                    public void onNext(AuthorizationModel baseModel) {
                        iBaseView.RequestSuccess(baseModel);
                    }
                });
    }

    /**
     * 用户注销
     */
    public void logout() {
        iBaseControl.showProgress();
        mUserApi.logout()
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseModel>() {
                    @Override
                    public void onCompleted() {
                        iBaseControl.HideProgress();
                    }

                    @Override
                    public void onError(Throwable e) {
                        iBaseControl.RequestError(e);
                        iBaseControl.HideProgress();
                    }

                    @Override
                    public void onNext(BaseModel baseModel) {
                        iBaseView.RequestSuccess(baseModel);
                    }
                });
    }

    /**
     * 获取用户信息
     */
    public void getUserInfo(){
        if(!NetWorkUtils.isNetworkConnected(mContext.get())){
            iBaseControl.NetWorkError();
            return;
        }
        iBaseControl.showProgress();
        mUserApi.getUserInfo()
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<AuthorizationModel>() {
                    @Override
                    public void onCompleted() {
                        iBaseControl.HideProgress();
                    }

                    @Override
                    public void onError(Throwable e) {
                        iBaseControl.RequestError(e);
                        iBaseControl.HideProgress();
                    }

                    @Override
                    public void onNext(AuthorizationModel authorizationModel) {
                        iBaseView.RequestSuccess(authorizationModel);
                    }
                });

    }

}
