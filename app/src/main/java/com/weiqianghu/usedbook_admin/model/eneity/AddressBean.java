package com.weiqianghu.usedbook_admin.model.eneity;

import android.os.Parcel;
import android.os.Parcelable;

import cn.bmob.v3.BmobObject;

/**
 * Created by weiqianghu on 2016/2/23.
 */
public class AddressBean extends BmobObject implements Parcelable {
    private String objectIdStr;
    private String name;
    private String mobileNo;
    private String zipCode;//邮编
    private String province;
    private String city;
    private String county;
    private String detailAddress;
    private UserBean user;
    private boolean isDefault;
    private boolean isDelete;


    public AddressBean() {
    }


    protected AddressBean(Parcel in) {
        objectIdStr = in.readString();
        name = in.readString();
        mobileNo = in.readString();
        zipCode = in.readString();
        province = in.readString();
        city = in.readString();
        county = in.readString();
        detailAddress = in.readString();
        isDefault = in.readByte() != 0;
        isDelete = in.readByte() != 0;
    }

    public static final Creator<AddressBean> CREATOR = new Creator<AddressBean>() {
        @Override
        public AddressBean createFromParcel(Parcel in) {
            return new AddressBean(in);
        }

        @Override
        public AddressBean[] newArray(int size) {
            return new AddressBean[size];
        }
    };

    public String getObjectIdStr() {
        return objectIdStr;
    }

    public void setObjectIdStr(String objectIdStr) {
        this.objectIdStr = objectIdStr;
    }

    public boolean isDelete() {
        return isDelete;
    }

    public void setDelete(boolean delete) {
        isDelete = delete;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public void setDefault(boolean aDefault) {
        isDefault = aDefault;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getDetailAddress() {
        return detailAddress;
    }

    public void setDetailAddress(String detailAddress) {
        this.detailAddress = detailAddress;
    }

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(objectIdStr);
        dest.writeString(name);
        dest.writeString(mobileNo);
        dest.writeString(zipCode);
        dest.writeString(province);
        dest.writeString(city);
        dest.writeString(county);
        dest.writeString(detailAddress);
        dest.writeByte((byte) (isDefault ? 1 : 0));
        dest.writeByte((byte) (isDelete ? 1 : 0));
    }
}
