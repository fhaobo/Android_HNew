package com.example.hnew.base;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.media.MediaBrowserCompat;
import android.view.View;
import android.widget.Toolbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hnew.R;

public abstract class BaseActivity extends AppCompatActivity implements UiCallBack {

    protected Activity context;//上下文
   // private Unbinder unbinder;
    private Dialog mDialog;//加载弹窗
    private static final int FAST_CLICK_DELAY_TIME = 500;
    private static long lastClickTime;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initBeforeView(savedInstanceState);//绑定视图
        this.context = this;//获取上下文
        //绑定视图xml
        if (getLayoutId() > 0) {
            setContentView(getLayoutId());
          //  unbinder = KnifeKit.bind(this);
        }
        initData(savedInstanceState);
    }

    @Override
    public void initBeforeView(Bundle savedInstanceState) {
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    //弹窗出现
    public void showLoadingDialog(){
        if (mDialog == null) {
            mDialog = new Dialog(context, R.style.loading_dialog);
        }
        mDialog.setContentView(R.layout.dialog_loading);
        mDialog.setCancelable(false);//设置返回键无效
        mDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);//设置背景颜色透明
        mDialog.show();
    }
    //弹窗消失
    public void dismissLoadingDialog(){
        if (mDialog != null) {
            mDialog.dismiss();//销毁
        }
        mDialog = null;
    }

    //无参数返回
    protected void Back(){
        context.finish();
        if(!isFastClick()){
            context.finish();
        }
    }

    //有参数返回
    protected void Back(Toolbar toolbar) {
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    context.finish();
                    if (!isFastClick()){
                        context.finish();
                }
            };

        });
    }


    //点击间隔不能少于500ms
    protected static boolean isFastClick() {
        boolean flag = true;
        long currentClickTime = System.currentTimeMillis();
        if ((currentClickTime - lastClickTime) >= FAST_CLICK_DELAY_TIME) {
            flag = false;
        }
        lastClickTime = currentClickTime;

        return flag;
    }

}