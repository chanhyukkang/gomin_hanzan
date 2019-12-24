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

public class ImageAdapter extends BaseAdapter {
    Context mContext;
    int rowwidth;
    int num;
    public class ListContents{
        int profile;
        String title;
        ListContents(int profile, String title)
        {
            this.profile = profile;
            this.title = title;

        }
    }

    private ArrayList<ImageAdapter.ListContents> m_List;
    public ImageAdapter(int rowwidth, Context con) {
        m_List = new ArrayList();
        this.rowwidth = rowwidth;
        mContext = con;
    }

    // 외부에서 아이템 추가 요청 시 사용
    public void add(int profile, String title) {
        m_List.add(new ListContents(profile,title));
        num = profile;
    }

    private Integer[] mThumbIds = { R.drawable.happy,
            R.drawable.surprised, R.drawable.angry,
            R.drawable.fear,R.drawable.disgust,
            R.drawable.sad
    };


    public int getCount() {
        return mThumbIds.length;
    }

    public Object getItem(int position) {
        return mThumbIds[position];
    }
    public long getItemId(int position) {
        return position;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {

        ImageView imageView = null;
        TextView text    = null;
        LinearLayout layout  = null;
        CustomHolder holder  = null;
        final Context context = parent.getContext();


        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.activity_griditem, parent, false);
            imageView = convertView.findViewById(R.id.imageView323);
            layout = convertView.findViewById(R.id.layout123);
            text = convertView.findViewById(R.id.textView_title223);
            holder = new CustomHolder();
            holder.textView   = text;
            holder.layout = layout;
            holder.imageView = imageView;
            convertView.setTag(holder);
        } else {
            holder  = (CustomHolder) convertView.getTag();
            text = holder.textView;
            layout = holder.layout;
            imageView =holder.imageView;
        }
        imageView.setPadding(1, 1, 1, 1);
        imageView.setLayoutParams(new LinearLayout.LayoutParams(this.rowwidth,this.rowwidth));
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
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
        text.setText(m_List.get(position).title);

        return convertView;
    }
    private class CustomHolder {
        ImageView imageView;
        LinearLayout layout;
        TextView textView;
    }
}

