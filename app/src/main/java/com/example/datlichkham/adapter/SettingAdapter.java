package com.example.datlichkham.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.datlichkham.R;
import com.example.datlichkham.model.Setting;

import java.util.List;

public class SettingAdapter extends BaseAdapter {
    private Context context;
    private List<Setting> mLists;

    public SettingAdapter(Context context, List<Setting> mLists) {
        this.context = context;
        this.mLists = mLists;
    }

    @Override
    public int getCount() {
        return mLists.size();
    }

    @Override
    public Object getItem(int position) {
        return mLists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SettingViewHolder viewHolder;
        if(convertView == null){
            viewHolder = new SettingViewHolder();
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_setting, parent, false);
            viewHolder.image = convertView.findViewById(R.id.image_setting);
            viewHolder.title = convertView.findViewById(R.id.title_setting);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (SettingViewHolder) convertView.getTag();
        }
        Setting obj = mLists.get(position);
        viewHolder.image.setImageResource(obj.getImage());
        viewHolder.title.setText(obj.getTitle());
        return convertView;
    }

    private static class SettingViewHolder{
        public ImageView image;
        public TextView title;
    }
}
