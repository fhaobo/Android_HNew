package com.example.hnew.adapter;



import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.hnew.R;
import com.example.hnew.bean.NewsResponse;


import java.util.List;


public class NewsAdapter extends BaseQuickAdapter<NewsResponse.ResultBean.DataBean, BaseViewHolder> {
    public NewsAdapter(int layoutResId, @Nullable List<NewsResponse.ResultBean.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, NewsResponse.ResultBean.DataBean item) {
        helper.setText(R.id.title,item.getTitle()).setText(R.id.author_name,item.getAuthor_name());//设置题目和来源
        if(item.getThumbnail_pic_s() != null){//判断是否有图片
            ImageView imageView = helper.getView(R.id.image);
            Glide.with(mContext).load(item.getThumbnail_pic_s()).into(imageView);
        }
        helper.addOnClickListener(R.id.item_layouts);
    }
}