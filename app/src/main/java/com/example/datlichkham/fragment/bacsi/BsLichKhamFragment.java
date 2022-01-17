package com.example.datlichkham.fragment.bacsi;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.datlichkham.MainActivity;
import com.example.datlichkham.R;
import com.example.datlichkham.adapter.LichKhamAdapter;
import com.example.datlichkham.model.PhieuKham;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class BsLichKhamFragment extends Fragment {
    private Context context;
    private List<PhieuKham> mList;
    private DatabaseReference ref;
    private String idBs;
    private RecyclerView rcLichKham;

    public BsLichKhamFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    public static BsLichKhamFragment newInstance() {
        BsLichKhamFragment fragment = new BsLichKhamFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bs_lich_kham, container, false);
        idBs = context.getSharedPreferences("PREFS", Context.MODE_PRIVATE).getString("USERNAME", "");
        ref = FirebaseDatabase.getInstance().getReference("History");
        rcLichKham = view.findViewById(R.id.rcPhieuKham);
        dangKham();
        getDataLichKham();
        return view;
    }

    private void getDataLichKham() {
        mList = new ArrayList<>();
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mList.clear();
                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    if(ds.child("idBs").getValue(String.class).equalsIgnoreCase(idBs) && ds.child("status").getValue(String.class).equalsIgnoreCase("đang chờ")){
                        PhieuKham obj = ds.getValue(PhieuKham.class);
                        mList.add(obj);
                    }
                }
                LichKhamAdapter lichKhamAdapter = new LichKhamAdapter(getContext(), mList);
                LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
                layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                rcLichKham.setLayoutManager(layoutManager);
                new ItemTouchHelper(simpleCallback).attachToRecyclerView(rcLichKham);
                rcLichKham.setAdapter(lichKhamAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, 8|4) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            PhieuKham obj = mList.get(Integer.valueOf(String.valueOf(viewHolder.itemView.getTag())));
            SharedPreferences pref = context.getSharedPreferences("BACSI", Context.MODE_PRIVATE);
            ref.child(obj.getId()).child("status").setValue("Đang khám");
            pref.edit().putString("IDPK", obj.getId()).commit();
            pref.edit().putBoolean("DANGKHAM", true).commit();
            dangKham();
        }
    };

    private void dangKham() {
        Boolean dangKhamBenh = context.getSharedPreferences("BACSI", Context.MODE_PRIVATE).getBoolean("DANGKHAM", false);
        if(dangKhamBenh){
            getFragmentManager().beginTransaction().add(R.id.fragment_container, new DangKhamFragment()).remove(this).commit();
        }
    }


}