package com.czw.project.util;

/**
 * Created by shenliang on 2015/9/17.
 */
public interface HttpCallbackListener {
    void onFinish(String response);
    void onError(Exception e);
}
