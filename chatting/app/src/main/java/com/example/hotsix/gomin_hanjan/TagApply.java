package com.example.hotsix.gomin_hanjan;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.hwan.chatting.R;

public class TagApply extends AppCompatActivity {

    EditText title,contents;
    Button ok,cancel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tagapply);
        title = (EditText)findViewById(R.id.edit_help_title);  //
        contents = (EditText)findViewById(R.id.edit_help_contents);
        ok = (Button)findViewById(R.id.button_okok);
        cancel= (Button)findViewById(R.id.button_backback);
        Intent intent2 = getIntent();
        final String userInfo[] = intent2.getStringArrayExtra("strings");
        final String usertitle[] = intent2.getStringArrayExtra("usertitle");

        ok.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                Toast.makeText(getApplicationContext(), "문의가 성공적으로 접수되었습니다", Toast.LENGTH_SHORT).show();
                intent.putExtra("strings",userInfo);
                intent.putExtra("usertitle",usertitle);
                startActivityForResult(intent,100);
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                Toast.makeText(getApplicationContext(), "문의를 실패하였습니다", Toast.LENGTH_SHORT).show();
                intent.putExtra("strings",userInfo);
                intent.putExtra("usertitle",usertitle);
                startActivityForResult(intent,100);
            }
        });
    }
}
