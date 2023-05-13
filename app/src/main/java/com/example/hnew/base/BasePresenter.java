package com.example.hnew.base;
import java.lang.ref.WeakReference;

public class BasePresenter <V extends  BaseView>{

    //弱引用view
    private WeakReference<V> mWeakReference;
    private V mview;

    //绑定view
    public void attach(V view){
        mview =view;
        mWeakReference=new WeakReference<V>(view);
    }

    //解绑view
    public void detach(V v){
        mview = null;
        if (mWeakReference!=null){
            mWeakReference.clear();
            mWeakReference=null;
        }
    }

    //获取view
    public V getView(){
        if (mWeakReference!=null){
            return mWeakReference.get();
        }
        return null;
    }

    //判断view是否绑定
    public boolean isViewAttach(){
        return mview != null;
    }
}
