package com.weiqianghu.usedbook_admin.model.eneity;

import android.os.Parcel;
import android.os.Parcelable;

import cn.bmob.v3.BmobObject;

/**
 * Created by weiqianghu on 2016/4/11.
 */
public class PushMessageBean extends BmobObject implements Parcelable {
    private String content;

    public PushMessageBean() {
    }

    protected PushMessageBean(Parcel in) {
        content = in.readString();
    }

    public static final Creator<PushMessageBean> CREATOR = new Creator<PushMessageBean>() {
        @Override
        public PushMessageBean createFromParcel(Parcel in) {
            return new PushMessageBean(in);
        }

        @Override
        public PushMessageBean[] newArray(int size) {
            return new PushMessageBean[size];
        }
    };

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(content);
    }
}
