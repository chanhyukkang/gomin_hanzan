package com.example.hotsix.gomin_hanjan;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.example.hwan.chatting.R;

import es.dmoral.toasty.Toasty;

public class SignUp_Promise extends AppCompatActivity {

    Button ok;
    CheckBox a,b,c,d,e,f,g,all;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_promise);
        ok = findViewById(R.id.ok);
        a = findViewById(R.id.a); b = findViewById(R.id.b); c = findViewById(R.id.c); d = findViewById(R.id.d);
        e = findViewById(R.id.e); f = findViewById(R.id.f); g = findViewById(R.id.g); all = findViewById(R.id.all);
        all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(all.isChecked()){
                    a.setChecked(true);
                    b.setChecked(true);
                    c.setChecked(true);
                    d.setChecked(true);
                    e.setChecked(true);
                    f.setChecked(true);
                    g.setChecked(true);
                }else{
                    a.setChecked(false);
                    b.setChecked(false);
                    c.setChecked(false);
                    d.setChecked(false);
                    e.setChecked(false);
                    f.setChecked(false);
                    g.setChecked(false);
                }
            }
        });
        a.setOnClickListener(onCheckBoxClickListener);
        b.setOnClickListener(onCheckBoxClickListener);
        c.setOnClickListener(onCheckBoxClickListener);
        d.setOnClickListener(onCheckBoxClickListener);
        e.setOnClickListener(onCheckBoxClickListener);
        f.setOnClickListener(onCheckBoxClickListener);
        g.setOnClickListener(onCheckBoxClickListener);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isAllChecked()) {
                    onBackPressed();
                }
                else{
                    Toasty.error(SignUp_Promise.this, "모두 동의해주세요", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }//onCreate

    private View.OnClickListener onCheckBoxClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            if(isAllChecked())
                all.setChecked(true);
            else
                all.setChecked(false);
        }
    };

    private boolean isAllChecked(){
        return (a.isChecked() && b.isChecked() && c.isChecked()&&d.isChecked() && e.isChecked() && f.isChecked()&&g.isChecked()) ?  true :  false;
    }

}
