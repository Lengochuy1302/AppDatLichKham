package com.example.datlichkham.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.datlichkham.ChiTietPhieuKhamActivity;
import com.example.datlichkham.MainActivity;
import com.example.datlichkham.R;
import com.example.datlichkham.model.PhieuKham;

import java.util.List;

public class LichSuNDAdapter extends RecyclerView.Adapter<LichSuNDAdapter.LichSuViewHolder>{
    private Context context;
    private List<PhieuKham> phieuKhamList;

    public LichSuNDAdapter(Context context, List<PhieuKham> phieuKhamList) {
        this.context = context;
        this.phieuKhamList = phieuKhamList;
    }

    @NonNull
    @Override
    public LichSuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lich_su_kham, parent, false);
        return new LichSuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LichSuViewHolder holder, int position) {
        PhieuKham obj = phieuKhamList.get(position);
        holder.tvTen.setText(obj.getTenBs());
        holder.tvTime.setText(obj.getDate() + " lúc " + obj.getTime());
        holder.tvStatus.setText(obj.getStatus());
        if(obj.getStatus().equalsIgnoreCase("Đang chờ")){
            holder.tvStatus.setTextColor(context.getResources().getColor(R.color.red));
        }
        if(obj.getStatus().equalsIgnoreCase("Đang khám")){
            holder.tvStatus.setTextColor(context.getResources().getColor(R.color.yellow));
        }

        holder.itemView.setOnClickListener(v -> {
            if(obj.getStatus().equalsIgnoreCase("Hoàn Thành")){
                Intent intent = new Intent(context, ChiTietPhieuKhamActivity.class);
                intent.putExtra("IDPK", obj.getId());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return phieuKhamList.size();
    }

    public static class LichSuViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTen, tvTime, tvStatus;
        public LichSuViewHolder(@NonNull View itemView) {
            super(itemView);
            tvStatus = itemView.findViewById(R.id.tvStatus_lichSuKham);
            tvTime = itemView.findViewById(R.id.tv_time_lichSuKham);
            tvTen = itemView.findViewById(R.id.tv_ten_lichSuKham);
        }
    }
}
