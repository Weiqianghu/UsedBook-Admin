package com.weiqianghu.usedbook_admin.model.eneity;

import android.os.Parcel;
import android.os.Parcelable;

import cn.bmob.v3.BmobObject;

/**
 * Created by weiqianghu on 2016/4/11.
 */
public class SysMessageBean extends BmobObject implements Parcelable {
    private String content;
    private String messageType;
    private String imgUrl;
    private String title;

    public SysMessageBean() {
    }

    protected SysMessageBean(Parcel in) {
        content = in.readString();
        messageType = in.readString();
        imgUrl = in.readString();
        title = in.readString();
    }

    public static final Creator<SysMessageBean> CREATOR = new Creator<SysMessageBean>() {
        @Override
        public SysMessageBean createFromParcel(Parcel in) {
            return new SysMessageBean(in);
        }

        @Override
        public SysMessageBean[] newArray(int size) {
            return new SysMessageBean[size];
        }
    };

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

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
        dest.writeString(messageType);
        dest.writeString(imgUrl);
        dest.writeString(title);
    }
}
