package com.example.hotsix.gomin_hanjan;


/**
 * Created by user on 2016-12-26.
 */

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hwan.chatting.R;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TabFragment2 extends Fragment implements SwipeRefreshLayout.OnRefreshListener{

    Button write,search;
    Intent intent;
    String userId, userPassword,  name, title, trust, emotion;
    ArrayList<String> list;
    ArrayAdapter adapter;
    String[] titleArray;
    String[] likeArray;
    String[] categoryArray;
    String[] profileArray;
    String[] tempArray;
    Intent intent1, intent2;
    private Context context;
    SwipeRefreshLayout mSwipeRefreshLayout;
    String[] build_id, build_category, build_profile;
    ChattingRoomAdapter m_Adapter = new ChattingRoomAdapter();
    String BASE;
    String selecttitle;
    String maintext;
    String[] userInfo;
    String[] usertitle;
    String like;
    EditText editSearch;
    int[] indexArray;
    Switch sortingswitch;
    TextView text_sorted;
    int s = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    public void search(String charText) {

        // 문자 입력시마다 리스트를 지우고 새로 뿌려준다.
        m_Adapter.clear();

        // 문자 입력이 없을때는 모든 데이터를 보여준다.
        if (charText.length() == 0) {
            for(int i=titleArray.length-1;i>=0;i--){
                m_Adapter.add(Integer.parseInt(profileArray[i]),titleArray[i], categoryArray[i], likeArray[i]);
            }
        }
        // 문자 입력을 할때..
        else
        {
            // 리스트의 모든 데이터를 검색한다.
            for(int i = titleArray.length-1;i >=0; i--)
            {
                // arraylist의 모든 데이터에 입력받은 단어(charText)가 포함되어 있으면 true를 반환한다.
                if (titleArray[i].contains(charText))
                {
                    // 검색된 데이터를 리스트에 추가한다.
                    m_Adapter.add(Integer.parseInt(profileArray[i]),titleArray[i], categoryArray[i], likeArray[i]);
                }
            }
        }
        // 리스트 데이터가 변경되었으므로 아답터를 갱신하여 검색된 데이터를 화면에 보여준다.
        m_Adapter.notifyDataSetChanged();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_fragment_2, container, false);
        ListView listview;
        context = container.getContext();
        editSearch = view.findViewById(R.id.editSearch2);
        search = view.findViewById(R.id.search2);
        write = (Button) view.findViewById(R.id.write);
        mSwipeRefreshLayout = view.findViewById(R.id.refresh);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.blue);
