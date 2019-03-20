package yokastore.youkagames.com.yokastore.module.gameList;

import android.content.Context;
import android.util.Log;

import rx.Scheduler;
import yokastore.youkagames.com.yokastore.model.BaseModel;
import yokastore.youkagames.com.yokastore.model.DataBooleanMode;
import yokastore.youkagames.com.yokastore.model.DataIntModel;
import yokastore.youkagames.com.yokastore.module.gameList.client.GameListApi;
import yokastore.youkagames.com.yokastore.module.gameList.client.GameListClient;
import yokastore.youkagames.com.yokastore.module.gameList.model.GameDetailModel;
import yokastore.youkagames.com.yokastore.module.gameList.model.GameListModel;
import yokastore.youkagames.com.support.utils.LogUtil;
import yokastore.youkagames.com.yokastore.module.gameList.model.GamePackageModel;
import yokastore.youkagames.com.yokastore.module.gameList.model.GameTypePackageModel;
import yokastore.youkagames.com.yokastore.utils.NetWorkUtils;
import yokastore.youkagames.com.yokastore.utils.PreferenceUtils;
import yokastore.youkagames.com.yokastore.view.IBaseView;
import yokastore.youkagames.com.yokastore.view.IBaseControl;

import java.lang.ref.WeakReference;
import java.util.HashMap;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by songdehua on 2018/6/12.
 */

public class GameListPresenter {
    private IBaseView iBaseView;
    private IBaseControl iBaseControl;

    private GameListApi mGameListApi;
    private WeakReference<Context> mContext;

    public GameListPresenter(Context context, IBaseView iBaseView, IBaseControl iBaseControl){
        this.iBaseControl = iBaseControl;
        this.iBaseView = iBaseView;
        mContext = new WeakReference<>(context);
        mGameListApi = GameListClient.getInstance(context).getGameListApi();
    }

    public GameListPresenter(Context context){
        this.iBaseView = (IBaseView)context;
        this.iBaseControl = (IBaseControl)context;
        mContext = new WeakReference<Context>(context);
        mGameListApi = GameListClient.getInstance(context).getGameListApi();
    }

    /*
    * 获取游戏列表
     */
    public void getGameListDatas(){
        if(!NetWorkUtils.isNetworkConnected(mContext.get())){
            iBaseControl.NetWorkError();
            return;
        }

        mGameListApi.getGameListData()
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<GameListModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        iBaseControl.NetWorkError();
                    }

                    @Override
                    public void onNext(GameListModel gameListModel) {
                        iBaseView.RequestSuccess(gameListModel);
                    }
                });
    }


    /*
    *获取游戏详情
     */
    public void getGameDetailDatas(String project_id) {
        mGameListApi.getGameDetailDatas(project_id)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<GameDetailModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.i("e = " + e);
                    }

                    @Override
                    public void onNext(GameDetailModel gameDetailModel) {
                        iBaseView.RequestSuccess(gameDetailModel);
                    }
                });
    }


    /*
     * 获取游戏安装包列表
     * @param pkey
     * @param vname
     */

    public void getGameVeriosnDatas(String project_id, String category, String tag){
        HashMap<String,String> map = new HashMap<>();
        map.put("project_id",project_id);
        map.put("category",category);
        map.put("tag",tag);
        mGameListApi.getGameVeriosnDatas(map).subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<GamePackageModel>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.i("Dehua","onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.i("Dehua","onError");
                        if(iBaseControl != null){
                            iBaseControl.RequestError(e);
                        }
                    }

                    @Override
                    public void onNext(GamePackageModel gamePackageModel) {
                        iBaseView.RequestSuccess(gamePackageModel);
                    }
                });
    }

    /*
    *获取游戏安装包列表(根据dev，res，custom区分)
    * @param pkey
    * @param type
     */
    public void getGamePackageDatas(String projectId, String category, String tag){
        HashMap<String,String> map = new HashMap();
        map.put("projectId",projectId);
        map.put("category",category);
        map.put("tag",tag);
        mGameListApi.getGamePackageDatas(map).subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<GamePackageModel>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.i("Dehua","onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.i("Dehua","onError");
                        if (iBaseControl != null){
                            iBaseControl.RequestError(e);
                        }
                    }

                    @Override
                    public void onNext(GamePackageModel gamePackageModel) {
                        iBaseView.RequestSuccess(gamePackageModel);
                    }
                });
    }

    /*
    *获取游戏安装包列表(根据dev，res，custom区分)
    * @param pkey
    * @param type
     */
    public void getGamePackageDatas_dev(String pkey){
        HashMap<String,String> map = new HashMap();
        map.put("pkey",pkey);
        mGameListApi.getGamePackageDatas(map).subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<GamePackageModel>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.i("Dehua","onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.i("Dehua","onError");
                        if (iBaseControl != null){
                            iBaseControl.RequestError(e);
                        }
                    }

                    @Override
                    public void onNext(GamePackageModel gamePackageModel) {
                        iBaseView.RequestSuccess(gamePackageModel);
                    }
                });
    }

}
