package yokastore.youkagames.com.yokastore.module.mine.model;

import yokastore.youkagames.com.yokastore.model.BaseModel;

/**
 * Created by songdehua on 2018/9/30.
 */

public class UserModel extends BaseModel {
    public DataModel data;

    /*
    public static class DataModel {
        public String user_id;
        public String user_name;
        public String img_url;
        public String token;
    }
*/
    public static class DataModel {
        public String user;
        public String token;
    }
}
