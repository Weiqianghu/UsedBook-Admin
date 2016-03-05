package com.weiqianghu.usedbook_admin.model.eneity;



import android.os.Parcel;
import android.os.Parcelable;

import com.weiqianghu.usedbook_admin.util.Constant;

import java.io.Serializable;

import cn.bmob.v3.BmobObject;

/**
 * Created by weiqianghu on 2016/2/27.
 */
public class ShopBean extends BmobObject implements Serializable,Parcelable{
    private String contacts;
    private String idNumber;
    private String idFrontImg;
    private String idBackImg;
    private String shopName;
    private String province;
    private String city;
    private String county;
    private String detailAddress;
    private String contactNumber;
    private int verifyState;//审核状态
    private String verifyStateStr;

    public ShopBean(){}

    protected ShopBean(Parcel in) {
        contacts = in.readString();
        idNumber = in.readString();
        idFrontImg = in.readString();
        idBackImg = in.readString();
        shopName = in.readString();
        province = in.readString();
        city = in.readString();
        county = in.readString();
        detailAddress = in.readString();
        contactNumber = in.readString();
        verifyState = in.readInt();
        verifyStateStr = in.readString();
    }

    public static final Creator<ShopBean> CREATOR = new Creator<ShopBean>() {
        @Override
        public ShopBean createFromParcel(Parcel in) {
            return new ShopBean(in);
        }

        @Override
        public ShopBean[] newArray(int size) {
            return new ShopBean[size];
        }
    };

    public String getVerifyStateStr() {
        switch (verifyState){
            case Constant.AUDIT_STATE0:
                verifyStateStr="信息已提交，等待审核";
                break;
            case Constant.AUDIT_STATE1:
                verifyStateStr="审核通过";
                break;
            case Constant.AUDIT_STATE2:
                verifyStateStr="审核失败，身份证和联系人不匹配";
                break;
            case Constant.AUDIT_STATE3:
                verifyStateStr="审核失败，身份证照片太模糊，不能辨认信息";
                break;
        }
        return verifyStateStr;
    }

    public int getVerifyState() {
        return verifyState;
    }

    public void setVerifyState(int verifyState) {
        this.verifyState = verifyState;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getIdFrontImg() {
        return idFrontImg;
    }

    public void setIdFrontImg(String idFrontImg) {
        this.idFrontImg = idFrontImg;
    }

    public String getIdBackImg() {
        return idBackImg;
    }

    public void setIdBackImg(String idBackImg) {
        this.idBackImg = idBackImg;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(contacts);
        dest.writeString(idNumber);
        dest.writeString(idFrontImg);
        dest.writeString(idBackImg);
        dest.writeString(shopName);
        dest.writeString(province);
        dest.writeString(city);
        dest.writeString(county);
        dest.writeString(detailAddress);
        dest.writeString(contactNumber);
        dest.writeInt(verifyState);
        dest.writeString(verifyStateStr);
    }
}
