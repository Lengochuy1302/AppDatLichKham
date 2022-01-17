package com.example.datlichkham.fragment.bacsi;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.datlichkham.R;
import com.example.datlichkham.model.PhieuKham;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DangKhamFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DangKhamFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String idPhieuKham;

    private EditText edChanDoan, edChiTiet;
    private TextView tvMaPhieuKham, tvTenBn;
    private Button btnHoanThanhKham;

    private DatabaseReference databaseReference;

    public DangKhamFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DangKhamFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DangKhamFragment newInstance(String param1, String param2) {
        DangKhamFragment fragment = new DangKhamFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dang_kham, container, false);
        idPhieuKham = getContext().getSharedPreferences("BACSI", Context.MODE_PRIVATE).getString("IDPK", "");
        mapping(view);
        getDataFromDb();
        hoanThanhKham();
        return view;
    }

    private void hoanThanhKham() {
        btnHoanThanhKham.setOnClickListener(v -> {
            String benh = edChanDoan.getText().toString().trim();
            String chiTiet = edChiTiet.getText().toString().trim();
            databaseReference = FirebaseDatabase.getInstance().getReference("History").child(idPhieuKham);
            databaseReference.child("benh").setValue(benh);
            databaseReference.child("note").setValue(chiTiet);
            databaseReference.child("status").setValue("Hoàn thành");
            getContext().getSharedPreferences("BACSI", Context.MODE_PRIVATE).edit().putBoolean("DANGKHAM", false).commit();
            getFragmentManager().beginTransaction().add(R.id.fragment_container, new BsLichKhamFragment()).remove(this).commit();
        });
    }

    private void getDataFromDb() {
        databaseReference = FirebaseDatabase.getInstance().getReference("History");
        databaseReference.orderByChild("id");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    if(ds.getKey().equalsIgnoreCase(idPhieuKham)){
                        PhieuKham obj = ds.getValue(PhieuKham.class);
                        tvTenBn.setText(obj.getTenBn());
                        tvMaPhieuKham.setText(obj.getId());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void mapping(View view) {
        edChanDoan = view.findViewById(R.id.edChanDoan);
        edChiTiet = view.findViewById(R.id.edChiTiet);
        tvMaPhieuKham = view.findViewById(R.id.tvMaPhieuKham_dangKham);
        tvTenBn = view.findViewById(R.id.tvTenBn_dangKham);
        btnHoanThanhKham = view.findViewById(R.id.btnHoanThanhKham);
    }
}