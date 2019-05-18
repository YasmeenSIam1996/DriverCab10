package com.example.drivercab10.Interfaces;


public interface UniversalStringCallBack {

    void onResponse(String result);
    void onFailure(String result);
    void onFinish();
    void OnError(String message);

}
