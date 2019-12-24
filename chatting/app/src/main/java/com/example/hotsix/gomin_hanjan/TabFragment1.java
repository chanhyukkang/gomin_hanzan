package com.example.hotsix.gomin_hanjan;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
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

import com.example.hwan.chatting.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TabFragment1 extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    Intent intent,intent2;
    ArrayAdapter adapter;
    Button b1, b2, b3, b4, b5, b6, b7, b8, b9, whole,
            b1_selected, b2_selected, b3_selected, b4_selected, b5_selected, b6_selected, b7_selected, b8_selected, b9_selected, whole_selected, search;
    String userId,userPassword, maintext, name, trust,emotion, selecttitle, like;
    String chattingroom_id = "0";
    TextView text, text_sorted;
    SwipeRefreshLayout mSwipeRefreshLayout;
    Context context;
    ArrayAdapter<String> adapter1;
    ArrayList<String> list;
    String[] userInfo, titleArray, categoryArray, profileArray, likeArray,tempArray;
    int[] indexArray;
    Boolean ischecked2;
    ChattingRoomAdapter m_Adapter = new ChattingRoomAdapter();
    EditText editSearch;
    Switch sortingswitch;
    boolean likesorting;
    String category = "";
    int s =0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final ListView listview;
        View view = inflater.inflate(R.layout.tab_fragment_1, container, false);
        b1 = view.findViewById(R.id.b1); b2 = view.findViewById(R.id.b2); b3 = view.findViewById(R.id.b3); b4 = view.findViewById(R.id.b4);
        b5 = view.findViewById(R.id.b5); b6 = view.findViewById(R.id.b6); b7 = view.findViewById(R.id.b7); b8 = view.findViewById(R.id.b8);
        b9 = view.findViewById(R.id.b9); whole = view.findViewById(R.id.whole);
        b1_selected = view.findViewById(R.id.b1_selected); b2_selected = view.findViewById(R.id.b2_selected); b3_selected = view.findViewById(R.id.b3_selected); b4_selected = view.findViewById(R.id.b4_selected);
        b5_selected = view.findViewById(R.id.b5_selected); b6_selected = view.findViewById(R.id.b6_selected); b7_selected = view.findViewById(R.id.b7_selected); b8_selected = view.findViewById(R.id.b8_selected);
        b9_selected = view.findViewById(R.id.b9_selected); whole_selected = view.findViewById(R.id.whole_selected);

        mSwipeRefreshLayout = view.findViewById(R.id.refresh);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.blue);
        context = container.getContext();
        editSearch = view.findViewById(R.id.editSearch);
        sortingswitch = view.findViewById(R.id.sorting);
        search = view.findViewById(R.id.search);

        Intent intent1 = getActivity().getIntent();
        userInfo = intent1.getStringArrayExtra("strings");
        userId = userInfo[0];
        userPassword = userInfo[1];
        name = userInfo[3];
        trust = userInfo[4];
        emotion = userInfo[5];
        like = "1";
        text = (TextView) view.findViewById(R.id.text);
        list = new ArrayList<>();
        adapter = new ArrayAdapter(getActivity(),android.R.layout.simple_list_item_1,list);
        listview = (ListView)view.findViewById(R.id.listview1);
        listview.setAdapter(m_Adapter);

        titleArray=userInfo[2].split(",");
        likeArray=userInfo[6].split(",");
        categoryArray=userInfo[7].split(",");
        profileArray=userInfo[8].split(",");

        b1_selected.setVisibility(View.GONE);b2_selected.setVisibility(View.GONE);b3_selected.setVisibility(View.GONE);b4_selected.setVisibility(View.GONE);
        b5_selected.setVisibility(View.GONE);b6_selected.setVisibility(View.GONE);b7_selected.setVisibility(View.GONE);b8_selected.setVisibility(View.GONE);
        b9_selected.setVisibility(View.GONE);whole.setVisibility(View.GONE);

        m_Adapter.clear();

        //처음에는 최신순으로 고민 방 리스트 보여줌
        if(userInfo[2].equals("")){
        }
        else{
            for (int i = titleArray.length - 1; i >= 0; i--) {
                m_Adapter.add(Integer.parseInt(profileArray[i]), titleArray[i], categoryArray[i], likeArray[i]);
            }
        }

        //likeArray를 tempArray에 복사해서 공감순으로 정렬하고 인덱스 순서 얻기
        tempArray=userInfo[6].split(",");
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

        editSearch.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if ((keyEvent.getAction() == KeyEvent.ACTION_DOWN) && i == KeyEvent.KEYCODE_ENTER) {
                    listview.requestFocus();
                    return true;
                }
                return false;
            }
        });
        //공감 순 스위치 버튼 ON OFF 할 때
        sortingswitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ischecked2 = isChecked;
                if (isChecked) { //공감순 일 때
                    likesorting=true;
                    m_Adapter.clear();
                    yellow();
                    if(category.equals("# 학업")) {
                        b8_selected.setVisibility(View.VISIBLE);
                        b8.setVisibility(View.GONE);
                        for (int i = 0; i <= titleArray.length - 1; i++)
                            if (categoryArray[indexArray[i]].equals("# 학업"))
                                m_Adapter.add(Integer.parseInt(profileArray[indexArray[i]]), titleArray[indexArray[i]], categoryArray[indexArray[i]], likeArray[indexArray[i]]);
                    }
                    else if(category.equals("# 이성")) {
                        b2_selected.setVisibility(View.VISIBLE);
                        b2.setVisibility(View.GONE);
                        for (int i = 0; i <= titleArray.length - 1; i++)
                            if (categoryArray[indexArray[i]].equals("# 이성"))
                                m_Adapter.add(Integer.parseInt(profileArray[indexArray[i]]), titleArray[indexArray[i]], categoryArray[indexArray[i]], likeArray[indexArray[i]]);
                    }
                    else if(category.equals("# 진로")) {
                        b1_selected.setVisibility(View.VISIBLE);
                        b1.setVisibility(View.GONE);
                        for (int i = 0; i <= titleArray.length - 1; i++)
                            if (categoryArray[indexArray[i]].equals("# 진로"))
                                m_Adapter.add(Integer.parseInt(profileArray[indexArray[i]]), titleArray[indexArray[i]], categoryArray[indexArray[i]], likeArray[indexArray[i]]);
                    }
                    else if(category.equals("# 취업")) {
                        b3_selected.setVisibility(View.VISIBLE);
                        b3.setVisibility(View.GONE);
                        for (int i = 0; i <= titleArray.length - 1; i++)
                            if (categoryArray[indexArray[i]].equals("# 취업"))
                                m_Adapter.add(Integer.parseInt(profileArray[indexArray[i]]), titleArray[indexArray[i]], categoryArray[indexArray[i]], likeArray[indexArray[i]]);
                    }
                    else if(category.equals("# 기타")) {
                        b9_selected.setVisibility(View.VISIBLE);
                        b9.setVisibility(View.GONE);
                        for (int i = 0; i <= titleArray.length - 1; i++)
                            if (categoryArray[indexArray[i]].equals("# 기타"))
                                m_Adapter.add(Integer.parseInt(profileArray[indexArray[i]]), titleArray[indexArray[i]], categoryArray[indexArray[i]], likeArray[indexArray[i]]);
                    }
                    else if(category.equals("# 경제")) {
                        b4_selected.setVisibility(View.VISIBLE);
                        b4.setVisibility(View.GONE);
                        for (int i = 0; i <= titleArray.length - 1; i++)
                            if (categoryArray[indexArray[i]].equals("# 경제"))
                                m_Adapter.add(Integer.parseInt(profileArray[indexArray[i]]), titleArray[indexArray[i]], categoryArray[indexArray[i]], likeArray[indexArray[i]]);
                    }
                    else if(category.equals("# 인간관계")) {
                        b6_selected.setVisibility(View.VISIBLE);
                        b6.setVisibility(View.GONE);
                        for (int i = 0; i <= titleArray.length - 1; i++)
                            if (categoryArray[indexArray[i]].equals("# 인간관계"))
                                m_Adapter.add(Integer.parseInt(profileArray[indexArray[i]]), titleArray[indexArray[i]], categoryArray[indexArray[i]], likeArray[indexArray[i]]);
                    }
                    else if(category.equals("# 외모")) {
                        b5_selected.setVisibility(View.VISIBLE);
                        b5.setVisibility(View.GONE);
                        for (int i = 0; i <= titleArray.length - 1; i++)
                            if (categoryArray[indexArray[i]].equals("# 외모"))
                                m_Adapter.add(Integer.parseInt(profileArray[indexArray[i]]), titleArray[indexArray[i]], categoryArray[indexArray[i]], likeArray[indexArray[i]]);
                    }
                    else if(category.equals("# 가정")) {
                        b7_selected.setVisibility(View.VISIBLE);
                        b7.setVisibility(View.GONE);
                        for (int i = 0; i <= titleArray.length - 1; i++)
                            if (categoryArray[indexArray[i]].equals("# 가정"))
                                m_Adapter.add(Integer.parseInt(profileArray[indexArray[i]]), titleArray[indexArray[i]], categoryArray[indexArray[i]], likeArray[indexArray[i]]);
                    }
                    else{ //전체일때
                        if(userInfo[2].equals("")){
                        }
                        else{
                            whole_selected.setVisibility(View.VISIBLE);
                            whole.setVisibility(View.GONE);
                            for (int i = 0; i <= titleArray.length - 1; i++) {
                                m_Adapter.add(Integer.parseInt(profileArray[indexArray[i]]), titleArray[indexArray[i]], categoryArray[indexArray[i]], likeArray[indexArray[i]]);
                            }
                        }
                    }
                    m_Adapter.notifyDataSetChanged();
                }

                else{ //공감순 아닐 때
                    likesorting=false;
                    m_Adapter.clear();

                    if(category.equals("# 진로")) {
                        b1_selected.setVisibility(View.VISIBLE);
                        b1.setVisibility(View.GONE);
                        for (int i = titleArray.length - 1; i >= 0; i--)
                            if (categoryArray[i].equals("# 진로"))
                                m_Adapter.add(Integer.parseInt(profileArray[i]), titleArray[i], categoryArray[i], likeArray[i]);
                    }
                    else if(category.equals("# 학업")) {
                        b8_selected.setVisibility(View.VISIBLE);
                        b8.setVisibility(View.GONE);
                        for (int i = titleArray.length - 1; i >= 0; i--)
                            if (categoryArray[i].equals("# 학업"))
                                m_Adapter.add(Integer.parseInt(profileArray[i]), titleArray[i], categoryArray[i], likeArray[i]);
                    }
                    else if(category.equals("# 이성")) {
                        b2_selected.setVisibility(View.VISIBLE);
                        b2.setVisibility(View.GONE);
                        for (int i = titleArray.length - 1; i >= 0; i--)
                            if (categoryArray[i].equals("# 이성"))
                                m_Adapter.add(Integer.parseInt(profileArray[i]), titleArray[i], categoryArray[i], likeArray[i]);
                    }
                    else if(category.equals("# 기타")) {
                        b9_selected.setVisibility(View.VISIBLE);
                        b9.setVisibility(View.GONE);
                        for (int i = titleArray.length - 1; i >= 0; i--)
                            if (categoryArray[i].equals("# 기타"))
                                m_Adapter.add(Integer.parseInt(profileArray[i]), titleArray[i], categoryArray[i], likeArray[i]);
                    }
                    else if(category.equals("# 가정")) {
                        b7_selected.setVisibility(View.VISIBLE);
                        b7.setVisibility(View.GONE);
                        for (int i = titleArray.length - 1; i >= 0; i--)
                            if (categoryArray[i].equals("# 가정"))
                                m_Adapter.add(Integer.parseInt(profileArray[i]), titleArray[i], categoryArray[i], likeArray[i]);
                    }
                    else if(category.equals("# 인간관계")) {
                        b6_selected.setVisibility(View.VISIBLE);
                        b6.setVisibility(View.GONE);
                        for (int i = titleArray.length - 1; i >= 0; i--)
                            if (categoryArray[i].equals("# 인간관계"))
                                m_Adapter.add(Integer.parseInt(profileArray[i]), titleArray[i], categoryArray[i], likeArray[i]);
                    }
                    else if(category.equals("# 취업")) {
                        b3_selected.setVisibility(View.VISIBLE);
                        b3.setVisibility(View.GONE);
                        for (int i = titleArray.length - 1; i >= 0; i--)
                            if (categoryArray[i].equals("# 취업"))
                                m_Adapter.add(Integer.parseInt(profileArray[i]), titleArray[i], categoryArray[i], likeArray[i]);
                    }
                    else if(category.equals("# 경제")) {
                        b4_selected.setVisibility(View.VISIBLE);
                        b4.setVisibility(View.GONE);
                        for (int i = titleArray.length - 1; i >= 0; i--)
                            if (categoryArray[i].equals("# 경제"))
                                m_Adapter.add(Integer.parseInt(profileArray[i]), titleArray[i], categoryArray[i], likeArray[i]);
                    }
                    else if(category.equals("# 외모")) {
                        b5_selected.setVisibility(View.VISIBLE);
                        b5.setVisibility(View.GONE);
                        for (int i = titleArray.length - 1; i >= 0; i--)
                            if (categoryArray[i].equals("# 외모"))
                                m_Adapter.add(Integer.parseInt(profileArray[i]), titleArray[i], categoryArray[i], likeArray[i]);
                    }
                    else {
                        whole_selected.setVisibility(View.VISIBLE);
                        whole.setVisibility(View.GONE);
                        for (int i = titleArray.length - 1; i >= 0; i--)
                            m_Adapter.add(Integer.parseInt(profileArray[i]), titleArray[i], categoryArray[i], likeArray[i]);
                    }
                }
                m_Adapter.notifyDataSetChanged();
            }
        });

        whole.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                yellow();
                whole_selected.setVisibility(View.VISIBLE);
                whole.setVisibility(View.GONE);
                category = "# 전체";
                if (likesorting) {
                    m_Adapter.clear();
                    for (int i = 0; i <= titleArray.length - 1; i++)
                        m_Adapter.add(Integer.parseInt(profileArray[indexArray[i]]), titleArray[indexArray[i]], categoryArray[indexArray[i]], likeArray[indexArray[i]]);
                    m_Adapter.notifyDataSetChanged();
                } else {
                    m_Adapter.clear();
                    for (int i = titleArray.length - 1; i >= 0; i--)
                        m_Adapter.add(Integer.parseInt(profileArray[i]), titleArray[i], categoryArray[i], likeArray[i]);
                    m_Adapter.notifyDataSetChanged();
                }
            }
        });
        whole_selected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                yellow();
                whole_selected.setVisibility(View.VISIBLE);
                whole.setVisibility(View.GONE);
                category = "# 전체";
                if (likesorting) {
                    m_Adapter.clear();
                    for (int i = 0; i <= titleArray.length - 1; i++)
                        m_Adapter.add(Integer.parseInt(profileArray[indexArray[i]]), titleArray[indexArray[i]], categoryArray[indexArray[i]], likeArray[indexArray[i]]);
                    m_Adapter.notifyDataSetChanged();
                } else {
                    m_Adapter.clear();
                    for (int i = titleArray.length - 1; i >= 0; i--)
                        m_Adapter.add(Integer.parseInt(profileArray[i]), titleArray[i], categoryArray[i], likeArray[i]);
                    m_Adapter.notifyDataSetChanged();
                }
            }
        });
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                yellow();
                b1_selected.setVisibility(View.VISIBLE);
                b1.setVisibility(View.GONE);
                category = "# 진로";
                if (likesorting) {
                    m_Adapter.clear();
                    for (int i = 0; i <= titleArray.length - 1; i++)
                        if (categoryArray[indexArray[i]].equals("# 진로"))
                            m_Adapter.add(Integer.parseInt(profileArray[indexArray[i]]), titleArray[indexArray[i]], categoryArray[indexArray[i]], likeArray[indexArray[i]]);
                    m_Adapter.notifyDataSetChanged();
                } else {
                    m_Adapter.clear();
                    for (int i = titleArray.length - 1; i >= 0; i--)
                        if (categoryArray[i].equals("# 진로"))
                            m_Adapter.add(Integer.parseInt(profileArray[i]), titleArray[i], categoryArray[i], likeArray[i]);
                    m_Adapter.notifyDataSetChanged();
                }
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                yellow();
                b2_selected.setVisibility(View.VISIBLE);
                b2.setVisibility(View.GONE);
                category = "# 이성";
                if (likesorting) {
                    m_Adapter.clear();
                    for (int i = 0; i <= titleArray.length - 1; i++)
                        if (categoryArray[indexArray[i]].equals("# 이성"))
                            m_Adapter.add(Integer.parseInt(profileArray[indexArray[i]]), titleArray[indexArray[i]], categoryArray[indexArray[i]], likeArray[indexArray[i]]);
                    m_Adapter.notifyDataSetChanged();
                } else {
                    m_Adapter.clear();
                    for (int i = titleArray.length - 1; i >= 0; i--)
                        if (categoryArray[i].equals("# 이성"))
                            m_Adapter.add(Integer.parseInt(profileArray[i]), titleArray[i], categoryArray[i], likeArray[i]);
                    m_Adapter.notifyDataSetChanged();
                }
            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                yellow();
                b3_selected.setVisibility(View.VISIBLE);
                b3.setVisibility(View.GONE);
                category = "# 취업";
                if (likesorting) {
                    m_Adapter.clear();
                    for (int i = 0; i <= titleArray.length - 1; i++)
                        if (categoryArray[indexArray[i]].equals("# 취업"))
                            m_Adapter.add(Integer.parseInt(profileArray[indexArray[i]]), titleArray[indexArray[i]], categoryArray[indexArray[i]], likeArray[indexArray[i]]);
                    m_Adapter.notifyDataSetChanged();
                } else {
                    m_Adapter.clear();
                    for (int i = titleArray.length - 1; i >= 0; i--)
                        if (categoryArray[i].equals("# 취업"))
                            m_Adapter.add(Integer.parseInt(profileArray[i]), titleArray[i], categoryArray[i], likeArray[i]);
                    m_Adapter.notifyDataSetChanged();
                }
            }
        });
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                yellow();
                b4_selected.setVisibility(View.VISIBLE);
                b4.setVisibility(View.GONE);
                category = "# 경제";
                if (likesorting) {
                    m_Adapter.clear();
                    for (int i = 0; i <= titleArray.length - 1; i++)
                        if (categoryArray[indexArray[i]].equals("# 경제"))
                            m_Adapter.add(Integer.parseInt(profileArray[indexArray[i]]), titleArray[indexArray[i]], categoryArray[indexArray[i]], likeArray[indexArray[i]]);
                    m_Adapter.notifyDataSetChanged();
                } else {
                    m_Adapter.clear();
                    for (int i = titleArray.length - 1; i >= 0; i--)
                        if (categoryArray[i].equals("# 경제"))
                            m_Adapter.add(Integer.parseInt(profileArray[i]), titleArray[i], categoryArray[i], likeArray[i]);
                    m_Adapter.notifyDataSetChanged();
                }
            }
        });
        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                yellow();
                b5_selected.setVisibility(View.VISIBLE);
                b5.setVisibility(View.GONE);
                category = "# 외모";
                if (likesorting) {
                    m_Adapter.clear();
                    for (int i = 0; i <= titleArray.length - 1; i++)
                        if(categoryArray[indexArray[i]].equals("# 외모"))
                            m_Adapter.add(Integer.parseInt(profileArray[indexArray[i]]), titleArray[indexArray[i]], categoryArray[indexArray[i]], likeArray[indexArray[i]]);
                    m_Adapter.notifyDataSetChanged();
                } else {
                    m_Adapter.clear();
                    for (int i = titleArray.length - 1; i >= 0; i--)
                        if (categoryArray[i].equals("# 외모"))
                            m_Adapter.add(Integer.parseInt(profileArray[i]), titleArray[i], categoryArray[i], likeArray[i]);
                    m_Adapter.notifyDataSetChanged();
                }
            }
        });
        b6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                yellow();
                b6_selected.setVisibility(View.VISIBLE);
                b6.setVisibility(View.GONE);
                category = "# 인간관계";
                if (likesorting) {
                    m_Adapter.clear();
                    for (int i = 0; i <= titleArray.length - 1; i++)
                        if(categoryArray[indexArray[i]].equals("# 인간관계"))
                            m_Adapter.add(Integer.parseInt(profileArray[indexArray[i]]), titleArray[indexArray[i]], categoryArray[indexArray[i]], likeArray[indexArray[i]]);
                    m_Adapter.notifyDataSetChanged();
                } else {
                    m_Adapter.clear();
                    for (int i = titleArray.length - 1; i >= 0; i--)
                        if (categoryArray[i].equals("# 인간관계"))
                            m_Adapter.add(Integer.parseInt(profileArray[i]), titleArray[i], categoryArray[i], likeArray[i]);
                    m_Adapter.notifyDataSetChanged();
                }
            }
        });
        b7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                yellow();
                b7_selected.setVisibility(View.VISIBLE);
                b7.setVisibility(View.GONE);
                category = "# 가정";
                if (likesorting) {
                    m_Adapter.clear();
                    for (int i = 0; i <= titleArray.length - 1; i++)
                        if (categoryArray[indexArray[i]].equals("# 가정"))
                            m_Adapter.add(Integer.parseInt(profileArray[indexArray[i]]), titleArray[indexArray[i]], categoryArray[indexArray[i]], likeArray[indexArray[i]]);
                    m_Adapter.notifyDataSetChanged();
                } else {
                    m_Adapter.clear();
                    for (int i = titleArray.length - 1; i >= 0; i--)
                        if (categoryArray[i].equals("# 가정"))
                            m_Adapter.add(Integer.parseInt(profileArray[i]), titleArray[i], categoryArray[i], likeArray[i]);
                    m_Adapter.notifyDataSetChanged();
                }
            }
        });
        b8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                yellow();
                b8_selected.setVisibility(View.VISIBLE);
                b8.setVisibility(View.GONE);
                category = "# 학업";
                if (likesorting) {
                    m_Adapter.clear();
                    for (int i = 0; i <= titleArray.length - 1; i++)
                        if (categoryArray[indexArray[i]].equals("# 학업"))
                            m_Adapter.add(Integer.parseInt(profileArray[indexArray[i]]), titleArray[indexArray[i]], categoryArray[indexArray[i]], likeArray[indexArray[i]]);
                    m_Adapter.notifyDataSetChanged();
                } else {
                    m_Adapter.clear();
                    for (int i = titleArray.length - 1; i >= 0; i--)
                        if (categoryArray[i].equals("# 학업"))
                            m_Adapter.add(Integer.parseInt(profileArray[i]), titleArray[i], categoryArray[i], likeArray[i]);
                    m_Adapter.notifyDataSetChanged();
                }
            }
        });
        b9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                yellow();
                b9_selected.setVisibility(View.VISIBLE);
                b9.setVisibility(View.GONE);
                category = "# 기타";
                if (likesorting) {
                    m_Adapter.clear();
                    for (int i = 0; i <= titleArray.length - 1; i++)
                        if (categoryArray[indexArray[i]].equals("# 기타"))
                            m_Adapter.add(Integer.parseInt(profileArray[indexArray[i]]), titleArray[indexArray[i]], categoryArray[indexArray[i]], likeArray[indexArray[i]]);
                    m_Adapter.notifyDataSetChanged();
                } else {
                    m_Adapter.clear();
                    for (int i = titleArray.length - 1; i >= 0; i--)
                        if (categoryArray[i].equals("# 기타"))
                            m_Adapter.add(Integer.parseInt(profileArray[i]), titleArray[i], categoryArray[i], likeArray[i]);
                    m_Adapter.notifyDataSetChanged();
                }
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
    } //onCreateView

    public void search(String charText) {

        // 문자 입력시마다 리스트를 지우고 새로 뿌려준다.
        m_Adapter.clear();

        // 문자 입력이 없을때는 모든 데이터를 보여준다.
        if (charText.length() == 0) {
            if(userInfo[2].equals("")){
            }
            else{
                for (int i = titleArray.length - 1; i >= 0; i--) {
                    m_Adapter.add(Integer.parseInt(profileArray[i]), titleArray[i], categoryArray[i], likeArray[i]);
                }
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

    //새로고침 코드
    @Override
    public void onRefresh(){
        mSwipeRefreshLayout.setRefreshing(true);
        final String BASE = SharedPreference.getAttribute(context.getApplicationContext(), "IP");

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() { // 여기에 코드 추가
                Retrofit retrofit1 = new Retrofit.Builder()
                        .baseUrl(BASE)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                ChattingInformationInterface chattingInformationInterface = retrofit1.create(ChattingInformationInterface.class);
                Call<List<Dummy3>> call1 = chattingInformationInterface.listDummies(userId);
                call1.enqueue(dummies1);

            }
        },1000); // 1초후에 새로고침 끝

    }

    Callback dummies1 = new Callback<List<Dummy3>>() {

        @Override
        public void onResponse(Call<List<Dummy3>> call1, Response<List<Dummy3>> response) {
            if (response.isSuccessful()) {
                List<Dummy3> dummies = response.body();
                String[] build;
                StringBuilder builder_like = new StringBuilder();
                StringBuilder builder_title = new StringBuilder();
                StringBuilder builder_category = new StringBuilder();
                StringBuilder builder_profile = new StringBuilder();
                for (Dummy3 dummy : dummies) {
                    build = dummy.toString().split(",");
                    builder_like.append(build[0]+",");
                    builder_title.append(build[1]+",");
                    builder_category.append(build[2]+",");
                    builder_profile.append(build[3]+",");
                }
                m_Adapter.clear();
                titleArray=builder_title.toString().split(",");
                likeArray=builder_like.toString().split(",");
                categoryArray=builder_category.toString().split(",");
                profileArray=builder_profile.toString().split(",");
                if(builder_title.toString().equals("")){
                }
                else{
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

    public void yellow() {
        b1_selected.setVisibility(View.GONE);b2_selected.setVisibility(View.GONE);b3_selected.setVisibility(View.GONE);b4_selected.setVisibility(View.GONE);
        b5_selected.setVisibility(View.GONE);b6_selected.setVisibility(View.GONE);b7_selected.setVisibility(View.GONE);b8_selected.setVisibility(View.GONE);
        b9_selected.setVisibility(View.GONE);whole_selected.setVisibility(View.GONE);
        b1.setVisibility(View.VISIBLE);b2.setVisibility(View.VISIBLE);b3.setVisibility(View.VISIBLE);b4.setVisibility(View.VISIBLE);
        b5.setVisibility(View.VISIBLE);b6.setVisibility(View.VISIBLE);b7.setVisibility(View.VISIBLE);b8.setVisibility(View.VISIBLE);
        b9.setVisibility(View.VISIBLE);whole.setVisibility(View.VISIBLE);
    }

}