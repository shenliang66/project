package com.czw.project.model;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by Administrator on 2015/8/30.
 */
public class _User extends BmobUser {
    public BmobFile getHeadPhoto() {
        return headPhoto;
    }
    public void setHeadPhoto(BmobFile headPhoto) {
        this.headPhoto = headPhoto;
    }
    private BmobFile headPhoto;
}
