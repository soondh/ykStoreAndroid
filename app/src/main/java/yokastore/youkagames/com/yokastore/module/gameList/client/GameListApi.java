package yokastore.youkagames.com.yokastore.module.gameList.client;

import yokastore.youkagames.com.yokastore.module.gameList.model.GameDetailModel;
import yokastore.youkagames.com.yokastore.module.gameList.model.GameListModel;

import java.util.HashMap;
import java.util.Map;

import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Path;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;
import rx.Observable;
import yokastore.youkagames.com.yokastore.module.gameList.model.GamePackageModel;
import yokastore.youkagames.com.yokastore.module.gameList.model.GameTypePackageModel;

/**
 * Created by songdehua on 2018/6/12.
 */

public interface GameListApi {
    /*
    *获取游戏列表
     */
    //@GET("/qa/dispatcher/game/all")
    @GET("/v2/5b6be7b22f00003400893a69")
    Observable<GameListModel> getGameListDataMock();

    /*
    *获取游戏详情
     */
    @GET("/v2/5b6be76f2f00008e00893a68")
    Observable<GameDetailModel> getGameDetailDatas_mock(@QueryMap HashMap<String, String> map);

    /*
    *获取安装包列表
     */
    @GET("/qa/dispatcher/package")
    Observable<GamePackageModel> getGameVeriosnDatas(@QueryMap HashMap<String, String> map);


    /*
    *获取安装包列表（根据dev，res，custom）
     */
    //@GET("/v2/5b767dab3000006900848b42")
    @GET("qa/dispatcher/package")
    Observable<GamePackageModel> getGamePackageDatas(@QueryMap HashMap<String,String > map);


    /*
    *获取游戏列表
     */
    @GET("/qa/dispatcher/project")
    Observable<GameListModel> getGameListData();

    /*
    *获取游戏详情
     */
    @GET("/qa/dispatcher/projectDetail/{project_id}")
    Observable<GameDetailModel> getGameDetailDatas(@Path("project_id") String project_id);
}
