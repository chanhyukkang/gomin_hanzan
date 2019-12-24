package com.example.hotsix.gomin_hanjan;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.hwan.chatting.R;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ChattingRoomReMake extends AppCompatActivity {
    EditText agemin, agemax;
    Button roommake, setting;
    Intent intent, intent2;
    Spinner category, trust;
    RadioGroup sex;
    RadioButton boy, girl, all;
    String userId, userPassword;
    String name, trust1, emotion;
    int backbutton = 0;
    EditText e1, e2, e3, e4;
    String title;
    int show = 0;
    LinearLayout setting_layout;

    String[] userInfo;
    String[] chattinglist;
    String[] readpost;
    String[] readpost1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chattingroom_remake);
        final String BASE = SharedPreference.getAttribute(getApplicationContext(), "IP");
        agemin = (EditText) findViewById(R.id.edit_min_age_re);
        agemax = (EditText) findViewById(R.id.edit_max_age_re);
        category = (Spinner) findViewById(R.id.spinner_category_re);
        roommake = (Button) findViewById(R.id.button_makeroom2_re);
        sex = (RadioGroup) findViewById(R.id.radioGroup_sex_re);
        boy = (RadioButton) findViewById(R.id.checkbox_man_re);
        girl = (RadioButton) findViewById(R.id.checkbox_girl_re);
        all = (RadioButton) findViewById(R.id.checkbox_all_re);
        trust = (Spinner) findViewById(R.id.spinner_trust_re);
        e1 = (EditText) findViewById(R.id.e1_re);
        e2 = (EditText) findViewById(R.id.e2_re);
        setting = findViewById(R.id.button_makeroom3_re);
        setting_layout = findViewById(R.id.setting_layout_re);

        Intent intent1 = getIntent();
        userInfo = intent1.getStringArrayExtra("strings");
        chattinglist = intent1.getStringArrayExtra("chattinglist");
        readpost = intent1.getStringArrayExtra("readpost");

        userId = userInfo[0];
        userPassword = userInfo[1];
        name = userInfo[2];
        trust1 = userInfo[3];
        emotion = userInfo[5];

        ArrayAdapter<String> adapter, trustadapter;

        List<String> list = new ArrayList<String>();
        list.add("# 진로");
        list.add("# 학업");
        list.add("# 이성");
        list.add("# 취업");
        list.add("# 인간관계");
        list.add("# 외모");
        list.add("# 가정");
        list.add("# 경제");
        list.add("# 기타");
        List<String>  trustlist = new ArrayList<String>();
        trustlist.add("초승달 등급");
        trustlist.add("반달 등급");
        trustlist.add("보름달 등급");
        trustlist.add("슈퍼문 등급");
        trustlist.add("달토끼 등급");

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        e1.setText(chattinglist[5]);
        e2.setText(chattinglist[6]);
        agemin.setText(chattinglist[1]);
        agemax.setText(chattinglist[2]);

        if (chattinglist[3] == "0") {
            all.setChecked(true);
        } else if (chattinglist[3] == "1") {
            boy.setChecked(true);
        }

        if (chattinglist[3] == "2") {
            girl.setChecked(true);
        }
//        if(chattinglist[4].equals("0")){
//            trust.
//        }
//        trust.setText(chattinglist[4]);

        adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        category.setAdapter(adapter);
        trustadapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, trustlist);
        trustadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        trust.setAdapter(trustadapter);

        e1.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if ((keyEvent.getAction() == KeyEvent.ACTION_DOWN) && i == KeyEvent.KEYCODE_ENTER) {
                    e2.requestFocus();
                    return true;
                }
                return false;
            }
        });
        agemin.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if ((keyEvent.getAction() == KeyEvent.ACTION_DOWN) && i == KeyEvent.KEYCODE_ENTER) {
                    agemax.requestFocus();
                    return true;
                }
                return false;
            }
        });
        agemax.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if ((keyEvent.getAction() == KeyEvent.ACTION_DOWN) && i == KeyEvent.KEYCODE_ENTER) {
                    trust.requestFocus();
                    return true;
                }
                return false;
            }
        });

        //방 설정 레이아웃 처음에는 숨기기
        setting_layout.setVisibility(View.GONE);

        //설정 누르면 보이기, 안보이기 반복
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (show % 2 == 0) {
                    setting_layout.setVisibility(View.VISIBLE);
                } else {
                    setting_layout.setVisibility(View.GONE);
                }
                show++;
            }
        });

        roommake.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(BASE)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                ChattingUpdateInterface chattingUpdateInterface = retrofit.create(ChattingUpdateInterface.class);
                title = e1.getText().toString();
                String maintext = e2.getText().toString();
                String agemin1 = String.valueOf(agemin.getText().toString());
                String agemax1 = String.valueOf(agemax.getText().toString());
                RadioButton rd = (RadioButton) findViewById(sex.getCheckedRadioButtonId());
                String sex1 = String.valueOf(rd.getText().toString());
                String trust1 = String.valueOf(trust.getSelectedItem().toString());
                String category1 = String.valueOf(category.getSelectedItem().toString());

                if(trust1.equals("초승달 등급"))
                    trust1 = String.valueOf("0");
                else if(trust1.equals("반달 등급"))
                    trust1 = String.valueOf("10");
                else if(trust1.equals("보름달 등급"))
                    trust1 = String.valueOf("30");
                else if(trust1.equals("슈퍼문 등급"))
                    trust1 = String.valueOf("60");
                else if(trust1.equals("달토끼 등급"))
                    trust1 = String.valueOf("100");

                readpost1 = new String[]{title, maintext, readpost[2], readpost[3]};

                Call<List<Dummy>> call = chattingUpdateInterface.listDummies(title, maintext, category1, agemin1, agemax1, sex1, trust1, userInfo[0], emotion, chattinglist[5]);
                call.enqueue(dummies);
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

                Toasty.normal(getApplicationContext(), "수정되었습니다", Toast.LENGTH_SHORT).show();
                intent = new Intent(ChattingRoomReMake.this, ReadPost.class);
                intent.putExtra("strings", userInfo);
                intent.putExtra("readpost", readpost1);
                startActivity(intent);
            }
        }

        @Override
        public void onFailure(Call<List<Dummy>> call, Throwable t) {

        }
    };
}