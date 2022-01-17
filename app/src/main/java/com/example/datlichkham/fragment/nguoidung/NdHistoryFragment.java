package com.example.datlichkham.fragment.nguoidung;

import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.datlichkham.R;
import com.example.datlichkham.adapter.LichSuNDAdapter;
import com.example.datlichkham.model.PhieuKham;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class NdHistoryFragment extends Fragment {
    private EditText edFirstDate, edSecondDate;
    private DatePickerDialog.OnDateSetListener onDateSetListener;
    private RecyclerView rcHistory;
    private List<PhieuKham> phieuKhamList;

    public static final String formatTime = "HH:mm";
    public static final String formatDate = "dd/MM/yyyy";



    public NdHistoryFragment() {
        // Required empty public constructor
    }

    public static NdHistoryFragment newInstance() {
        NdHistoryFragment fragment = new NdHistoryFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nd_history, container, false);
        edFirstDate = view.findViewById(R.id.edFirstDate);
        edSecondDate = view.findViewById(R.id.edSecondDate);
        rcHistory = view.findViewById(R.id.rcLichSuKhamNd);
        rcHistory.setLayoutManager(new LinearLayoutManager(getContext()));

        edFirstDate.setOnClickListener(v -> {

            onDateSetListener = new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    month = month+1;
                    String date = dayOfMonth + "/" + month + "/" + year;
                    edFirstDate.setText(date);
                    getLichSu();
                }
            };
            chooseDate();

        });
        edSecondDate.setOnClickListener(v -> {
            onDateSetListener = new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    month = month+1;
                    String date = dayOfMonth + "/" + month + "/" + year;
                    edSecondDate.setText(date);
                    getLichSu();
                }
            };
            chooseDate();
        });
        return view;
    }

    private void getLichSu() {
        if(edFirstDate.getText().toString().isEmpty() || edSecondDate.getText().toString().isEmpty()){
            Toast.makeText(getContext(), "Mốc thời gian bị trống!", Toast.LENGTH_SHORT).show();
        } else if(parseDate(edFirstDate.getText().toString().trim()).after(parseDate(edSecondDate.getText().toString().trim()))){
            Toast.makeText(getContext(), "Thời gian trước phải bé hơn thời gian sau", Toast.LENGTH_SHORT).show();
        } else {
            getHistoryFromDb();
        }
    }

    private void getHistoryFromDb() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("History");
        databaseReference.orderByChild("date");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try {
                    String idNd = getContext().getSharedPreferences("PREFS", Context.MODE_PRIVATE).getString("USERNAME", "none");
                    String level = getContext().getSharedPreferences("PREFS", Context.MODE_PRIVATE).getString("LEVEL", "none");
                    phieuKhamList = new ArrayList<>();
                    for(DataSnapshot ds: dataSnapshot.getChildren()){
//                    && ds.child("status").getValue(String.class).equalsIgnoreCase("Hoàn thành")
                        if(level.equalsIgnoreCase("Bệnh Nhân")){
                            if(ds.child("idBn").getValue(String.class).equalsIgnoreCase(idNd) && compareDate(parseDate(ds.child("date").getValue(String.class)))){
                                PhieuKham obj = ds.getValue(PhieuKham.class);
                                phieuKhamList.add(obj);
                                Toast.makeText(getContext(), "found! " + obj.getDate() + " " + obj.getNote(), Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            if(ds.child("idBs").getValue(String.class).equalsIgnoreCase(idNd) && compareDate(parseDate(ds.child("date").getValue(String.class)))){
                                PhieuKham obj = ds.getValue(PhieuKham.class);
                                phieuKhamList.add(obj);
                                Toast.makeText(getContext(), "found! " + obj.getDate() + " " + obj.getNote(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                    LichSuNDAdapter lichSuNDAdapter = new LichSuNDAdapter(getContext(), phieuKhamList);
                    rcHistory.setAdapter(lichSuNDAdapter);
                } catch (NullPointerException e){
                    Log.e("===//", ""+e);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private Boolean compareDate(Date myDate){
        Date beforeDate = parseDate(edFirstDate.getText().toString().trim());
        Date afterDate = parseDate(edSecondDate.getText().toString().trim());

        if(beforeDate.compareTo(myDate) == 0 || beforeDate.before(myDate) && afterDate.after(myDate) || afterDate.compareTo(myDate) == 0){
            return true;
        }
        else return false;
    }

    private Date parseDate(String date){
        SimpleDateFormat inputParser = new SimpleDateFormat(formatDate);
        try {
            return inputParser.parse(date);
        } catch (ParseException e){
            return new Date(0);
        }
    }

    private void chooseDate() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(
                getActivity(),
                android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                onDateSetListener,
                year, month, day);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

    }

}