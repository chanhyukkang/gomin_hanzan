package com.example.hotsix.gomin_hanjan;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.hwan.chatting.R;

public class FindPassword extends AppCompatActivity {

    EditText id;
    Button find, signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_findpassword);

        id = findViewById(R.id.editid);
        find = findViewById(R.id.button_find);
        signup = findViewById(R.id.signup);

        signup.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),SignUp.class);
                startActivityForResult(intent,100);
            }
        });
    }
}
