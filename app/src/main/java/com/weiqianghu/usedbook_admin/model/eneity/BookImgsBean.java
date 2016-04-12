package com.weiqianghu.usedbook_admin.model.eneity;

import java.io.Serializable;

import cn.bmob.v3.BmobObject;

/**
 * Created by weiqianghu on 2016/3/10.
 */
public class BookImgsBean extends BmobObject implements Serializable{
    private String img;
    private BookBean book;

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public BookBean getBook() {
        return book;
    }

    public void setBook(BookBean book) {
        this.book = book;
    }
}
