package com.weiqianghu.usedbook_admin.model.eneity;

import android.os.Parcel;
import android.os.Parcelable;

import cn.bmob.v3.BmobObject;

/**
 * Created by weiqianghu on 2016/3/30.
 */
public class CommentBean extends BmobObject implements Parcelable {
    private UserBean user;
    private OrderBean order;
    private BookBean book;
    private int grade;
    private String content;

    public CommentBean() {
    }

    protected CommentBean(Parcel in) {
        order = in.readParcelable(OrderBean.class.getClassLoader());
        book = in.readParcelable(BookBean.class.getClassLoader());
        grade = in.readInt();
        content = in.readString();
    }

    public static final Creator<CommentBean> CREATOR = new Creator<CommentBean>() {
        @Override
        public CommentBean createFromParcel(Parcel in) {
            return new CommentBean(in);
        }

        @Override
        public CommentBean[] newArray(int size) {
            return new CommentBean[size];
        }
    };

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public OrderBean getOrder() {
        return order;
    }

    public void setOrder(OrderBean order) {
        this.order = order;
    }

    public BookBean getBook() {
        return book;
    }

    public void setBook(BookBean book) {
        this.book = book;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
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
        dest.writeParcelable(order, flags);
        dest.writeParcelable(book, flags);
        dest.writeInt(grade);
        dest.writeString(content);
    }
}
