package com.weiqianghu.usedbook_admin.model.eneity;



import com.weiqianghu.usedbook_admin.util.Constant;

import java.io.Serializable;

import cn.bmob.v3.BmobObject;

/**
 * Created by weiqianghu on 2016/2/27.
 */
public class ShopBean extends BmobObject implements Serializable{
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
}
