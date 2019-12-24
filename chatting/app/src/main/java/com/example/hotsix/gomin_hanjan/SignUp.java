package com.example.hotsix.gomin_hanjan;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.hwan.chatting.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SignUp extends AppCompatActivity {

    EditText name, age, sex, id, password, password2;
    Button signup, idConfirm, promise, backbutton;
    Intent intent;
    RadioGroup radioGroup;
    CheckBox checkBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        final String BASE = SharedPreference.getAttribute(getApplicationContext(), "IP");
        name = (EditText)findViewById(R.id.name);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        age = (EditText)findViewById(R.id.age);
        id = (EditText)findViewById(R.id.id);
        password = (EditText)findViewById(R.id.password);
        password2 = (EditText)findViewById(R.id.password2);
        signup = findViewById(R.id.signup);
        idConfirm = findViewById(R.id.insert);
        promise = findViewById(R.id.promise);
        checkBox = findViewById(R.id.checkBox);
        backbutton = findViewById(R.id.backbutton);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkBox.isChecked()) {
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(BASE)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

                    SignUpInterface signUpInterface = retrofit.create(SignUpInterface.class);
                    String name1 = String.valueOf(name.getText().toString());
                    RadioButton rd = (RadioButton) findViewById(radioGroup.getCheckedRadioButtonId());
                    String sex1 = rd.getText().toString();
                    if (sex1.equals("남")) {
                        sex1 = "0";
                    } else if (sex1.equals("여")) {
                        sex1 = "1";
                    }
                    String age1 = String.valueOf(age.getText().toString());
                    String id1 = String.valueOf(id.getText().toString());
                    String password_1 = String.valueOf(password.getText().toString());
                    String password_2 = String.valueOf(password2.getText().toString());

                    Call<List<Dummy>> call = signUpInterface.listDummies(name1, sex1, age1, id1, password_1, password_2);
                    call.enqueue(dummies);
                }
                else{
                    Toast.makeText(getApplicationContext(), "약속 지키셔야죠", Toast.LENGTH_SHORT).show();
                }
            }
        });
        name.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if ((keyEvent.getAction() == KeyEvent.ACTION_DOWN) && i == KeyEvent.KEYCODE_ENTER) {
                    age.requestFocus();
                    return true;
                }
                return false;
            }
        });
        age.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if ((keyEvent.getAction() == KeyEvent.ACTION_DOWN) && i == KeyEvent.KEYCODE_ENTER) {
                    id.requestFocus();
                    return true;
                }
                return false;
            }
        });
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
                    password2.requestFocus();
                    return true;
                }
                return false;
            }
        });
        password2.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if ((keyEvent.getAction() == KeyEvent.ACTION_DOWN) && i == KeyEvent.KEYCODE_ENTER) {
                    checkBox.requestFocus();
                    return true;
                }
                return false;
            }
        });
        idConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(BASE)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                IdInterface idInterface = retrofit.create(IdInterface.class);
                String id1 = String.valueOf(id.getText().toString());
                Call<List<Dummy>> call = idInterface.listDummies(id1);
                call.enqueue(dummies1);
            }
        });

        promise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),SignUp_Promise.class);
                startActivityForResult(intent,100);
            }
        });

        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),Login.class);
                startActivityForResult(intent,100);
            }
        });
    }

    Callback dummies = new Callback<List<Dummy>>() {

        @Override
        public void onResponse(Call<List<Dummy>> call, Response<List<Dummy>> response) {
            if (response.isSuccessful()) {
                List<Dummy> dummies = response.body();
                StringBuilder builder = new StringBuilder();
                for (Dummy dummy : dummies) {
                    builder.append(dummy.toString());
                }
                if(builder.toString().equals("0")){
                    Toast.makeText(getApplicationContext(), "Registered", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(),Login.class);
                    startActivityForResult(intent,100);
                }
                else if(builder.toString().equals("1")){
                    Toast.makeText(getApplicationContext(), "ID가 중복입니다", Toast.LENGTH_SHORT).show();
                }
                else if(builder.toString().equals("2")){
                    Toast.makeText(getApplicationContext(), "이름을 입력하세요", Toast.LENGTH_SHORT).show();
                }
                else if(builder.toString().equals("3")){
                    Toast.makeText(getApplicationContext(), "성별을 입력하세요", Toast.LENGTH_SHORT).show();
                }
                else if(builder.toString().equals("4")){
                    Toast.makeText(getApplicationContext(), "나이를 입력하세요", Toast.LENGTH_SHORT).show();
                }
                else if(builder.toString().equals("5")){
                    Toast.makeText(getApplicationContext(), "ID를 입력하세요", Toast.LENGTH_SHORT).show();
                }
                else if(builder.toString().equals("6")){
                    Toast.makeText(getApplicationContext(), "비밀번호를 입력하세요", Toast.LENGTH_SHORT).show();
                }
                else if(builder.toString().equals("7")){
                    Toast.makeText(getApplicationContext(), "비밀번호 확인해주세요", Toast.LENGTH_SHORT).show();
                }
                else if(builder.toString().equals("8")){
                    Toast.makeText(getApplicationContext(), "비밀번호가 서로 다릅니다", Toast.LENGTH_SHORT).show();
                }
                else if(builder.toString().equals("9")){
                    Toast.makeText(getApplicationContext(), "ID 중복확인 해주세요", Toast.LENGTH_SHORT).show();
                }
            }
        }

        @Override
        public void onFailure(Call<List<Dummy>> call, Throwable t){

        }
    };

    Callback dummies1 = new Callback<List<Dummy>>() {

        @Override
        public void onResponse(Call<List<Dummy>> call, Response<List<Dummy>> response) {
            if (response.isSuccessful()) {
                List<Dummy> dummies = response.body();
                StringBuilder builder = new StringBuilder();
                for (Dummy dummy : dummies) {
                    builder.append(dummy.toString());
                }
                if(builder.toString().equals("0")){
                    Toast.makeText(getApplicationContext(), "사용할 수 있는 ID입니다", Toast.LENGTH_SHORT).show();
                }

                if(builder.toString().equals("1")){
                    Toast.makeText(getApplicationContext(), "ID 중복입니다", Toast.LENGTH_SHORT).show();
                }
            }
        }

        @Override
        public void onFailure(Call<List<Dummy>> call, Throwable t){

        }
    };
}
