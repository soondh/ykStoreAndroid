package yokastore.youkagames.com.yokastore.module.mine.model;

import yokastore.youkagames.com.yokastore.model.BaseModel;

/**
 * Created by songdehua on 2018/10/15.
 */

public class AuthorizationModel extends BaseModel{
    public DataModel data;
    public static class DataModel {
        public String username;
        public String token;
    }
}
