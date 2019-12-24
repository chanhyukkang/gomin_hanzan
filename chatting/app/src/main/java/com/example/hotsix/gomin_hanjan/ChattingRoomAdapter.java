package com.example.hotsix.gomin_hanjan;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.hwan.chatting.R;

import java.util.ArrayList;

public class ChattingRoomAdapter extends BaseAdapter {
    private class listItem {
        private int profile;    // R.drawable.~ 리소스 아이디 값을 받아오는 변수
        private String name;    // String 카카오톡 대화 목록의 이름
        private String chat;    // String 마지막 대화
        private String like;

        // 매개변수가 있는 생성자로 받아와 값을 전달한다.
        public listItem(int profile, String name, String chat, String like){
            this.profile=profile;
            this.name=name;
            this.chat=chat;
            this.like=like;
        }
    }

    // 외부에서 아이템 추가 요청 시 사용
    private ArrayList<listItem> list;

    public ChattingRoomAdapter(ArrayList<listItem> list){
        // Adapter 생성시 list값을 넘겨 받는다.
        this.list=list;
    }

    public ChattingRoomAdapter() {
        list = new ArrayList();
    }

    public void add(int profile, String name, String chat, String like) {
        list.add(new listItem(profile, name, chat, like));
    }

    public void remove(int _position) {
        list.remove(_position);
    }

    public void clear() {
        list.clear();
    }

    @Override
    public int getCount() {
        // list의 사이즈 만큼 반환
        return list.size();
    }

    @Override
    public listItem getItem(int position) {
        // 현재 position에 따른 list의 값을 반환 시켜준다.
        return list.get(position);
    }

    public String getPosition(int position) {
        // 현재 position에 따른 list의 값을 반환 시켜준다.
        return list.get(position).name;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        TextView        text    = null;
        CustomHolder    holder  = null;
        LinearLayout    layout  = null;
        TextView        textView1 = null;
        TextView        textView2 = null;
        ImageView       imageView = null;
        TextView        textView  = null;

        // 리스트가 길어지면서 현재 화면에 보이지 않는 아이템은 converView가 null인 상태로 들어 옴
        if ( convertView == null ) {
            // view가 null일 경우 커스텀 레이아웃을 얻어 옴
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.activity_chattingroom, parent, false);

            ViewGroup.LayoutParams layoutParams = convertView.getLayoutParams();
            layoutParams.height = 210;
            convertView.setLayoutParams(layoutParams);

            layout    = (LinearLayout) convertView.findViewById(R.id.layout);
            textView1    = (TextView) convertView.findViewById(R.id.name);
            textView2    = (TextView) convertView.findViewById(R.id.chat);
            imageView   = (ImageView) convertView.findViewById(R.id.profile_image);
            textView    = (TextView) convertView.findViewById(R.id.heartnum);

            // 홀더 생성 및 Tag로 등록
            holder = new CustomHolder();
            holder.layout = layout;
            holder.textView1 = textView1;
            holder.textView2 = textView2;
            holder.imageView = imageView;
            holder.textView = textView;
            convertView.setTag(holder);
        }
        else {
            holder  = (CustomHolder) convertView.getTag();
            layout  = holder.layout;
            textView1 = holder.textView1;
            textView2 = holder.textView2;
            imageView = holder.imageView;
            textView = holder.textView;
        }

        // Text 등록
        switch(getItem(pos).profile) {
            case 0:
                imageView.setImageResource(R.drawable.happy);
                break;
            case 1:
                imageView.setImageResource(R.drawable.surprised);
                break;
            case 2:
                imageView.setImageResource(R.drawable.angry);
                break;
            case 3:
                imageView.setImageResource(R.drawable.fear);
                break;
            case 4:
                imageView.setImageResource(R.drawable.disgust);
                break;
            case 5:
                imageView.setImageResource(R.drawable.sad);
                break;
            default: break;

        }
        //imageView.setImageResource()
        textView1.setText(getItem(pos).name);
        textView2.setText(getItem(pos).chat);
        textView.setText(getItem(pos).like);

        return convertView;
    }

    private class CustomHolder {
        public TextView textView1;
        public TextView textView2;
        ImageView imageView;
        LinearLayout layout;
        TextView textView;
    }
}