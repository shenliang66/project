package com.czw.project.model;

import cn.bmob.v3.BmobObject;

/**
 * Created by Administrator on 2015/8/30.
 */
public class Comment extends BmobObject {

    private String content;
    private TourStory mTourStory;
    private _User author;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public TourStory getTourStory() {
        return mTourStory;
    }

    public void setTourStory(TourStory tourStory) {
        this.mTourStory = tourStory;
    }

    public _User getAuthor() {
        return author;
    }

    public void setAuthor(_User author) {
        this.author = author;
    }
}
