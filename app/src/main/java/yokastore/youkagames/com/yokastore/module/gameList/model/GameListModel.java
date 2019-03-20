package yokastore.youkagames.com.yokastore.module.gameList.model;

import yokastore.youkagames.com.yokastore.model.BaseModel;

import java.util.ArrayList;

/**
 * Created by songdehua on 2018/6/4.
 */

public class GameListModel extends BaseModel{
    //public ArrayList<GameListData> data;
    public ArrayList<GameInfoData> data;

    //老版本model
    public static class GameListData{
        public String title;
        public String icon;
        public String desc;
        public String key;
        public String background;
        public String updateDate;
        public String packageDate;
    }

    //新版本model
    public static class GameInfoData{
        public String projectId;
        public String pkey;
        public String icon;
        public String icon32;
        public String icon64;
        public String icon128;
        public String title;
        public String description;

        public String latestAndroidGmtCreate;
        public String latestAndroidGmtModified;
        public String latestAndroidName;
        public String latestAndroidUrlOfDownload;
        public String latestAndroidUrlOfQrCode;

        public String latestIOSGmtCreate;
        public String latestIOSGmtModified;
        public String latestIOSName;
        public String latestIOSUrlOfDownload;
        public String latestIOSUrlOfQrCode;
    }

}
