package com.example.datlichkham.fragment.nguoidung;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.datlichkham.R;
import com.example.datlichkham.adapter.BacSiAdapter;
import com.example.datlichkham.model.Users;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class NdDatLichFragment extends Fragment {
    private DatabaseReference ref;
    private Context mContext;
    private RecyclerView rcDatLich;
    private List<Users> mUsers;
    private BacSiAdapter datLichAdapter;

    public NdDatLichFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    public static NdDatLichFragment newInstance() {
        NdDatLichFragment fragment = new NdDatLichFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nd_dat_lich, container, false);
        rcDatLich = view.findViewById(R.id.rcDatLichKham);
        getDataDoctor();

        return view;
    }

    private void getDataDoctor() {
        mUsers = new ArrayList<>();
        mUsers.clear();
        ref = FirebaseDatabase.getInstance().getReference("users");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mUsers.clear();
                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    if(ds.child("level").getValue(String.class).equalsIgnoreCase("Bác Sĩ")){
                        Users bacSi = ds.getValue(Users.class);
                        if(!bacSi.getFullName().isEmpty())
                            mUsers.add(bacSi);
                    }
                    datLichAdapter = new BacSiAdapter(mUsers);

                    LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
                    layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                    rcDatLich.setLayoutManager(layoutManager);
                    rcDatLich.setAdapter(datLichAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}