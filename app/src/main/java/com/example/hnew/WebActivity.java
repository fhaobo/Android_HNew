package com.example.hnew;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import android.app.SearchManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.http.SslError;
import android.os.Bundle;
import android.util.Log;

import android.view.Menu;
import android.view.MenuItem;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;

import com.example.hnew.register.Mysql;

import java.sql.Connection;
import java.util.HashMap;

public class WebActivity extends AppCompatActivity {
    private WebView webView;
    private Toolbar toolbar;
    String url,title;
    Mysql mysql;
    SQLiteDatabase db;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        webView = (WebView) findViewById(R.id.webView);
        toolbar = (Toolbar) findViewById(R.id.toolbar_webview);
        mysql = new Mysql(this,"news",null,1);      //建数据库
        db = mysql.getReadableDatabase();
        sp = this.getSharedPreferences("newsinfo",this.MODE_PRIVATE);
    }

    @Override
    protected void onStart() {
        super.onStart();
        title = getIntent().getStringExtra("title");
        url = getIntent().getStringExtra("url");//获取url
        Log.d("Okhttp", "onStart: " + url+title);
        webView.loadUrl(url);
        Log.d("urlis", "onStart: "+url);
        toolbar.setTitle("新闻");
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);//显示返回图标
            actionBar.setHomeAsUpIndicator(R.drawable.ic_chevron_left);//设置图标资源
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_webview,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        switch (item.getItemId()){
            case R.id.news_collect:

                ContentValues cv = new ContentValues();
                cv.put("newsurl",url);
                cv.put("newstitle",title);
                db.insert("logins",null,cv);
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("newsurl",url);
                editor.putString("newstitle",title);
                editor.commit();



                //CollectActivity.collectActivity.finish();
                Toast.makeText(WebActivity.this,"success",Toast.LENGTH_SHORT).show();
                break;
            case android.R.id.home:
                WebActivity.this.finish();
                break;
            case R.id.news_feedback:
                final EditText ed = new EditText(WebActivity.this);
                AlertDialog.Builder dialog = new AlertDialog.Builder(WebActivity.this);
                dialog.setTitle("举报");
                dialog.setView(ed);
                dialog.setCancelable(false);
                dialog.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(WebActivity.this,"accept",Toast.LENGTH_SHORT).show();
                    }
                });

                dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(WebActivity.this, "", Toast.LENGTH_SHORT).show();
                    }
                });
                dialog.show();
                break;
            default:

                break;
        }



        return true;
    }


}