package com.example.hotsix.gomin_hanjan;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.hwan.chatting.R;

import java.util.List;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Login extends AppCompatActivity {
    private static final String BASE = "http://15.165.101.224:3000";

    EditText position;
    Button getButton, button_developer;
    String info_id, info_password;
    String title;
    String result_id;
    String name;
    String trust;
    String emotion;
    int admin = 0;
    int login = 0;
    int user = 0;

    String[] usertitle;
    StringBuilder builder_like = new StringBuilder();
    StringBuilder builder_title = new StringBuilder();
    StringBuilder builder_category = new StringBuilder();
    StringBuilder builder_profile = new StringBuilder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        final EditText id = (EditText) findViewById(R.id.edittext_id);
        SharedPreference.setAttribute(getApplicationContext(), "IP", BASE);
        final EditText password = (EditText) findViewById(R.id.edittext_password);
        final Button login = (Button) findViewById(R.id.button_login);
        Button signup = (Button) findViewById(R.id.button_signup);
//        Button button_developer = (Button) findViewById(R.id.button_developer);
        Button findpassword = findViewById(R.id.button_findpassword);
//        CheckBox remember = findViewById(R.id.remember);

        id.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if ((keyEvent.getAction() == KeyEvent.ACTION_DOWN) && i == KeyEvent.KEYCODE_ENTER) {
                    password.requestFocus();
                    return true;
                }
                return false;
            }
        });
        password.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if ((keyEvent.getAction() == KeyEvent.ACTION_DOWN) && i == KeyEvent.KEYCODE_ENTER) {
                    login.performClick();
                    return true;
                }
                return false;
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(BASE)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                GetService service = retrofit.create(GetService.class);
                String id1 = String.valueOf(id.getText().toString());
                String password1 = String.valueOf(password.getText().toString());
                info_id=id1;
                info_password=password1;
                Call<List<Dummy3>> call = service.listDummies(id1, password1);
                call.enqueue(dummies);
            }
        });

        signup.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),SignUp.class);
                startActivityForResult(intent,100);
            }
        });

        findpassword.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),FindPassword.class);
                startActivityForResult(intent,100);
            }
        });

