package com.example.datlichkham.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.datlichkham.R;

public class finiteAdapter extends RecyclerView.Adapter<finiteAdapter.finiteViewHolder>{
    Integer [] images = {R.drawable.src_assets_images_giadinh14, R.drawable.src_assets_images_giadinh21, R.drawable.src_assets_images_giadinh26};

    public finiteAdapter() {
    }

    @NonNull
    @Override
    public finiteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.finite_image, parent, false);
        return new finiteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull finiteViewHolder holder, int position) {
        holder.imageView.setImageResource(images[position]);
    }

    @Override
    public int getItemCount() {
        return images.length;
    }

    public static class finiteViewHolder extends RecyclerView.ViewHolder{
        private ImageView imageView;
        public finiteViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.iv_finite);
        }
    }
}
