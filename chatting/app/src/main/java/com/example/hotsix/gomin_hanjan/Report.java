package com.example.hotsix.gomin_hanjan;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hwan.chatting.R;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;

public class Report extends AppCompatActivity {

    Spinner reason;
    EditText title,contents;
    TextView username;
    Button ok,cancel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        ArrayAdapter<String> adapter;
        List<String> list;
        list = new ArrayList<String>();
        username = (TextView)findViewById(R.id.reportsangdae);
        contents = (EditText)findViewById(R.id.edit_report_contents);
        ok = (Button)findViewById(R.id.button_report);
        cancel= (Button)findViewById(R.id.button_back);
        Intent intent2 = getIntent();
        final String userInfo[] = intent2.getStringArrayExtra("strings");
        final String userkey[] = intent2.getStringArrayExtra("strings1");
        list.add("불법 광고 ");
        list.add("욕설/인신공격");
        list.add("도배성글");
        list.add("개인정보노출/사생활침해");
        list.add("음란성/선정성");
        list.add("영리목적");
        adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        reason = (Spinner)findViewById(R.id.spinner_reportsayou);
        reason.setAdapter(adapter);

        username.setText("익명 "+userkey[0]);

        String category1 = String.valueOf(reason.getSelectedItem().toString());//문의 이유 받아오는 변수
        ok.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toasty.success(getApplicationContext(), "성공적으로 접수되었습니다", Toast.LENGTH_SHORT).show();
                onBackPressed();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toasty.error(getApplicationContext(), "문의를 실패하였습니다", Toast.LENGTH_SHORT).show();
                onBackPressed();
            }
        });
    }
}