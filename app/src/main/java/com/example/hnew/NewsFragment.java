package com.example.hnew;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.DialogTitle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.hnew.adapter.NewsAdapter;
import com.example.hnew.bean.NewsResponse;
import com.example.hnew.contract.NewsContract;
import com.example.hnew.mvp.MvpFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;


public class NewsFragment extends MvpFragment<NewsContract.NewsPresenter> implements NewsContract.INewsView {

    private FloatingActionButton fab;
    private RecyclerView rv;
    private SmartRefreshLayout smartRefreshLayout;
    private static final int UPNEWS_INSERT = 0;
    private NewsAdapter newsAdapter;
    private  String data;
    public List<NewsResponse.ResultBean.DataBean> newslist;


    @Override
    public void initData(Bundle savedInstanceState) {
        newslist = new ArrayList<>();
        rv = (RecyclerView) rootView.findViewById(R.id.listView);
        fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
        smartRefreshLayout = (SmartRefreshLayout) rootView.findViewById(R.id.smart_refresh);
        newsAdapter = new NewsAdapter(R.layout.item_layout,newslist);
        LinearLayoutManager managerHourly = new LinearLayoutManager(context);
        managerHourly.setOrientation(RecyclerView.VERTICAL);//设置列表为横向
        rv.setLayoutManager(managerHourly);
        rv.setAdapter(newsAdapter);
    }

    @Override
    public int getLayoutId() {
        return R.layout.list_item;
    }

    @Override
    protected NewsContract.NewsPresenter createPresent() {
        return new NewsContract.NewsPresenter();
    }



    @SuppressLint("HandlerLeak")
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
     //   onAttach(getActivity());
        Bundle bundle = getArguments();
        data = bundle.getString("name","top");
        Message message = Message.obtain();
        message.what = UPNEWS_INSERT;
        //置顶功能
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rv.smoothScrollToPosition(0);
            }
        });

        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mPresent.TopNews(data);
                Log.d("data", " "+ data);
            }
        });
        //把网络请求内容给webactivity，新闻详情显示
        newsAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(getActivity(),WebActivity.class);
                String url = newslist.get(position).getUrl();
                String title = newslist.get(position).getTitle();
                intent.putExtra("url",url);
                intent.putExtra("title",title);
                Log.d("url+title", "onItemChildClick: "+url+title);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("Okhttp", "onResume: " + newslist.size());
        newsAdapter.notifyDataSetChanged();
    }

    //新闻预览显示
    @Override
    public void getTopNews(Response<NewsResponse> response) {
        smartRefreshLayout.finishRefresh();
        if(response.code() == 200){
            List<NewsResponse.ResultBean.DataBean> dataBeans = response.body().getResult().getData();
            if(dataBeans != null && dataBeans.size() > 0){
                newslist.addAll(dataBeans);
                Log.d("Okhttp", "getTopNews: " + newslist.size());
                newsAdapter.notifyDataSetChanged();
            }else{
                Toast.makeText(getActivity(),"今日已达到访问次数上线",Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(getActivity(),"网络异常",Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void getDataFailed() {
        Toast.makeText(getActivity(),"网络异常",Toast.LENGTH_SHORT).show();
    }

}