//        button_developer.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                admin = 1;
//
//                Retrofit retrofit1 = new Retrofit.Builder()
//                        .baseUrl(BASE)
//                        .addConverterFactory(GsonConverterFactory.create())
//                        .build();
//
//                UserInterface userInterface = retrofit1.create(UserInterface.class);
//                Call<List<Dummy3>> call1 = userInterface.listDummies("admin");
//                call1.enqueue(dummies1);
//            }
//        });
    }

    @Override
    public void onBackPressed() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            finishAffinity();
        }
        System.runFinalization();
        System.exit(0);
    }

    Callback dummies = new Callback<List<Dummy3>>() {

        @Override
        public void onResponse(Call<List<Dummy3>> call, Response<List<Dummy3>> response) {
            if (response.isSuccessful()) {
                List<Dummy3> dummies = response.body();
                StringBuilder builder = new StringBuilder();
                for (Dummy3 dummy : dummies) {
                    builder.append(dummy.toString()+",");
                }

                String[] result;
                result = builder.toString().split(",");
                result_id = result[0];
                name = result[1];
                trust = result[2];
                emotion = result[3];

                if(result_id.equals("1")){
                    login=1;
                    Retrofit retrofit1 = new Retrofit.Builder()
                            .baseUrl(BASE)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

                    UserInterface userInterface = retrofit1.create(UserInterface.class);
                    Call<List<Dummy3>> call1 = userInterface.listDummies(info_id);
                    call1.enqueue(dummies1);
                }
                else if(result_id.equals("2")){
                    Toasty.error(getApplicationContext(), "ID, Password를 입력하세요", Toast.LENGTH_SHORT).show();
                }
                else if(result_id.equals("3")){
                    Toasty.error(getApplicationContext(), "ID를 입력하세요", Toast.LENGTH_SHORT).show();
                }
                else if(result_id.equals("4")){
                    Toasty.error(getApplicationContext(), "Password를 입력하세요", Toast.LENGTH_SHORT).show();
                }
                else if(result_id.equals("0")){
                    Toasty.error(getApplicationContext(),"회원가입을 해주세요", Toast.LENGTH_SHORT).show();
                }
            }
        }

        @Override
        public void onFailure(Call<List<Dummy3>> call, Throwable t) {

        }
    };

    Callback dummies1 = new Callback<List<Dummy3>>() {

        @Override
        public void onResponse(Call<List<Dummy3>> call1, Response<List<Dummy3>> response) {
            if (response.isSuccessful()) {
                user = 1;

                List<Dummy3> dummies = response.body();
                String[] build1;
                StringBuilder builder_title1 = new StringBuilder();
                StringBuilder builder_category1 = new StringBuilder();
                StringBuilder builder_profile1 = new StringBuilder();
                StringBuilder builder_like1 = new StringBuilder();
                for (Dummy3 dummy : dummies) {
                    build1 = dummy.toString().split(",");
                    builder_title1.append(build1[0]+",");
                    builder_category1.append(build1[1]+",");
                    builder_profile1.append(build1[2]+",");
                    builder_like1.append(build1[3]+",");
                }
                usertitle= new String[]{String.valueOf(builder_title1), String.valueOf(builder_category1), String.valueOf(builder_profile1), String.valueOf(builder_like1)};
                if(login==1) {
                    Retrofit retrofit2 = new Retrofit.Builder()
                            .baseUrl(BASE)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

                    ChattingInformationInterface chattingInformationInterface = retrofit2.create(ChattingInformationInterface.class);
                    Call<List<Dummy3>> call2 = chattingInformationInterface.listDummies(info_id);
                    call2.enqueue(dummies2);
                }

                else if(admin==1){
                    Retrofit retrofit2 = new Retrofit.Builder()
                            .baseUrl(BASE)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

                    ChattingInformationInterface chattingInformationInterface = retrofit2.create(ChattingInformationInterface.class);
                    Call<List<Dummy3>> call2 = chattingInformationInterface.listDummies("admin");
                    call2.enqueue(dummies2);
                }
            }
        }

        @Override
        public void onFailure(Call<List<Dummy3>> call1, Throwable t) {

        }
    };

    Callback dummies2 = new Callback<List<Dummy3>>() {

        @Override
        public void onResponse(Call<List<Dummy3>> call2, Response<List<Dummy3>> response) {
            if (response.isSuccessful()) {
                List<Dummy3> dummies = response.body();
                String[] build;
                for (Dummy3 dummy : dummies) {
                    build = dummy.toString().split(",");
                    builder_like.append(build[0]+",");
                    builder_title.append(build[1]+",");
                    builder_category.append(build[2]+",");
                    builder_profile.append(build[3]+",");
                }

                if(login==1&&user==1){
                    login = 0;
                    user = 0;
                    Toasty.success(getApplicationContext(), "로그인 성공", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                    String[] information = new String[] {info_id, info_password, String.valueOf(builder_title), name, trust, emotion, String.valueOf(builder_like), String.valueOf(builder_category), String.valueOf(builder_profile)};
                    intent.putExtra("strings", information);
                    intent.putExtra("usertitle", usertitle);
                    startActivityForResult(intent,100);
                }

                if(admin==1&&user==1){
                    admin=0;
                    user = 0;
                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                    String[] information = new String[] {"admin", "admin", String.valueOf(builder_title), "Admin", "0", "0", String.valueOf(builder_like), String.valueOf(builder_category), String.valueOf(builder_profile)};
                    intent.putExtra("strings", information);
                    intent.putExtra("usertitle", usertitle);
                    String name = emotion;
                    startActivityForResult(intent,100);
                }
            }
        }

        @Override
        public void onFailure(Call<List<Dummy3>> call1, Throwable t) {

        }
    };
}