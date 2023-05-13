package com.example.hnew.contract;

import android.util.Log;

import androidx.appcompat.widget.DialogTitle;

import com.example.hnew.api.ServiceApi;
import com.example.hnew.base.BasePresenter;
import com.example.hnew.base.BaseView;
import com.example.hnew.bean.NewsResponse;
import com.example.hnew.net.ServiceGenerator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsContract {


    public static class NewsPresenter extends BasePresenter<INewsView>{

        public void  TopNews(String type){
            ServiceApi serviceApi = ServiceGenerator.createService(ServiceApi.class, 0);
            serviceApi.getNews(type).enqueue(new Callback<NewsResponse>() {
                @Override
                public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response) {
                    if(getView() != null){
                        getView().getTopNews(response);
                    }

                }

                @Override
                public void onFailure(Call<NewsResponse> call, Throwable t) {
                    if(getView() != null){
                        getView().getDataFailed();
                    }
                }
            });

        }

    }

    public interface INewsView extends BaseView {

        //获取新闻头条
        void getTopNews(Response<NewsResponse> response);

        void getDataFailed();

    }
}
