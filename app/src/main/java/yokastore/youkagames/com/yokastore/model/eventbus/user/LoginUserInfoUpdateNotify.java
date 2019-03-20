package yokastore.youkagames.com.yokastore.model.eventbus.user;

/**
 * Created by song dehua on 2018/3/27.
 * Created by songdehua on 2018/9/30.
 */

public class LoginUserInfoUpdateNotify {
    public static final int LOGINSUCCESS = 0;
    public static final int QUITLOGIN = 1;
    private int loginStatus;
    public LoginUserInfoUpdateNotify(int status){
        loginStatus = status;
    }

    public int getLoginStatus() {
        return loginStatus;
    }

    public void setLoginStatus(int loginStatus) {
        this.loginStatus = loginStatus;
    }
}
