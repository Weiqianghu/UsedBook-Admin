package com.weiqianghu.usedbook_admin.model.eneity;

import android.os.Parcel;
import android.os.Parcelable;

import cn.bmob.v3.BmobObject;

/**
 * Created by weiqianghu on 2016/3/24.
 */
public class OrderBean extends BmobObject implements Parcelable {
    private UserBean user;
    private String orderNo;
    private double totalPrice;
    private int amount;
    private BookBean book;
    private String orderState;
    private AddressBean address;

    public OrderBean() {
    }

    protected OrderBean(Parcel in) {
        orderNo = in.readString();
        totalPrice = in.readDouble();
        amount = in.readInt();
        book = in.readParcelable(BookBean.class.getClassLoader());
        orderState = in.readString();
        address = in.readParcelable(AddressBean.class.getClassLoader());
    }

    public static final Creator<OrderBean> CREATOR = new Creator<OrderBean>() {
        @Override
        public OrderBean createFromParcel(Parcel in) {
            return new OrderBean(in);
        }

        @Override
        public OrderBean[] newArray(int size) {
            return new OrderBean[size];
        }
    };

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public BookBean getBook() {
        return book;
    }

    public void setBook(BookBean book) {
        this.book = book;
    }

    public String getOrderState() {
        return orderState;
    }

    public void setOrderState(String orderState) {
        this.orderState = orderState;
    }

    public AddressBean getAddress() {
        return address;
    }

    public void setAddress(AddressBean address) {
        this.address = address;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(orderNo);
        dest.writeDouble(totalPrice);
        dest.writeInt(amount);
        dest.writeParcelable(book, flags);
        dest.writeString(orderState);
        dest.writeParcelable(address, flags);
    }
}
