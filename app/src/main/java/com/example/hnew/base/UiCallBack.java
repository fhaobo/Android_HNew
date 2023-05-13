package com.example.hnew.base;

import android.os.Bundle;

//UI回调接口
public interface UiCallBack {
    //初始化savedInstanceState
    void initBeforeView(Bundle savedInstanceState);

    //初始化视图
    void initData(Bundle savedInstanceState);

    //获取布局ID
    int getLayoutId();
}
