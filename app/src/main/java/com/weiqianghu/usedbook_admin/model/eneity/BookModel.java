package com.weiqianghu.usedbook_admin.model.eneity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by weiqianghu on 2016/3/12.
 */
public class BookModel implements Parcelable {
    private BookBean book;
    private List<BookImgsBean> bookImgs;

    public BookModel() {
    }

    protected BookModel(Parcel in) {
        book = in.readParcelable(BookBean.class.getClassLoader());
    }

    public static final Creator<BookModel> CREATOR = new Creator<BookModel>() {
        @Override
        public BookModel createFromParcel(Parcel in) {
            return new BookModel(in);
        }

        @Override
        public BookModel[] newArray(int size) {
            return new BookModel[size];
        }
    };

    public BookBean getBook() {
        return book;
    }

    public void setBook(BookBean book) {
        this.book = book;
    }

    public List<BookImgsBean> getBookImgs() {
        return bookImgs;
    }

    public void setBookImgs(List<BookImgsBean> bookImgs) {
        this.bookImgs = bookImgs;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(book, flags);
    }
}
