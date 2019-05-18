package com.example.drivercab10.Interfaces;


public interface UniversalCallBack {

    void onResponse(Object result);
    void onFailure(Object result);
    void onFinish();
    void OnError(String message);

}
