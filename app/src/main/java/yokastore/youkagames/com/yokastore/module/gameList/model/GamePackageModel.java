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

public class GamePackageModel extends BaseModel {

    public ArrayList<DataBean> data;

    public static class DataBean implements Parcelable{
        public String projectId;
        public String packageId;
        public String tag;
        public String originalFileName;
        public String urlOfDownload;
        public String urlOfQrCode;
        public String gmtCreate;
        public String gmtModified;
        public String manualDescription;
        public String manualVersion;
        public String category;
        public String fileMd5;
        public String fileSize;
        public String bundleDebugRelease;
        public String bundleExinfo;
        public String bundleIconPath;
        public String bundleIdentifier;
        public String bundleName;
        public String bundleVersion;


        protected DataBean(Parcel in){
            projectId = in.readString();
            packageId = in.readString();
            tag = in.readString();
            originalFileName = in.readString();
            urlOfDownload = in.readString();
            urlOfQrCode = in.readString();
            gmtCreate = in.readString();
            gmtModified = in.readString();
            manualDescription = in.readString();
            manualVersion = in.readString();
            category = in.readString();
            fileMd5 = in.readString();
            fileSize = in.readString();
            bundleDebugRelease = in.readString();
            bundleExinfo = in.readString();
            bundleIconPath = in.readString();
            bundleIdentifier = in.readString();
            bundleName = in.readString();
            bundleVersion = in.readString();

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
            dest.writeString(projectId);
            dest.writeString(packageId);
            dest.writeString(tag);
            dest.writeString(originalFileName);
            dest.writeString(urlOfDownload);
            dest.writeString(urlOfQrCode);
            dest.writeString(gmtCreate);
            dest.writeString(gmtModified);
            dest.writeString(manualDescription);
            dest.writeString(manualVersion);
            dest.writeString(category);
            dest.writeString(fileMd5);
            dest.writeString(fileSize);
            dest.writeString(bundleDebugRelease);
            dest.writeString(bundleExinfo);
            dest.writeString(bundleIconPath);
            dest.writeString(bundleIdentifier);
            dest.writeString(bundleName);
            dest.writeString(bundleVersion);
        }
    }
}
