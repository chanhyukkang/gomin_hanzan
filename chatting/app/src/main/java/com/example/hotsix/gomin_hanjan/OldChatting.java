package com.example.hotsix.gomin_hanjan;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.hwan.chatting.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class OldChatting extends AppCompatActivity {
    String[] userInfo;
    String userId, userPassword;
    String name, trust, emotion;
    String title;
    int userKey;
    TextView chattingroomname;
    Button enterroom;
    ChattingAdapter m_Adapter;
    RelativeLayout layout1;
    ScrollView scrollview_old;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oldchatting);

        final String BASE = SharedPreference.getAttribute(getApplicationContext(), "IP");

        m_Adapter = new ChattingAdapter();

        final ListView listview = (ListView) findViewById(R.id.chatting_old);
        listview.setAdapter(m_Adapter);
        listview.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);

        layout1 = (RelativeLayout) findViewById(R.id.chattingview_old);
        scrollview_old = (ScrollView) findViewById(R.id.scrollview_old);

        chattingroomname = findViewById(R.id.chattingroomname_old);
        enterroom = findViewById(R.id.entermyroom);

        Intent intent = getIntent();
        userInfo = intent.getStringArrayExtra("strings");

        userId = userInfo[0];
        userPassword = userInfo[1];
        userKey = Integer.parseInt(userInfo[2]);
        name = userInfo[3];
        trust = userInfo[4];
        emotion = userInfo[5];
        title = userInfo[6];

        chattingroomname.setText(title);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ChattingListInterface chattingListInterface = retrofit.create(ChattingListInterface.class);
        Call<List<Dummy1>> call = chattingListInterface.listDummies(title);
        call.enqueue(dummies);

        enterroom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OldChatting.this, ChattingActivity.class);
                intent.putExtra("strings", userInfo);
                startActivity(intent);
            }
        });

        listview.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                scrollview_old.requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });
    }

    Callback dummies = new Callback<List<Dummy1>>() {

        @Override
        public void onResponse(Call<List<Dummy1>> call, Response<List<Dummy1>> response) {
            if (response.isSuccessful()) {
                List<Dummy1> dummies = response.body();
                StringBuilder builder = new StringBuilder();
                String[] build;
                for (Dummy1 dummy : dummies) {
                    build = dummy.toString().split(",");

                    if(userKey==Integer.parseInt(build[1])){
                        m_Adapter.add(Integer.parseInt(build[2]), build[0], 0, String.valueOf(build[1]));
                    }
                    else{
                        m_Adapter.add(Integer.parseInt(build[2]), build[0], 1, String.valueOf(build[1]));
                    }
                }
                m_Adapter.notifyDataSetChanged();
            }
        }

        @Override
        public void onFailure(Call<List<Dummy1>> call, Throwable t) {

        }
    };

}