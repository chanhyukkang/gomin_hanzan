package com.example.hotsix.gomin_hanjan;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.hwan.chatting.R;

public class UseDocs extends AppCompatActivity {

    Button ok;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usedocs);
        intent = getIntent();
        final String userInfo[] = intent.getStringArrayExtra("strings");
        final String usertitle[] = intent.getStringArrayExtra("usertitle");
        ok = findViewById(R.id.bt_main);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(getApplicationContext(),MainActivity.class);
                intent2.putExtra("strings",userInfo);
                intent2.putExtra("usertitle",usertitle);
                startActivityForResult(intent2,100);
            }
        });
    }
}