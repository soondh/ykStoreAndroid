package yokastore.youkagames.com.yokastore.module.gameList.model;

import android.os.Parcel;
import android.os.Parcelable;

import yokastore.youkagames.com.yokastore.model.BaseModel;

import java.util.ArrayList;

/**
 * Created by songdehua on 2018/6/27.
 */

public class GameDetailModel extends BaseModel {
        public GameDetailData data;

        public static class GameDetailData implements Parcelable{
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

                protected GameDetailData(Parcel in) {
                        projectId = in.readString();
                        pkey = in.readString();
                        icon = in.readString();
                        icon32 = in.readString();
                        icon64 = in.readString();
                        icon128 = in.readString();
                        title = in.readString();
                        description = in.readString();

                        latestAndroidGmtCreate = in.readString();
                        latestAndroidGmtModified = in.readString();
                        latestAndroidName = in.readString();
                        latestAndroidUrlOfDownload = in.readString();
                        latestAndroidUrlOfQrCode = in.readString();

                        latestIOSGmtCreate = in.readString();
                        latestIOSGmtModified = in.readString();
                        latestIOSName = in.readString();
                        latestIOSUrlOfDownload = in.readString();
                        latestIOSUrlOfQrCode = in.readString();
                }

                public static final Creator<GameDetailData> CREATOR = new Creator<GameDetailData>() {
                        @Override
                        public GameDetailData createFromParcel(Parcel in) {
                                return new GameDetailData(in);
                        }

                        @Override
                        public GameDetailData[] newArray(int size) {
                                return new GameDetailData[size];
                        }
                };

                @Override
                public int describeContents() {
                        return 0;
                }

                @Override
                public void writeToParcel(Parcel dest, int flags) {
                        dest.writeString(pkey);
                        dest.writeString(icon);
                        dest.writeString(icon);
                        dest.writeString(icon32);
                        dest.writeString(icon64);
                        dest.writeString(icon128);
                        dest.writeString(title);
                        dest.writeString(description);
                        dest.writeString(latestAndroidGmtCreate);
                        dest.writeString(latestAndroidGmtModified);
                        dest.writeString(latestAndroidName);
                        dest.writeString(latestAndroidUrlOfDownload);
                        dest.writeString(latestAndroidUrlOfQrCode);

                        dest.writeString(latestIOSGmtCreate);
                        dest.writeString(latestIOSGmtModified);
                        dest.writeString(latestIOSName);
                        dest.writeString(latestIOSUrlOfDownload);
                        dest.writeString(latestIOSUrlOfQrCode);

                }
        }

}
