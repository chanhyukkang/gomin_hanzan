package com.example.hotsix.gomin_hanjan;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hwan.chatting.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ChattingActivity extends AppCompatActivity {

    int userKey = 0;
    EditText sendChatText;
    Button sendButton, mainButton, startButton, stopButton, favoriteButton, background;
    private Socket socket;
    String title;
    int mainbutton1 = 0;
    String userId, userPassword;
    ChattingAdapter m_Adapter;
    int num;
    TextView chattingroomname;
    String msg;
    String name, trust, emotion;
    Intent intent9;
    MediaPlayer mediaPlayer;
    String BASE;
    RelativeLayout layout1;
    int backnum;
    StringBuilder builder_like = new StringBuilder();
    StringBuilder builder_title = new StringBuilder();
    StringBuilder builder_category = new StringBuilder();
    StringBuilder builder_profile = new StringBuilder();
    String updatetrust;
    String[] userkey;
    ScrollView scrollview_chatting;
    int backbutton = 0;
    TextView roomnum;
    int num1 = 0;
    int tmp;
    int background1 = 0;
    int favorite = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        BASE = SharedPreference.getAttribute(getApplicationContext(), "IP");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatting);

        m_Adapter = new ChattingAdapter();
        background = (Button) findViewById(R.id.button_background);

        // listview 생성 및 adapter 지정.
        final ListView listview = (ListView) findViewById(R.id.chatting);
        listview.setAdapter(m_Adapter);
        listview.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
        layout1 = (RelativeLayout) findViewById(R.id.chattingview);
        sendChatText = (EditText) findViewById(R.id.chat_content);
        sendButton = (Button) findViewById(R.id.send_btn);
        mainButton = (Button) findViewById(R.id.mainbutton);
        chattingroomname = (TextView) findViewById(R.id.chattingroomname);
        favoriteButton = (Button) findViewById(R.id.favorite);
        scrollview_chatting = (ScrollView) findViewById(R.id.scrollview_chatting);
        roomnum = (TextView) findViewById(R.id.roomnum);

        Intent intent1 = getIntent();
        final String[] userInfo = intent1.getStringArrayExtra("strings");

        userId = userInfo[0];
        userPassword = userInfo[1];
        userKey = Integer.parseInt(userInfo[2]);
        name = userInfo[3];
        trust = userInfo[4];
        emotion = userInfo[5];
        title = userInfo[6];

        //getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        FavoriteListInterface favoriteListInterface = retrofit.create(FavoriteListInterface.class);
        Call<List<Dummy>> call = favoriteListInterface.listDummies(userInfo[0]);
        call.enqueue(dummies8);

        try {
            socket = IO.socket("http://15.165.101.224:3000"); //로컬호스트 ip주소 수정하기
        } catch (Exception e) {
            Log.i("THREADSERVICE", "Server not connected");
            e.printStackTrace();
        }

        socket.connect();

        Retrofit retrofit6 = new Retrofit.Builder()
                .baseUrl(BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ChattingNumInterface chattingNumInterface = retrofit6.create(ChattingNumInterface.class);
        Call<List<Dummy>> call6 = chattingNumInterface.listDummies(title);
        call6.enqueue(dummies6);

        sendButton.setEnabled(false);
        sendButton.setAlpha((float) 0.5);

        sendChatText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                sendButton.setEnabled(true);
                sendButton.setAlpha((float) 1);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        sendChatText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if ((keyEvent.getAction() == KeyEvent.ACTION_DOWN) && i == KeyEvent.KEYCODE_ENTER) {
                    sendButton.performClick();
                    return true;
                }
                return false;
            }
        });

        background.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), BackgroundSelect.class);
                intent.putExtra("strings", userInfo);
                startActivityForResult(intent, 100);
            }
        });

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JSONObject obj = new JSONObject();
                String message = sendChatText.getText().toString(); //전송할 메시지
                try {
                    obj.put("roomname", title);
                    obj.put("message", message);
                    obj.put("key", userKey);
                    obj.put("profile", emotion);
                    obj.put("roomnum", num1);
                    socket.emit("message", obj);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                sendChatText.setText("");
                sendButton.setEnabled(false);
                sendButton.setAlpha((float) 0.5);
            }
        });

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                String message = m_Adapter.getMessage(position);
                if (message.length() == 0) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(ChattingActivity.this);

                    builder.setTitle("이 사용자의 신뢰도를");

                    builder.setItems(R.array.LAN, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int pos) {
                            String[] items = getResources().getStringArray(R.array.LAN);
                            Retrofit retrofit = new Retrofit.Builder()
                                    .baseUrl(BASE)
                                    .addConverterFactory(GsonConverterFactory.create())
                                    .build();

                            TrustInterface trustInterface = retrofit.create(TrustInterface.class);
                            Call<List<Dummy>> call = trustInterface.listDummies(m_Adapter.getUserkey(position), String.valueOf(userKey), items[pos]);
                            call.enqueue(dummies);
                        }
                    });

                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                } else if (message.charAt(0) == '#') {
                    AlertDialog diaBox = new AlertDialog.Builder(ChattingActivity.this)
                            .setTitle("추천해준 음악링크로 이동하기")
                            .setMessage("음악 링크로 이동합니다")
                            .setIcon(R.drawable.heart1)
                            .setPositiveButton("네",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int whichButton) {
                                            String url = "https://www.youtube.com/results?search_query=" + m_Adapter.getMessage(position).substring(1);
                                            Intent i = new Intent(Intent.ACTION_VIEW);
                                            i.setData(Uri.parse(url));
                                            startActivity(i);
                                        }
                                    })
                            .setNegativeButton("아니요", null).create();
                    diaBox.show();

                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(ChattingActivity.this);

                    builder.setTitle("사용자");

                    builder.setItems(R.array.LAN, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int pos)
                        {
                            String[] items = getResources().getStringArray(R.array.LAN);
                            if(pos==2)
                            {
                                userkey = new String[]{m_Adapter.getUserkey(position)};
                                Intent intent = new Intent(getApplicationContext(), Profile.class);
                                intent.putExtra("strings", userInfo);
                                intent.putExtra("strings1", userkey);
                                startActivity(intent);
                            }
                            else if(pos ==3)
                            {
                                userkey = new String[]{m_Adapter.getUserkey(position)};
                                Intent intent = new Intent(getApplicationContext(), Report.class);
                                intent.putExtra("strings1", userkey);
                                startActivity(intent);
                            }
                            else {
                                Retrofit retrofit = new Retrofit.Builder()
                                        .baseUrl(BASE)
                                        .addConverterFactory(GsonConverterFactory.create())
                                        .build();

                                TrustInterface trustInterface = retrofit.create(TrustInterface.class);
                                Call<List<Dummy>> call = trustInterface.listDummies(m_Adapter.getUserkey(position), String.valueOf(userKey), items[pos]);
                                call.enqueue(dummies);
                            }
                        }
                    });

                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
            }
        });

        listview.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                scrollview_chatting.requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });

        mainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JSONObject obj2 = new JSONObject();
                try {
                    obj2.put("roomname", title);
                    obj2.put("roomnum", tmp);
                    socket.emit("leave", obj2);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                socket.emit("disconnect", "");
                socket.disconnect();
                mainbutton1 = 1;
                Retrofit retrofit1 = new Retrofit.Builder()
                        .baseUrl(BASE)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                ChattingInformationInterface chattingInformationInterface = retrofit1.create(ChattingInformationInterface.class);
                Call<List<Dummy3>> call1 = chattingInformationInterface.listDummies(userId);
                call1.enqueue(dummies1);
            }
        });

        backnum = intent1.getIntExtra("backgroundnumber", 0);
        switch (backnum) {
            case 0:
                layout1.setBackgroundResource(R.drawable.skyblue1);
                break;
            case 1:
                layout1.setBackgroundResource(R.drawable.skyblue2);
                break;
            case 2:
                layout1.setBackgroundResource(R.drawable.snow);
                break;
            case 3:
                layout1.setBackgroundResource(R.drawable.soju);
                break;
            case 4:
                layout1.setBackgroundResource(R.drawable.merrychristmas);
                break;
            case 5:
                layout1.setBackgroundResource(R.drawable.cheese);
                break;
            case 6:
                layout1.setBackgroundResource(R.drawable.chicken);
            default:
                break;
        }

        favoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(favorite==0){
                    favorite=1;
                }
                else if(favorite==1){
                    favorite=0;
                }

                if(favorite==1) {
                    Retrofit retrofit3 = new Retrofit.Builder()
                            .baseUrl(BASE)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

                    FavoriteInterface favoriteInterface = retrofit3.create(FavoriteInterface.class);
                    Call<List<Dummy>> call3 = favoriteInterface.listDummies(userId, title);
                    call3.enqueue(dummies3);
                    favoriteButton.setBackgroundResource(R.drawable.star);
                }

                else{
                    Retrofit retrofit7 = new Retrofit.Builder()
                            .baseUrl(BASE)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

                    FavoriteEraseInterface favoriteEraseInterface = retrofit7.create(FavoriteEraseInterface.class);
                    Call<List<Dummy>> call7 = favoriteEraseInterface.listDummies(title);
                    call7.enqueue(dummies7);
                    favoriteButton.setBackgroundResource(R.drawable.favoritetoomyung);
                }
            }
        });

        Emitter.Listener onMessageReceived = new Emitter.Listener() {
            @Override
            public void call(final Object... args) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        JSONObject received = (JSONObject) args[0];
                        String msg1 = null;
                        String key = null;
                        String profile = null;
                        try {
                            msg1 = received.get("message").toString(); //받는 메시지
                            key = received.get("key").toString(); //유저 식별키
                            profile = received.get("profile").toString();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        if (key.equals(Integer.toString(userKey))) {
                            num = 0;
                            if (msg1.length() > 24) {
                                for (int i = 0; i < (msg1.length() / 24); i++) {
                                    if (i == 0) {
                                        msg = msg1.substring((i * 25), 24 * (i + 1)) + "\n";
                                    } else {
                                        msg = msg + msg1.substring((i * 25), 24 * (i + 1) + 1) + "\n";
                                    }
                                }
                                msg = msg + msg1.substring(msg1.length() - (msg1.length() % 24), msg1.length()) + "\n";
                            } else {
                                msg = msg1;
                            }

                            m_Adapter.add(Integer.parseInt(profile), msg, num, String.valueOf(key));
                            m_Adapter.notifyDataSetChanged();
                        } else {
                            num = 1;
                            if (msg1.length() > 24) {
                                for (int i = 0; i < (msg1.length() / 24); i++) {
                                    if (i == 0) {
                                        msg = msg1.substring((i * 25), 25 * (i + 1) - 1) + "\n";
                                    } else {
                                        msg = msg + msg1.substring((i * 25), 25 * (i + 1) - 1) + "\n";
                                    }
                                }
                                msg = msg + msg1.substring(msg1.length() - (msg1.length() % 24), msg1.length()) + "\n";
                            } else {
                                msg = msg1;
                            }
                            m_Adapter.add(Integer.parseInt(profile), msg, num, String.valueOf(key));
                            m_Adapter.notifyDataSetChanged();
                        }
                    }
                });
            }
        };
        socket.on("receiveMsg", onMessageReceived);

        Emitter.Listener roomleave = new Emitter.Listener() {
            @Override
            public void call(final Object... args) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        JSONObject received = (JSONObject) args[0];
                        int exit;
                        try {
                            exit = (int) received.get("roomnum"); //받는 메시지
                            roomnum.setText(Integer.toString(exit - 1));
                            tmp = exit - 1;
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        };
        socket.on("exit", roomleave);

        intent9 = getIntent();
        int num = intent9.getIntExtra("bgm", 10);
        switch (num) {
            case 0:
                mediaPlayer = MediaPlayer.create(ChattingActivity.this, R.raw.showme119);
                mediaPlayer.start();
                break;
            case 1:
                mediaPlayer = MediaPlayer.create(ChattingActivity.this, R.raw.tomyyouth);
                mediaPlayer.start();
                break;
        }

        listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                AlertDialog diaBox = new AlertDialog.Builder(ChattingActivity.this)
                        .setTitle("삭제")
                        .setMessage("정말 삭제하십니까?")
                        .setIcon(R.drawable.heart1)
                        .setPositiveButton("네",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton) {
                                        m_Adapter.remove(position);
                                        listview.clearChoices();
                                        m_Adapter.notifyDataSetChanged();
                                    }
                                })
                        .setNegativeButton("아니요", null).create();
                diaBox.show();
                return true;
            }
        });
    }

    public void onBackPressed() {
        JSONObject obj2 = new JSONObject();
        try {
            obj2.put("roomname", title);
            obj2.put("roomnum", tmp);
            socket.emit("leave", obj2);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        socket.emit("disconnect", "");
        socket.disconnect();
        backbutton = 1;
        Retrofit retrofit1 = new Retrofit.Builder()
                .baseUrl(BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ChattingInformationInterface chattingInformationInterface = retrofit1.create(ChattingInformationInterface.class);
        Call<List<Dummy3>> call1 = chattingInformationInterface.listDummies(userId);
        call1.enqueue(dummies1);
    }

    @Override
    public void onRestart(){
        super.onRestart();
        Retrofit retrofit6 = new Retrofit.Builder()
                .baseUrl(BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ChattingNumInterface chattingNumInterface = retrofit6.create(ChattingNumInterface.class);
        Call<List<Dummy>> call6 = chattingNumInterface.listDummies(title);
        call6.enqueue(dummies6);
    }

    @Override
    public void onStop(){
        super.onStop();
        JSONObject obj2 = new JSONObject();
        try {
            obj2.put("roomname", title);
            obj2.put("roomnum", tmp);
            socket.emit("leave", obj2);
        } catch (JSONException e) {
            e.printStackTrace();
        }
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

                if (builder.toString().equals("1")) {
                    Toast.makeText(getApplicationContext(), "신뢰도를 올렸습니다", Toast.LENGTH_SHORT).show();
                } else if (builder.toString().equals("2")) {
                    Toast.makeText(getApplicationContext(), "신뢰도를 내렸습니다", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "자신의 신뢰도를 올리거나 내릴 수 없습니다", Toast.LENGTH_SHORT).show();
                }
            }
        }

        @Override
        public void onFailure(Call<List<Dummy>> call, Throwable t) {

        }
    };

    Callback dummies1 = new Callback<List<Dummy3>>() {

        @Override
        public void onResponse(Call<List<Dummy3>> call1, Response<List<Dummy3>> response) {
            if (response.isSuccessful()) {
                List<Dummy3> dummies = response.body();
                String[] build;
                builder_like = new StringBuilder();
                builder_title = new StringBuilder();
                builder_category = new StringBuilder();
                builder_profile = new StringBuilder();
                for (Dummy3 dummy : dummies) {
                    build = dummy.toString().split(",");
                    builder_like.append(build[0] + ",");
                    builder_title.append(build[1] + ",");
                    builder_category.append(build[2] + ",");
                    builder_profile.append(build[3] + ",");
                }

                if (mainbutton1 == 1) {
                    Retrofit retrofit1 = new Retrofit.Builder()
                            .baseUrl(BASE)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

                    GetTrustInterface getTrustInterface = retrofit1.create(GetTrustInterface.class);
                    Call<List<Dummy>> call3 = getTrustInterface.listDummies(userId);
                    call3.enqueue(dummies5);
                }

                if (backbutton == 1) {
                    Retrofit retrofit1 = new Retrofit.Builder()
                            .baseUrl(BASE)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

                    GetTrustInterface getTrustInterface = retrofit1.create(GetTrustInterface.class);
                    Call<List<Dummy>> call3 = getTrustInterface.listDummies(userId);
                    call3.enqueue(dummies5);
                }
            }
        }

        @Override
        public void onFailure(Call<List<Dummy3>> call1, Throwable t) {

        }
    };

    Callback dummies5 = new Callback<List<Dummy>>() {

        @Override
        public void onResponse(Call<List<Dummy>> call3, Response<List<Dummy>> response) {
            if (response.isSuccessful()) {
                List<Dummy> dummies = response.body();
                StringBuilder builder = new StringBuilder();
                for (Dummy dummy : dummies) {
                    builder.append(dummy.toString());
                }
                updatetrust = builder.toString();

                Retrofit retrofit2 = new Retrofit.Builder()
                        .baseUrl(BASE)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                UserInterface userInterface = retrofit2.create(UserInterface.class);
                Call<List<Dummy3>> call2 = userInterface.listDummies(userId);
                call2.enqueue(dummies2);
            }
        }

        @Override
        public void onFailure(Call<List<Dummy>> call3, Throwable t) {

        }
    };

    Callback dummies2 = new Callback<List<Dummy3>>() {

        @Override
        public void onResponse(Call<List<Dummy3>> call2, Response<List<Dummy3>> response) {
            if (response.isSuccessful()) {
                List<Dummy3> dummies = response.body();
                String[] build1;
                StringBuilder builder_title1 = new StringBuilder();
                StringBuilder builder_category1 = new StringBuilder();
                StringBuilder builder_profile1 = new StringBuilder();
                StringBuilder builder_like1 = new StringBuilder();
                for (Dummy3 dummy : dummies) {
                    build1 = dummy.toString().split(",");
                    builder_title1.append(build1[0] + ",");
                    builder_category1.append(build1[1] + ",");
                    builder_profile1.append(build1[2] + ",");
                    builder_like1.append(build1[3] + ",");
                }
                String[] usertitle = new String[]{String.valueOf(builder_title1), String.valueOf(builder_category1), String.valueOf(builder_profile1), String.valueOf(builder_like1)};

                Intent intent2 = new Intent(ChattingActivity.this, MainActivity.class);
                String[] information1 = new String[]{userId, userPassword, String.valueOf(builder_title), name, updatetrust, emotion, String.valueOf(builder_like), String.valueOf(builder_category), String.valueOf(builder_profile)};
                intent2.putExtra("strings", information1);
                intent2.putExtra("usertitle", usertitle);
                intent2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent2);
            }
        }

        @Override
        public void onFailure(Call<List<Dummy3>> call1, Throwable t) {

        }
    };

    Callback dummies3 = new Callback<List<Dummy>>() {

        @Override
        public void onResponse(Call<List<Dummy>> call3, Response<List<Dummy>> response) {
            if (response.isSuccessful()) {
                List<Dummy> dummies = response.body();
                StringBuilder builder = new StringBuilder();
                for (Dummy dummy : dummies) {
                    builder.append(dummy.toString());
                }
                if (builder.toString().equals("1")) {
                    Toast.makeText(getApplicationContext(), "이미 추가되었습니다", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "즐겨찾기에 추가되었습니다", Toast.LENGTH_SHORT).show();
                }
            }
        }

        @Override
        public void onFailure(Call<List<Dummy>> call3, Throwable t) {

        }
    };

    Callback dummies6 = new Callback<List<Dummy>>() {

        @Override
        public void onResponse(Call<List<Dummy>> call6, Response<List<Dummy>> response) {
            if (response.isSuccessful()) {
                List<Dummy> dummies = response.body();
                StringBuilder builder = new StringBuilder();

                for (Dummy dummy : dummies) {
                    num1 = Integer.parseInt(dummy.toString())+1;
                }
                chattingroomname.setText(title+"   ");

                JSONObject obj1 = new JSONObject();
                try {
                    obj1.put("roomname", title);
                    obj1.put("roomnum", num1);
                    socket.emit("join", obj1);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                //roomnum.setText(Integer.toString(num1));
                Emitter.Listener roomenter = new Emitter.Listener() {
                    @Override
                    public void call(final Object... args) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                JSONObject received = (JSONObject) args[0];
                                String enter = null;
                                try {
                                    enter = received.get("roomnum").toString(); //받는 메시지
                                    if(num1<Integer.parseInt(enter)){
                                        roomnum.setText(enter.toString());
                                        tmp = Integer.parseInt(enter);
                                    }
                                    else{
                                        roomnum.setText(Integer.toString(num1));
                                        tmp = num1;
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                };
                socket.on("enter", roomenter);
            }
        }

        @Override
        public void onFailure(Call<List<Dummy>> call6, Throwable t) {

        }
    };

    Callback dummies7 = new Callback<List<Dummy>>() {

        @Override
        public void onResponse(Call<List<Dummy>> call7, Response<List<Dummy>> response) {
            if (response.isSuccessful()) {
                List<Dummy> dummies = response.body();
                StringBuilder builder = new StringBuilder();
                for (Dummy dummy : dummies) {
                    builder.append(dummy.toString()+",");
                }
            }
            Toast.makeText(getApplicationContext(), "즐겨찾기에서 삭제하였습니다", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onFailure(Call<List<Dummy>> call7, Throwable t) {

        }
    };

    Callback dummies8 = new Callback<List<Dummy>>() {

        @Override
        public void onResponse(Call<List<Dummy>> call, Response<List<Dummy>> response) {
            if (response.isSuccessful()) {
                List<Dummy> dummies = response.body();
                StringBuilder builder = new StringBuilder();
                String[] build;
                for (Dummy dummy : dummies) {
                    builder.append(dummy.toString()+",");
                }
                build = builder.toString().split(",");
                for(int i=0;i<build.length;i++){
                    if(build[i].equals(title)){
                        favoriteButton.setBackgroundResource(R.drawable.star);
                        favorite = 1;
                    }
                }

            }
        }

        @Override
        public void onFailure(Call<List<Dummy>> call, Throwable t) {

        }
    };
}