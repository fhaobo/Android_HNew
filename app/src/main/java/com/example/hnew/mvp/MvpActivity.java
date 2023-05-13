package com.example.hnew.mvp;

import android.os.Bundle;

import com.example.hnew.base.BaseActivity;
import com.example.hnew.base.BasePresenter;
import com.example.hnew.base.BaseView;

public abstract class MvpActivity<P extends BasePresenter> extends BaseActivity {
    protected P mPresent;
    //创建Presenter
    protected abstract P createPresent();
    //绑定view
    @Override
    public void initBeforeView(Bundle savedInstanceState) {
        mPresent=createPresent();
        mPresent.attach((BaseView) this);
    }



    //解绑
    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresent.detach((BaseView) this);
    }

}