package com.coolweather.app.util;

/**
 * Created by Chai on 2016/9/13.
 */
public interface HttpCallbackListener {
    void onFinish(String response);
    void onError(Exception e);
}
