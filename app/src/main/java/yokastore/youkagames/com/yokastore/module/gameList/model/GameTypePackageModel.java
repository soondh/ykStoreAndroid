package yokastore.youkagames.com.yokastore.module.gameList.model;

import android.os.Parcel;
import android.os.Parcelable;

import yokastore.youkagames.com.yokastore.model.BaseModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by songdehua on 2018/6/27.
 * 安装包列表的model
 */

public class GameTypePackageModel extends BaseModel {

    public ArrayList<DataBean> data;

    public static class DataBean implements Parcelable{
        public String id;
        public String type;
        public String project;
        public String createDate;
        public String title;
        public String name;
        public String plistUrl;

        protected DataBean(Parcel in){
            id = in.readString();
            type = in.readString();
            project = in.readString();
            createDate = in.readString();
            title = in.readString();
            name = in.readString();
            plistUrl = in.readString();

        }

        public static final Creator<DataBean> CREATOR = new Creator<DataBean>() {
            @Override
            public DataBean createFromParcel(Parcel in) {
                return new DataBean(in);
            }

            @Override
            public DataBean[] newArray(int size) {
                return new DataBean[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        public void writeToParcel(Parcel dest, int flag) {
            dest.writeString(id);
            dest.writeString(type);
            dest.writeString(project);
            dest.writeString(createDate);
            dest.writeString(title);
            dest.writeString(name);
            dest.writeString(plistUrl);
        }
    }
}
