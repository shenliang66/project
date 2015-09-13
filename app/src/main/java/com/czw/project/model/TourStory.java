package com.czw.project.model;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;


public class TourStory extends BmobObject {
    private String title;
    private String content;
    private _User author;
    private BmobFile picture1;
    private BmobFile picture2;
    private BmobFile picture3;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    private String fileName;

    public BmobFile getPicture1() {
        return picture1;
    }

    public void setPicture1(BmobFile picture1) {
        this.picture1 = picture1;
    }

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

    public _User getAuthor() {
        return author;
    }

    public void setAuthor(_User author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
