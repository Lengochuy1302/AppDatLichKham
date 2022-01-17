package com.example.datlichkham;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Pattern;

public class CapNhatThongTinActivity extends AppCompatActivity {
    private TextInputLayout tilUsername, tilHoTen, tilMail, tilBirthday, tilAge, tilPhone;
    private SharedPreferences prefs;
    private DatabaseReference reference;

    public static final String AGE = "AGE";
    public static final String BIRTHDAY = "BIRTHDAY";
    public static final String EMAIL = "EMAIL";
    public static final String FULLNAME = "FULLNAME";
    public static final String LEVEL = "LEVEL";
    public static final String PHONE = "PHONE";
    public static final String USERNAME = "USERNAME";
    public static final String PASSWORD = "PASSWORD";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cap_nhat_thong_tin);
        getSupportActionBar().setTitle("Cập nhật thông tin");
        mappingView();
        prefs = getSharedPreferences("PREFS", MODE_PRIVATE);
        String getUsername = prefs.getString(USERNAME, "");
        reference = FirebaseDatabase.getInstance().getReference("users").child(getUsername);
        showAllUserData(getUsername);
        updateInfor();
    }

    private void showAllUserData(String username) {
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String ageFromDB = dataSnapshot.child("age").getValue(String.class);
                String birthdayFromDB = dataSnapshot.child("birthday").getValue(String.class);
                String emailFromDB = dataSnapshot.child("email").getValue(String.class);
                String fullNameFromDB = dataSnapshot.child("fullName").getValue(String.class);
                String phoneFromDB = dataSnapshot.child("phone").getValue(String.class);

                tilAge.getEditText().setText(ageFromDB);
                tilBirthday.getEditText().setText(birthdayFromDB);
                tilMail.getEditText().setText(emailFromDB);
                tilHoTen.getEditText().setText(fullNameFromDB);
                tilPhone.getEditText().setText(phoneFromDB);
                tilUsername.getEditText().setText(username);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }

    private void updateInfor() {
        findViewById(R.id.btn_ok_capNhatActivity).setOnClickListener(v -> {
            Boolean checkError = true;
            if(tilHoTen.getEditText().getText().toString().trim().isEmpty()){
                tilHoTen.setError("Tên không được để trống");
                checkError = false;
            }

            if(tilMail.getEditText().getText().toString().trim().isEmpty()){
                tilMail.setError("Email không được để trống");
                checkError = false;
            }

            if(!Pattern.matches("^[a-zA-Z][\\w-]+@([\\w]+\\.[\\w]+|[\\w]+\\.[\\w]{2,}\\.[\\w]{2,})$", tilMail.getEditText().getText().toString().trim())){
                tilMail.setError("Email sai định dạng");
                checkError = false;
            }

            if(checkError){
                reference.child("age").setValue(tilAge.getEditText().getText().toString().trim());
                reference.child("birthday").setValue(tilBirthday.getEditText().getText().toString().trim());
                reference.child("email").setValue(tilMail.getEditText().getText().toString().trim());
                reference.child("fullName").setValue(tilHoTen.getEditText().getText().toString().trim());
                reference.child("phone").setValue(tilPhone.getEditText().getText().toString().trim());
                prefs.edit().putString(FULLNAME, tilHoTen.getEditText().getText().toString().trim()).commit();
                startActivity(new Intent(CapNhatThongTinActivity.this, MainActivity.class));
                finish();
            }
        });
    }

    private void mappingView() {
        tilUsername = findViewById(R.id.til_username_capNhatActivity);
        tilHoTen = findViewById(R.id.til_hoTen_capNhatActivity);
        tilMail = findViewById(R.id.til_mail_capNhatActivity);
        tilBirthday = findViewById(R.id.til_birtday_capNhatActivity);
        tilAge = findViewById(R.id.til_age_capNhatActivity);
        tilPhone = findViewById(R.id.til_phone_capNhatActivity);
    }
}