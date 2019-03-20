package yokastore.youkagames.com.yokastore.client.apis;

import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import rx.Observable;

import yokastore.youkagames.com.yokastore.model.BaseModel;
import yokastore.youkagames.com.yokastore.module.mine.model.AuthorizationModel;
import yokastore.youkagames.com.yokastore.module.mine.model.UserModel;

/**
 * Created by songdehua on 2018/5/23.
 */

public interface UserApi {

    /**
     * 登录
     * @param auth
     * @param names
     * @return
     */
    @FormUrlEncoded
    @POST("/qa/dispatcher/user/login")
    Observable<AuthorizationModel> login(@Query("usingFormdata") String auth, @FieldMap Map<String, String> names);

    //Observable<UserModel> login(@QueryMap Map<String, String> names, @Body AuthorizationModel authorizationModel);
    /**
     * 用户注销
     * @return
     */
    @GET("/user/logout")
    Observable<BaseModel> logout();

    /**
     * 用户信息
     * @return
     */
    @GET("/qa/dispatcher/user/info")
    Observable<AuthorizationModel> getUserInfo();
}
