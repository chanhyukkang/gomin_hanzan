package com.example.hotsix.gomin_hanjan;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.hwan.chatting.R;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;

public class Help extends AppCompatActivity {

    Spinner reason;
    EditText title,contents;
    Button ok,cancel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        ArrayAdapter<String> adapter;
        List<String> list;
        list = new ArrayList<String>();
        title = (EditText)findViewById(R.id.edit_help_title);  //
        contents = (EditText)findViewById(R.id.edit_help_contents);
        ok = (Button)findViewById(R.id.button_ok);
        cancel= (Button)findViewById(R.id.button_back);
        Intent intent2 = getIntent();
        final String userInfo[] = intent2.getStringArrayExtra("strings");
        final String usertitle[] = intent2.getStringArrayExtra("usertitle");
        list.add("로그인 문제");
        list.add("서비스 이용관련 문제");
        list.add("버그 리포트");
        list.add("기타");
        adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        reason = (Spinner)findViewById(R.id.spinner_sayou);
        reason.setAdapter(adapter);

        String category1 = String.valueOf(reason.getSelectedItem().toString());//문의 이유 받아오는 변수
        ok.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                Toasty.success(getApplicationContext(), "문의가 성공적으로 접수되었습니다", Toast.LENGTH_SHORT).show();
                intent.putExtra("strings",userInfo);
                intent.putExtra("usertitle",usertitle);
                startActivityForResult(intent,100);
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                Toasty.error(getApplicationContext(), "문의를 실패하였습니다", Toast.LENGTH_SHORT).show();
                intent.putExtra("strings",userInfo);
                intent.putExtra("usertitle",usertitle);
                startActivityForResult(intent,100);
            }
        });
    }
}
