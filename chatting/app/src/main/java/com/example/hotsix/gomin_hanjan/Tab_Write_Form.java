package com.example.hotsix.gomin_hanjan;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.hwan.chatting.R;

/**
 * Created by user on 2017-11-07.
 */

public class Tab_Write_Form extends Activity{

    Button b1, b2;
    EditText e1, e2;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab1_write_form);

        b1 =  (Button)findViewById(R.id.b1);
        b2 =  (Button)findViewById(R.id.b2);
        e1 =  (EditText)findViewById(R.id.e1);
        e2 =  (EditText)findViewById(R.id.e2);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Tab_Write_Form.this, "그런 일이 있었군요", Toast.LENGTH_SHORT).show();
                intent = new Intent(Tab_Write_Form.this, ChattingRoomMake.class);
                startActivity(intent);
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}


