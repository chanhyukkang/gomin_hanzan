package com.example.hotsix.gomin_hanjan;

import android.content.Context;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.hwan.chatting.R;

import java.util.ArrayList;

public class ChattingAdapter extends BaseAdapter {
    public class ListContents{
        int profile;
        String msg;
        int type;
        String userKey;
        ListContents(int profile, String _msg, int _type, String userKey)
        {
            this.profile = profile;
            this.msg = _msg;
            this.type = _type;
            this.userKey = userKey;
        }
    }

    private ArrayList<ListContents> m_List;
    public ChattingAdapter() {
        m_List = new ArrayList();
    }

    // 외부에서 아이템 추가 요청 시 사용
    public void add(int profile, String _msg,int _type, String userKey) {

        m_List.add(new ListContents(profile,_msg,_type, userKey));
    }

    public String getMessage(int position){
        return m_List.get(position).msg;
    }

    // 외부에서 아이템 삭제 요청 시 사용
    public void remove(int _position) {
        m_List.remove(_position);
    }

    @Override
    public int getCount() {
        return m_List.size();
    }

    @Override
    public Object getItem(int position) {
        return m_List.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public String getUserkey(int position){
        return m_List.get(position).userKey;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        TextView        text    = null;
        CustomHolder    holder  = null;
        LinearLayout    layout  = null;
        View            viewRight = null;
        View            viewLeft = null;
        ImageView       imageView = null;
        TextView        name    = null;

        // 리스트가 길어지면서 현재 화면에 보이지 않는 아이템은 converView가 null인 상태로 들어 옴
        if ( convertView == null ) {
            // view가 null일 경우 커스텀 레이아웃을 얻어 옴
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.activity_chatitem, parent, false);

            ViewGroup.LayoutParams layoutParams = convertView.getLayoutParams();
            layoutParams.height = 160;
            convertView.setLayoutParams(layoutParams);

            layout    = (LinearLayout) convertView.findViewById(R.id.layout);
            text    = (TextView) convertView.findViewById(R.id.text);
            viewRight    = (View) convertView.findViewById(R.id.imageViewright);
            viewLeft    = (View) convertView.findViewById(R.id.imageViewleft);
            imageView   = (ImageView) convertView.findViewById(R.id.imageView2);
            name        = (TextView) convertView.findViewById(R.id.name);

            // 홀더 생성 및 Tag로 등록
            holder = new CustomHolder();
            holder.m_TextView   = text;
            holder.layout = layout;
            holder.viewRight = viewRight;
            holder.viewLeft = viewLeft;
            holder.imageView = imageView;
            holder.name = name;
            convertView.setTag(holder);
        }
        else {
            holder  = (CustomHolder) convertView.getTag();
            text    = holder.m_TextView;
            layout  = holder.layout;
            viewRight = holder.viewRight;
            viewLeft = holder.viewLeft;
            imageView = holder.imageView;
            name = holder.name;
        }

        switch(m_List.get(position).profile) {
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

        // Text 등록
        if(m_List.get(position).msg.charAt(0) == '#') {
            SpannableString content = new SpannableString(m_List.get(position).msg);
            content.setSpan(new UnderlineSpan(), 0, content.length(),0);
            text.setText(content);
            text.setTextColor(Color.parseColor("#0100FF"));
        }
        else{
            text.setTextColor(Color.parseColor("#000000"));
            text.setText(m_List.get(position).msg);
        }

        name.setText("리스너 "+m_List.get(position).userKey);

        if( m_List.get(position).type == 0 ) {
            imageView.setVisibility(imageView.INVISIBLE);
            name.setVisibility(View.INVISIBLE);
            text.setBackgroundResource(R.drawable.outbox2);
            layout.setGravity(Gravity.RIGHT);
            viewRight.setVisibility(View.GONE);
            viewLeft.setVisibility(View.GONE);
        }else if(m_List.get(position).type == 1){
            imageView.setVisibility(imageView.VISIBLE);
            name.setVisibility(View.VISIBLE);
            text.setBackgroundResource(R.drawable.inbox2);
            layout.setGravity(Gravity.LEFT);
            viewRight.setVisibility(View.GONE);
            viewLeft.setVisibility(View.GONE);
        }

        // 리스트 아이템을 길게 터치 했을때 이벤트 발생
//        convertView.setOnLongClickListener(new View.OnLongClickListener() {
//
//            @Override
//            public boolean onLongClick(View v) {
//                // 터치 시 해당 아이템 이름 출력
//                Toast.makeText(context, "리스트 롱 클릭 : "+m_List.get(pos), Toast.LENGTH_SHORT).show();
//                m_List.remove(m_List.get(pos));
//                return false;
//            }
//        });

        return convertView;
    }

    private class CustomHolder {
        ImageView imageView;
        TextView    m_TextView;
        LinearLayout    layout;
        View viewRight;
        View viewLeft;
        TextView name;
    }
}