//        text_sorted=view.findViewById(R.id.text_sorted2);
        sortingswitch = view.findViewById(R.id.sorting2);
        intent1 = getActivity().getIntent();
        userInfo = intent1.getStringArrayExtra("strings");
        usertitle = intent1.getStringArrayExtra("usertitle");
        userId = userInfo[0];
        userPassword = userInfo[1];
        name = userInfo[3];
        trust = userInfo[4];
        emotion = userInfo[5];
        list = new ArrayList<>();
        adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, list);
        listview = (ListView)view.findViewById(R.id.listview2);
        listview.setAdapter(m_Adapter);

        titleArray = usertitle[0].split(",");
        categoryArray = usertitle[1].split(",");
        profileArray = usertitle[2].split(",");
        likeArray = usertitle[3].split(",");

        //likeArray를 tempArray에 복사해서 공감순으로 정렬하고 인덱스 순서 얻기
        tempArray=usertitle[3].split(",");
        indexArray= new int[tempArray.length];
        String tmpstr;
        int tmpint;
        for(int i = 0; i < tempArray.length; i++){
            indexArray[i]=i;
        }
        for(int i = 0; i < tempArray.length-1; i++){
            for(int h= 1 ; h < tempArray.length-i; h++) {
                if (Integer.parseInt(tempArray[i]) < Integer.parseInt(tempArray[i + h])) {
                    tmpstr = tempArray[i];
                    tempArray[i] = tempArray[i + h];
                    tempArray[i + h] = tmpstr;

                    tmpint = indexArray[i];
                    indexArray[i] = indexArray[i + h];
                    indexArray[i + h] = tmpint;
                }
            }
        }
        //처음에는 최신순
        if(usertitle[0].equals("")) {
        }
        else {
            for (int i = titleArray.length - 1; i >= 0; i--) {
                m_Adapter.add(Integer.parseInt(profileArray[i]), titleArray[i], categoryArray[i], likeArray[i]);
            }
        }
        sortingswitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) { //공감순 일 때
//                    text_sorted.setText("공감순ON");
                    if(m_Adapter.getCount()==0)
                    {

                    }
                    else{
                        m_Adapter.clear();
                        for (int i = 0; i <= likeArray.length - 1; i++)
                            m_Adapter.add(Integer.parseInt(profileArray[indexArray[i]]), titleArray[indexArray[i]], categoryArray[indexArray[i]], likeArray[indexArray[i]]);
                        m_Adapter.notifyDataSetChanged();
                    }


                }
                else{ //공감순 아닐 때
//                    text_sorted.setText("공감순OFF");
                    if(m_Adapter.getCount()==0)
                    {
                    }
                    else {
                        m_Adapter.clear();

                        for (int i = likeArray.length - 1; i >= 0; i--)
                            m_Adapter.add(Integer.parseInt(profileArray[i]), titleArray[i], categoryArray[i], likeArray[i]);

                        m_Adapter.notifyDataSetChanged();
                    }
                }
            }
        });

        write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toasty.normal(context, "다 얘기해봐요", Toast.LENGTH_SHORT).show();
                intent = new Intent(context, ChattingRoomMake.class);
                String[] information = new String[]{userInfo[0], userInfo[1], name, trust, emotion};
                intent.putExtra("strings", information);
                startActivity(intent);
            }
        });

        editSearch.setVisibility(View.GONE);

        //설정 누르면 보이기, 안보이기 반복
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(s%2 == 0) {
                    editSearch.setVisibility(View.VISIBLE);
                }
                else {
                    editSearch.setVisibility(View.GONE);
                }
                s++;
            }
        });

        editSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // input창에 문자를 입력할때마다 호출된다.
                // search 메소드를 호출한다.
                String text = editSearch.getText().toString();
                search(text);
            }
        });


        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final String BASE = SharedPreference.getAttribute(context.getApplicationContext(), "IP");
                selecttitle = m_Adapter.getPosition(position);

                Retrofit retrofit2 = new Retrofit.Builder()
                        .baseUrl(BASE)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                ReadPostInterface readPostInterface = retrofit2.create(ReadPostInterface.class);
                Call<List<Dummy2>> call2 = readPostInterface.listDummies(selecttitle);
                call2.enqueue(dummies2);
            }

        });

        return view;
    } // onCreateView

    //새로고침
    @Override
    public void onRefresh(){
        mSwipeRefreshLayout.setRefreshing(true);
        BASE = SharedPreference.getAttribute(context.getApplicationContext(), "IP");

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() { // 여기에 코드 추가
                Retrofit retrofit1 = new Retrofit.Builder()
                        .baseUrl(BASE)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                UserInterface userInterface = retrofit1.create(UserInterface.class);
                Call<List<Dummy3>> call1 = userInterface.listDummies(userId);
                call1.enqueue(dummies1);
            }
        },1000); // 1초후에 새로고침 끝
    }

    Callback dummies1 = new Callback<List<Dummy3>>() {

        @Override
        public void onResponse(Call<List<Dummy3>> call1, Response<List<Dummy3>> response) {
            if (response.isSuccessful()) {
                List<Dummy3> dummies = response.body();
                String[] build1;
                StringBuilder builder_title1 = new StringBuilder();
                StringBuilder builder_category1 = new StringBuilder();
                StringBuilder builder_profile1 = new StringBuilder();
                StringBuilder builder_like1 = new StringBuilder();
                for (Dummy3 dummy : dummies) {
                    build1 = dummy.toString().split(",");
                    builder_title1.append(build1[0]+",");
                    builder_category1.append(build1[1]+",");
                    builder_profile1.append(build1[2]+",");
                    builder_like1.append(build1[3]+",");
                }

                m_Adapter.clear();
                titleArray=builder_title1.toString().split(",");
                categoryArray=builder_category1.toString().split(",");
                profileArray=builder_profile1.toString().split(",");
                likeArray=builder_like1.toString().split(",");

                if(builder_title1.toString().equals("")) {
                }
                else {
                    for (int i = titleArray.length - 1; i >= 0; i--) {
                        m_Adapter.add(Integer.parseInt(profileArray[i]), titleArray[i], categoryArray[i], likeArray[i]);
                    }
                }
                m_Adapter.notifyDataSetChanged();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        }

        @Override
        public void onFailure(Call<List<Dummy3>> call1, Throwable t) {

        }
    };

    Callback dummies2 = new Callback<List<Dummy2>>() {

        @Override
        public void onResponse(Call<List<Dummy2>> call1, Response<List<Dummy2>> response) {
            if (response.isSuccessful()) {
                List<Dummy2> dummies = response.body();
                StringBuilder builder = new StringBuilder();
                String[] build = new String[0];
                for (Dummy2 dummy : dummies) {
                    build = dummy.toString().split(",");
                }
                maintext = build[0];

                for(int i=0;i<titleArray.length;i++){
                    if(titleArray[i]==selecttitle){
                        like = likeArray[i];
                    }
                }

                String[] readpost = new String[]{selecttitle, maintext, like, build[1]};

                intent2 =  new Intent(getActivity(), ReadPost.class);
                intent2.putExtra("strings", userInfo);
                intent2.putExtra("readpost", readpost);
                startActivity(intent2);
            }
        }

        @Override
        public void onFailure(Call<List<Dummy2>> call1, Throwable t) {

        }

    };
}