package com.example.hnew.mvp;

import android.os.Bundle;

import com.example.hnew.base.BaseFragment;
import com.example.hnew.base.BasePresenter;
import com.example.hnew.base.BaseView;

public abstract class MvpFragment<P extends BasePresenter> extends BaseFragment {
    protected P mPresent;

    protected abstract P createPresent();

    @Override
    public void initBeforeView(Bundle savedInstanceState) {
        mPresent=createPresent();
        mPresent.attach((BaseView) this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (mPresent!=null){
            mPresent.detach((BaseView) this);
        }
    }



}