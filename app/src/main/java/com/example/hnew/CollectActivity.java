package com.example.hnew;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.example.hnew.WebActivity;
import androidx.annotation.LongDef;
import androidx.appcompat.widget.Toolbar;
import com.example.hnew.MainActivity;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;

public class CollectActivity extends AppCompatActivity {


    public static CollectActivity collectActivity;
    private ListView listView;
    String url,title;
    private SharedPreferences sp1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collect);
        collectActivity = this;
        listView = findViewById(R.id.list_news);



        sp1 =  this.getSharedPreferences("newsinfo",this.MODE_PRIVATE);


        url = sp1.getString("newsurl", null);
        title = sp1.getString("newstitle", null);

//        url = getIntent().getStringExtra("url");
//        title = getIntent().getStringExtra("title");


        Log.d("ooo", "onStart: "+url+title);

        String[] data = {title};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(CollectActivity.this, android.R.layout.simple_list_item_1,data);
        listView.setAdapter(adapter);

    }

    @Override
    protected void onStart() {
        super.onStart();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent  = new Intent(collectActivity,WebActivity.class);
                intent.putExtra("url",url);
                intent.putExtra("title",title);
                startActivity(intent);

            }
        });
    }
}
