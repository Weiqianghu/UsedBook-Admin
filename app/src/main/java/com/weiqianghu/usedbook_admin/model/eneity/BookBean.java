package com.weiqianghu.usedbook_admin.model.eneity;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

import cn.bmob.v3.BmobObject;

/**
 * Created by 胡伟强 on 2016/1/26.
 */
public class BookBean extends BmobObject implements Serializable, Parcelable {
    private String objectIdStr;
    private String bookName;
    private String isbn;
    private double price;
    private String press;//出版社
    private String author;
    private String category;
    private boolean isSell;
    private int salesVolume;//销量
    private int stock;//库存
    private double percent;
    private String percentDescribe;
    private ShopBean shop;

    public BookBean() {
    }

    protected BookBean(Parcel in) {
        objectIdStr = in.readString();
        bookName = in.readString();
        isbn = in.readString();
        price = in.readDouble();
        press = in.readString();
        author = in.readString();
        category = in.readString();
        isSell = in.readByte() != 0;
        salesVolume = in.readInt();
        stock = in.readInt();
        percent = in.readDouble();
        percentDescribe = in.readString();
    }


    public String getObjectIdStr() {
        return objectIdStr;
    }

    public void setObjectIdStr(String objectIdStr) {
        this.objectIdStr = objectIdStr;
    }

    public static final Creator<BookBean> CREATOR = new Creator<BookBean>() {
        @Override
        public BookBean createFromParcel(Parcel in) {
            return new BookBean(in);
        }

        @Override
        public BookBean[] newArray(int size) {
            return new BookBean[size];
        }
    };

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getPress() {
        return press;
    }

    public void setPress(String press) {
        this.press = press;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public boolean isSell() {
        return isSell;
    }

    public void setSell(boolean sell) {
        isSell = sell;
    }

    public int getSalesVolume() {
        return salesVolume;
    }

    public void setSalesVolume(int salesVolume) {
        this.salesVolume = salesVolume;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public double getPercent() {
        return percent;
    }

    public void setPercent(double percent) {
        this.percent = percent;
    }

    public String getPercentDescribe() {
        return percentDescribe;
    }

    public void setPercentDescribe(String percentDescribe) {
        this.percentDescribe = percentDescribe;
    }

    public ShopBean getShop() {
        return shop;
    }

    public void setShop(ShopBean shop) {
        this.shop = shop;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(objectIdStr);
        dest.writeString(bookName);
        dest.writeString(isbn);
        dest.writeDouble(price);
        dest.writeString(press);
        dest.writeString(author);
        dest.writeString(category);
        dest.writeByte((byte) (isSell ? 1 : 0));
        dest.writeInt(salesVolume);
        dest.writeInt(stock);
        dest.writeDouble(percent);
        dest.writeString(percentDescribe);
    }
}