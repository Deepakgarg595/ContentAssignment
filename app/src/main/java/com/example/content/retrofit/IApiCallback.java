package com.example.content.retrofit;


public interface IApiCallback {

    void onSuccess(Object type, Object data, Object extraData);

    void onFailure(Object data);
}
