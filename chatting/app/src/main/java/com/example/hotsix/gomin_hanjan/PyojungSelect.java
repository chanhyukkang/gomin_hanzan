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

import java.util.List;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PyojungSelect extends Activity {
    // references to our images


    Intent intent,intent2;
    String result;
    String id, password, title, name, trust, emotion, builder_id, builder_category, builder_profile;
    String[] userInfo;
    String[] usertitle;
    ImageAdapter imageAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pyojung);
        GridView gridview = (GridView) findViewById(R.id.gridview);


        DisplayMetrics mMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(mMetrics);
        int rowWidth = (mMetrics.widthPixels) / 5;
        imageAdapter = new ImageAdapter(rowWidth,this);
        gridview.setAdapter(imageAdapter);
        imageAdapter.add(0,"기뻐요");
        imageAdapter.add(1,"놀라워요");
        imageAdapter.add(2,"화나요");
        imageAdapter.add(3,"두려워요");
        imageAdapter.add(4,"싫어요");
        imageAdapter.add(5,"슬퍼요");
        gridview.setOnItemClickListener(gridviewOnItemClickListener);

    }

    private GridView.OnItemClickListener gridviewOnItemClickListener
            = new GridView.OnItemClickListener() {

        public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                long arg3) {
            intent2 = getIntent();
            userInfo = intent2.getStringArrayExtra("strings");
            usertitle = intent2.getStringArrayExtra("usertitle");
            userInfo[5] = Integer.toString(arg2);
            id = userInfo[0];
            password = userInfo[1];
            title = userInfo[2];
            name = userInfo[3];
            trust = userInfo[4];
            emotion = userInfo[5];
            builder_id = userInfo[6];
            builder_category = userInfo[7];
            builder_profile = userInfo[8];
            final String BASE = SharedPreference.getAttribute(getApplicationContext(), "IP");

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            EmotionInterface emotionInterface = retrofit.create(EmotionInterface.class);
            Call<List<Dummy>> call = emotionInterface.listDummies(userInfo[0], userInfo[5]);
            call.enqueue(dummies);
        }
    };

    Callback dummies = new Callback<List<Dummy>>() {

        @Override
        public void onResponse(Call<List<Dummy>> call, Response<List<Dummy>> response) {
            if (response.isSuccessful()) {
                List<Dummy> dummies = response.body();
                StringBuilder builder = new StringBuilder();
                for (Dummy dummy : dummies) {
                    builder.append(dummy.toString());
                }
                result=builder.toString();

                if(result.equals("0")){
                    switch(Integer.parseInt(emotion)) {
                        case 0:
                            Toasty.normal(PyojungSelect.this, "기쁜일이 있으셨군요^_^!!!!", Toast.LENGTH_SHORT).show();
                            break;
                        case 1:
                            Toasty.normal(PyojungSelect.this, "깜짝 놀라는 일이있으셨군요!", Toast.LENGTH_SHORT).show();
                            break;
                        case 2:
                            Toasty.normal(PyojungSelect.this, "화나는 일이 있으셨군요.. ㅠㅠ", Toast.LENGTH_SHORT).show();
                            break;
                        case 3:
                            Toasty.normal(PyojungSelect.this, "무서운일이 있으셨군요..후덜덜덜", Toast.LENGTH_SHORT).show();
                            break;
                        case 4:
                            Toasty.normal(PyojungSelect.this, "매우 싫은일이 있으셨군요..", Toast.LENGTH_SHORT).show();
                            break;
                        case 5:
                            Toasty.normal(PyojungSelect.this, "슬픈 일이 있으셨군요.. ㅠㅠ", Toast.LENGTH_SHORT).show();
                            break;

                    }
                    intent = new Intent(PyojungSelect.this, MainActivity.class);
                    String[] information = new String[]{id, password, String.valueOf(title), name, trust, emotion, builder_id, builder_category, builder_profile};
                    intent.putExtra("strings", information);
                    intent.putExtra("usertitle", usertitle);
                    startActivity(intent);
                }
            }
        }

        @Override
        public void onFailure(Call<List<Dummy>> call, Throwable t) {

        }
    };
}