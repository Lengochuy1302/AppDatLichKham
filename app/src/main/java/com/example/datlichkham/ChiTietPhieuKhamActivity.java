package com.example.datlichkham;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.datlichkham.model.PhieuKham;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ChiTietPhieuKhamActivity extends AppCompatActivity {
    private String idPk;

    private TextView tvId, tvTenBs, tvTenBn, tvTime, tvBenh, tvKeDon;
    private RatingBar ratingBar;
    private Button btnDong;

    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_phieu_kham);
        getSupportActionBar().hide();
        mappingView();
        getDataFromDB();
        btnDong.setOnClickListener(v -> {
            databaseReference.child("History").child(idPk).child("rate").setValue(ratingBar.getRating());
            finish();
        });
    }

    private void getDataFromDB() {
        databaseReference.child("History").child(idPk).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                PhieuKham obj = dataSnapshot.getValue(PhieuKham.class);
                setDataFromDb(obj);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void setDataFromDb(PhieuKham obj) {
        tvId.setText("Mã phiếu: " + obj.getId());
        tvTenBs.setText(obj.getTenBs());
        tvTenBn.setText(obj.getTenBn());
        tvTime.setText(obj.getTime() + " ngày " + obj.getDate());
        tvBenh.setText(obj.getBenh());
        tvKeDon.setText(obj.getNote());
        ratingBar.setRating(obj.getRate());
    }

    private void mappingView() {
        databaseReference = FirebaseDatabase.getInstance().getReference();
        tvId = findViewById(R.id.tvID_ChiTietPhieuKham);
        tvTenBs = findViewById(R.id.tvTenBs_ChiTietPhieuKham);
        tvTenBn = findViewById(R.id.tvTenBn_ChiTietPhieuKham);
        tvTime = findViewById(R.id.tvTime_ChiTietPhieuKham);
        tvBenh = findViewById(R.id.tvTenBenh_ChiTietPhieuKham);
        tvKeDon = findViewById(R.id.tvChiTiet_ChiTietPhieuKham);
        btnDong = findViewById(R.id.btnDong_ChiTietPhieuKham);
        ratingBar = findViewById(R.id.ratingBar);
        ratingBar.setStepSize(1);
        if(getSharedPreferences("PREFS", MODE_PRIVATE).getString("LEVEL", "").equalsIgnoreCase("Bác Sĩ")){
            ratingBar.setIsIndicator(true);
        }
        idPk = getIntent().getStringExtra("IDPK");
    }
}