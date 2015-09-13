package com.czw.project.model;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by shenliang on 2015/8/31.
 */
public class FarmStory extends BmobObject{


    private BmobFile picture1;
    private BmobFile picture2;
    private BmobFile picture3;
    private BmobFile picture4;

    public BmobFile getHeadPhoto() {
        return headPhoto;
    }

    public void setHeadPhoto(BmobFile headPhoto) {
        this.headPhoto = headPhoto;
    }

    private BmobFile headPhoto;
    private String title;

    public BmobFile getPicture2() {
        return picture2;
    }

    public void setPicture2(BmobFile picture2) {
        this.picture2 = picture2;
    }

    public BmobFile getPicture3() {
        return picture3;
    }

    public void setPicture3(BmobFile picture3) {
        this.picture3 = picture3;
    }

    public BmobFile getPicture4() {
        return picture4;
    }

    public void setPicture4(BmobFile picture4) {
        this.picture4 = picture4;
    }

    public String getBoss() {
        return boss;
    }

    public void setBoss(String boss) {
        this.boss = boss;
    }

    public String getBossPhone() {
        return bossPhone;
    }

    public void setBossPhone(String bossPhone) {
        this.bossPhone = bossPhone;
    }

    private String address;
    private String boss;
    private String bossPhone;


    public BmobFile getPicture1() {
        return picture1;
    }

    public void setPicture1(BmobFile picture1) {
        this.picture1 = picture1;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
