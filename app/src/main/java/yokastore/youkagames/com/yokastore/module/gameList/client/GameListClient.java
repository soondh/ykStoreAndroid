package yokastore.youkagames.com.yokastore.module.gameList.client;

import android.content.Context;

import yokastore.youkagames.com.yokastore.client.BaseClient;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

/**
 * Created by songdehua on 2018/6/12.
 */

public class GameListClient extends BaseClient{
    private static GameListClient gameListClient;
    private GameListApi mGameListApiInterface;
    private Retrofit mGameListRetrofit;

    public static GameListClient getInstance(Context context){
        if (gameListClient == null) {
            synchronized (GameListClient.class) {
                if (gameListClient == null)
                    gameListClient = new GameListClient(context);
            }

        }
        return gameListClient;
    }

    private GameListClient(Context context) {
        OkHttpClient httpClient = getHttpClientBuilder(context).build();
        mGameListRetrofit = getOkhttpRetrofit(httpClient);
    }
    public GameListApi getGameListApi() {
        if (mGameListApiInterface == null) {
            mGameListApiInterface = mGameListRetrofit.create(GameListApi.class);
        }
        return mGameListApiInterface;
    }
}
