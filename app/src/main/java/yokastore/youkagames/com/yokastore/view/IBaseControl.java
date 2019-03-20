package yokastore.youkagames.com.yokastore.view;

/**
 * Created by songdehua on 2018/5/29.
 */

public interface IBaseControl {
    void showProgress();
    void HideProgress();
    void NetWorkError();
    void RequestError(Throwable e);
}
