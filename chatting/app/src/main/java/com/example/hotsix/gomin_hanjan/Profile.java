package com.example.hotsix.gomin_hanjan;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hwan.chatting.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Profile extends AppCompatActivity {
    ImageView profile;
    TextView username, believe;
    Button back;
    String[] userInfo;
    String[] userkey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final String BASE = SharedPreference.getAttribute(getApplicationContext(), "IP");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        profile = findViewById(R.id.image_profile);
        username = findViewById(R.id.text_user);
        believe = findViewById(R.id.text_believe);
        back = findViewById(R.id.button_bback);

        Intent intent1 = getIntent();
        userInfo = intent1.getStringArrayExtra("strings");
        userkey = intent1.getStringArrayExtra("strings1");

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ProfileInterface profileInterface = retrofit.create(ProfileInterface.class);
        Call<List<Dummy1>> call = profileInterface.listDummies(userkey[0]);
        call.enqueue(dummies);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    Callback dummies = new Callback<List<Dummy1>>() {

        @Override
        public void onResponse(Call<List<Dummy1>> call, Response<List<Dummy1>> response) {
            if (response.isSuccessful()) {
                List<Dummy1> dummies = response.body();
                String[] build;
                for (Dummy1 dummy : dummies) {
                    build = dummy.toString().split(",");
                    switch(Integer.parseInt(build[0])) {
                        case 0:
                            profile.setImageResource(R.drawable.happy);
                            break;
                        case 1:
                            profile.setImageResource(R.drawable.surprised);
                            break;
                        case 2:
                            profile.setImageResource(R.drawable.angry);
                            break;
                        case 3:
                            profile.setImageResource(R.drawable.fear);
                            break;
                        case 4:
                            profile.setImageResource(R.drawable.disgust);
                            break;
                        case 5:
                            profile.setImageResource(R.drawable.sad);
                            break;
                        default: break;
                    }
                    username.setText("익명 "+build[1]);
                    believe.setText(build[2]);
                }
            }
        }

        @Override
        public void onFailure(Call<List<Dummy1>> call, Throwable t) {

        }
    };
}