package com.example.hotsix.gomin_hanjan;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.example.hwan.chatting.R;

public class Promise extends AppCompatActivity {

    Button ok;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promise);
//        ok = findViewById(R.id.btok);

        Intent intent2 = getIntent();
        final String userInfo[] = intent2.getStringArrayExtra("strings");
        final String usertitle[] = intent2.getStringArrayExtra("usertitle");

//        ok.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
//                intent.putExtra("strings",userInfo);
//                intent.putExtra("usertitle",usertitle);
//                startActivityForResult(intent,100);
//            }
//        });
    }
}