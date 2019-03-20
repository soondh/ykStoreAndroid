package yokastore.youkagames.com.yokastore.client.engine.callback;

/**
 * Created by songdehua on 2018/5/25.
 */

public interface BaseHttpCallBack<T> {

    void onResponse(T t);

    void onError(T t);
}
