package com.example.datlichkham;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DangNhapActivity extends AppCompatActivity {

    private SharedPreferences prefs;
    private EditText edUsername, edPass;

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
        setContentView(R.layout.activity_dang_nhap);
        getSupportActionBar().hide();

        mappingView();
        prefs = getSharedPreferences("PREFS", MODE_PRIVATE);
        //isFirstLogin = true thì show màn hình giới thiệu
        Boolean isFirstLogin = prefs.getBoolean("ISFIRST", true);
        if(isFirstLogin) {
            prefs.edit().putBoolean("ISFIRST", false).apply();
            startActivity(new Intent(DangNhapActivity.this, IntroActivity.class));
            finish();
        }
        if(prefs.getBoolean("REMEMBERLOGIN", false)){
            Intent dangNhapIntent = new Intent(DangNhapActivity.this, MainActivity.class);
            startActivity(dangNhapIntent);
            finish();
        }
        setupListener();
    }

    private void mappingView() {
        edUsername = findViewById(R.id.dangNhap_edusername);
        edPass = findViewById(R.id.dangNhap_edpass);
    }

    private void setupListener(){
        findViewById(R.id.dangNhap_ivPhone).setOnClickListener(v -> {
            startActivity(new Intent(DangNhapActivity.this, DangKiActivity.class));
        });

        findViewById(R.id.dangNhap_btnDangnhap).setOnClickListener(v -> {
            String userNameInput = edUsername.getText().toString().trim();
            String passwordInput = edPass.getText().toString().trim();

            Boolean checkError = true;
            if(userNameInput.isEmpty()){
                edUsername.setError("Nhập tên đăng nhập");
                checkError = false;
            }
            if(passwordInput.isEmpty()){
                edPass.setError("Nhập mật khẩu");
                checkError = false;
            }
            if(passwordInput.length()<6){
                edPass.setError("Mật khẩu ít nhất 6 kí tự");
                checkError = false;
            }
            if(checkError) {
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("users");
                Query checkUser = ref.orderByChild("userName").equalTo(userNameInput);
                checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()){
                            String passwordFromDB = dataSnapshot.child(userNameInput).child("password").getValue(String.class);

                            if(passwordFromDB.equals(passwordInput)){
                                String fullNameFromDB = dataSnapshot.child(userNameInput).child("fullName").getValue(String.class);
                                String levelFromDB = dataSnapshot.child(userNameInput).child("level").getValue(String.class);
                                String userNameFromDB = dataSnapshot.child(userNameInput).child("userName").getValue(String.class);
                                prefs.edit().putString(USERNAME, userNameFromDB).commit();
                                prefs.edit().putString(FULLNAME, fullNameFromDB).commit();
                                prefs.edit().putString(LEVEL, levelFromDB).commit();
                                prefs.edit().putBoolean("REMEMBERLOGIN", true).commit();
                                Intent dangNhapIntent = new Intent(DangNhapActivity.this, MainActivity.class);
                                startActivity(dangNhapIntent);
                                finish();
                            } else {
                                Toast.makeText(DangNhapActivity.this, "Sai tên đăng nhập hoặc mật khẩu", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }


        });
    }

//    private void saveDataUser(String ageFromDB, String birthdayFromDB, String emailFromDB, String fullNameFromDB, String levelFromDB, String phoneFromDB, String userNameFromDB, String passwordFromDB) {
//        prefs.edit().putString(AGE, ageFromDB).commit();
//        prefs.edit().putString(BIRTHDAY, birthdayFromDB).commit();
//        prefs.edit().putString(EMAIL, emailFromDB).commit();
//        prefs.edit().putString(FULLNAME, fullNameFromDB).commit();
//        prefs.edit().putString(LEVEL, levelFromDB).commit();
//        prefs.edit().putString(PHONE, phoneFromDB).commit();
//        prefs.edit().putString(USERNAME, userNameFromDB).commit();
//        prefs.edit().putString(PASSWORD, passwordFromDB).commit();
//    }

}