package com.weiqianghu.usedbook_admin.model.eneity;

import cn.bmob.v3.BmobUser;

/**
 * Created by weiqianghu on 2016/2/19.
 */
public class UserBean extends BmobUser {
    private int age;
    private boolean sex;
    private String sexStr;
    private String img;
    private String address;
    private Double longitude;
    private Double latitude;

    private boolean isShop;
    private ShopBean shop;

    public boolean isShop() {
        return isShop;
    }

    public void setShop(boolean shop) {
        isShop = shop;
    }

    public ShopBean getShop() {
        return shop;
    }

    public void setShop(ShopBean shop) {
        this.shop = shop;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean isSex() {
        return sex;
    }

    public void setSex(boolean sex) {
        this.sex = sex;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public String getSexStr() {
        if(sex==true){
            this.sexStr="男";
        }else{
            this.sexStr="女";
        }
        return sexStr;
    }
}
