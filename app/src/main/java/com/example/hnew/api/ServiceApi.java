package com.example.hnew.api;

import com.example.hnew.bean.NewsResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ServiceApi {
    @GET("/toutiao/index?key=0101ebb1424abc47b8b23a2eeddfa2da")//get请求
    Call<NewsResponse> //数据结构
    getNews(@Query("type") String type);//获取数据类型
}
