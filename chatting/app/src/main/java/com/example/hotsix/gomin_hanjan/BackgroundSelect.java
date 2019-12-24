package com.example.hotsix.gomin_hanjan;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.example.hwan.chatting.R;

public class BackgroundSelect extends Activity {
    // references to our images
    DisplayMetrics mMetrics;
    Intent intent,intent2;
    String id, title, name, emotion;
    String[] userInfo;
    String[] usertitle;
    BackgroundAdapter backgroundAdapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_background_select);
        GridView gridview = (GridView) findViewById(R.id.gridview_background2);
        DisplayMetrics mMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(mMetrics);
        int rowWidth = (mMetrics.widthPixels) / 5;
        backgroundAdapter = new BackgroundAdapter(rowWidth,this);
        gridview.setAdapter(backgroundAdapter);
        backgroundAdapter.add(0,"기본배경1");
        backgroundAdapter.add(1,"기본배경2");
        backgroundAdapter.add(2,"눈오는 겨울");
        backgroundAdapter.add(3,"소주");
        backgroundAdapter.add(4,"메리크리스마스~");
        backgroundAdapter.add(5,"치즈");
        backgroundAdapter.add(6,"치킨");
        gridview.setOnItemClickListener(gridviewOnItemClickListener);
    }

    private GridView.OnItemClickListener gridviewOnItemClickListener
            = new GridView.OnItemClickListener() {
        public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                long arg3) {
            intent2 = getIntent();
            userInfo = intent2.getStringArrayExtra("strings");
            usertitle = intent2.getStringArrayExtra("usertitle");
            Toast.makeText(BackgroundSelect.this, "배경화면이 변경되었습니다", Toast.LENGTH_LONG).show();

            intent = new Intent(BackgroundSelect.this, ChattingActivity.class);
            intent.putExtra("strings", userInfo);
            intent.putExtra("usertitle", usertitle);
            intent.putExtra("backgroundnumber",arg2);
            startActivity(intent);

        }
    };
}