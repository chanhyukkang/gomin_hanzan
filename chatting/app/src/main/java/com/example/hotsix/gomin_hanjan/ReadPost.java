package com.example.hotsix.gomin_hanjan;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hwan.chatting.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ReadPost extends AppCompatActivity {
    Button enterbt, likebutton, oldchatbutton, edit;
    TextView title,maintext;
    Intent intent2;
    String userKey;
    String userId, userPassword;
    String name;
    String trust;
    int like;
    String emotion;
    TextView liketext;
    String like1;
    String[] userInfo;
    String[] readpost;
    int oldchat = 0;
    int likebutton1 = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_readpost);

        enterbt =  (Button)findViewById(R.id.button_enter);
        title =  (TextView)findViewById(R.id.textview_title);
        maintext =  (TextView)findViewById(R.id.textview_maintext);
        liketext = (TextView) findViewById(R.id.text_like);
        likebutton = (Button) findViewById(R.id.button_like);
        oldchatbutton = (Button) findViewById(R.id.button_oldchat);
        edit = findViewById(R.id.button_edit);
        Intent intent3 = getIntent();
        userInfo = intent3.getStringArrayExtra("strings");
        readpost = intent3.getStringArrayExtra("readpost");

        userId = userInfo[0];
        userPassword = userInfo[1];
        name = userInfo[3];
        trust = userInfo[4];
        emotion = userInfo[5];
        like1 = readpost[2];

        if(!userId.equals(readpost[3])){
            oldchatbutton.setVisibility(View.GONE);
            edit.setVisibility(View.GONE);
        }

        title.setText(readpost[0]);
        maintext.setText(readpost[1]);
        liketext.setText(like1);

        final String BASE = SharedPreference.getAttribute(getApplicationContext(), "IP");

        enterbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Retrofit retrofit2 = new Retrofit.Builder()
                        .baseUrl(BASE)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                UserKeyInterface userKeyInterface = retrofit2.create(UserKeyInterface.class);
                Call<List<Dummy>> call2 = userKeyInterface.listDummies(userInfo[0], userInfo[1]);
                call2.enqueue(dummies2);
            }
        });


        likebutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(likebutton1==0) {
                    like = Integer.parseInt(like1) + 1;
                    liketext.setText(Integer.toString(like));
                    View view = View.inflate(getApplicationContext(), R.layout.toast_temp, null);
                    Toast toast = new Toast(getApplicationContext());
                    toast.setView(view);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.setDuration(Toast.LENGTH_SHORT);
                    toast.show();

                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(BASE)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

                    LikeInterface likeInterface = retrofit.create(LikeInterface.class);
                    Call<List<Dummy>> call = likeInterface.listDummies(readpost[0], Integer.toString(like));
                    call.enqueue(dummies1);
                    likebutton.setBackgroundResource(R.drawable.heart);
                    likebutton1 = 1;
                }

                else if(likebutton1==1){
                    like = Integer.parseInt(like1) - 1;
                    liketext.setText(Integer.toString(like));

                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(BASE)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

                    LikeInterface likeInterface = retrofit.create(LikeInterface.class);
                    Call<List<Dummy>> call = likeInterface.listDummies(readpost[0], Integer.toString(like));
                    call.enqueue(dummies1);
                    likebutton.setBackgroundResource(R.drawable.heart2);
                    likebutton1 = 0;
                }
            }
        });

        oldchatbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                oldchat = 1;
                Retrofit retrofit2 = new Retrofit.Builder()
                        .baseUrl(BASE)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                UserKeyInterface userKeyInterface = retrofit2.create(UserKeyInterface.class);
                Call<List<Dummy>> call2 = userKeyInterface.listDummies(userInfo[0], userInfo[1]);
                call2.enqueue(dummies2);
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Retrofit retrofit3 = new Retrofit.Builder()
                        .baseUrl(BASE)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                ChattingRemakeInterface chattingRemakeInterface = retrofit3.create(ChattingRemakeInterface.class);
                Call<List<Dummy7>> call3 = chattingRemakeInterface.listDummies(readpost[0]);
                call3.enqueue(dummies3);
            }
        });
    }

    Callback dummies1 = new Callback<List<Dummy>>() {

        @Override
        public void onResponse(Call<List<Dummy>> call, Response<List<Dummy>> response) {
            if (response.isSuccessful()) {
                List<Dummy> dummies = response.body();
                StringBuilder builder = new StringBuilder();
                for (Dummy dummy : dummies) {
                    builder.append(dummy.toString());
                }

                like1 = Integer.toString(like);
            }
        }

        @Override
        public void onFailure(Call<List<Dummy>> call, Throwable t) {

        }
    };

    Callback dummies2 = new Callback<List<Dummy>>() {

        @Override
        public void onResponse(Call<List<Dummy>> call1, Response<List<Dummy>> response) {
            if (response.isSuccessful()) {
                List<Dummy> dummies = response.body();
                StringBuilder builder = new StringBuilder();
                for (Dummy dummy : dummies) {
                    builder.append(dummy.toString());
                }

                if(oldchat==1){
                    userKey = builder.toString();
                    intent2 = new Intent(ReadPost.this, OldChatting.class);
                    String[] information1 = new String[]{userId, userPassword, userKey, name, trust, emotion, readpost[0]};
                    intent2.putExtra("strings", information1);
                    startActivity(intent2);
                    oldchat = 0;
                }

                else {
                    userKey = builder.toString();
                    intent2 = new Intent(ReadPost.this, ChattingActivity.class);
                    String[] information1 = new String[]{userId, userPassword, userKey, name, trust, emotion, readpost[0]};
                    intent2.putExtra("strings", information1);
                    startActivity(intent2);
                }
            }
        }

        @Override
        public void onFailure(Call<List<Dummy>> call1, Throwable t) {

        }
    };

    Callback dummies3 = new Callback<List<Dummy7>>() {

        @Override
        public void onResponse(Call<List<Dummy7>> call3, Response<List<Dummy7>> response) {
            if (response.isSuccessful()) {
                List<Dummy7> dummies = response.body();
                String[] build;
                String category = null;
                String agemin = null;
                String agemax = null;
                String sex = null;
                String trust = null;
                String title = null;
                String maintext = null;

                for (Dummy7 dummy : dummies) {
                    build = dummy.toString().split(",");
                    category = build[0];
                    agemin = build[1];
                    agemax = build[2];
                    sex = build[3];
                    trust = build[4];
                    title = build[5];
                    maintext = build[6];
                }

                String[] chattinglist = new String[] {category, agemin, agemax, sex, trust, title, maintext};

                Intent intent = new Intent(getApplicationContext(),ChattingRoomReMake.class);
                intent.putExtra("strings",userInfo);
                intent.putExtra("chattinglist",chattinglist);
                intent.putExtra("readpost",readpost);
                startActivityForResult(intent,100);
            }
        }

        @Override
        public void onFailure(Call<List<Dummy7>> call3, Throwable t) {

        }
    };
}