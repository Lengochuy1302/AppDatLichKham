package com.example.datlichkham.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.datlichkham.R;
import com.example.datlichkham.model.IntroItem;

import java.util.List;

public class IntroAdapter extends RecyclerView.Adapter<IntroAdapter.IntroViewHolder>{
    private List<IntroItem> introItems;

    public IntroAdapter(List<IntroItem> introItems) {
        this.introItems = introItems;
    }

    @NonNull
    @Override
    public IntroViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_intro, parent, false);
        return new IntroViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IntroViewHolder holder, int position) {
        holder.setData(introItems.get(position));
    }

    @Override
    public int getItemCount() {
        return introItems.size();
    }

    public static class IntroViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivAvatar;
        private TextView tvTitle;

        public IntroViewHolder(@NonNull View itemView) {
            super(itemView);
            ivAvatar = itemView.findViewById(R.id.itemIntro_ivAvatar);
            tvTitle = itemView.findViewById(R.id.itemIntro_tvTitle);
        }

        public void setData(IntroItem introItem){
            ivAvatar.setImageResource(introItem.getImageView());
            tvTitle.setText(""+introItem.getTextView());
        }
    }
}
