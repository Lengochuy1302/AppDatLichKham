package com.example.datlichkham;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import com.example.datlichkham.fragment.bacsi.BsChatFragment;
import com.example.datlichkham.fragment.bacsi.BsHistoryFragment;
import com.example.datlichkham.fragment.bacsi.BsHomeFragment;
import com.example.datlichkham.fragment.bacsi.BsLichKhamFragment;
import com.example.datlichkham.fragment.bacsi.BsSettingFragment;
import com.example.datlichkham.fragment.nguoidung.NdChatFragment;
import com.example.datlichkham.fragment.nguoidung.NdDatLichFragment;
import com.example.datlichkham.fragment.nguoidung.NdHistoryFragment;
import com.example.datlichkham.fragment.nguoidung.NdHomeFragment;
import com.example.datlichkham.fragment.nguoidung.NdSettingFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    private SharedPreferences prefs;
    public static final String AGE = "AGE";
    public static final String BIRTHDAY = "BIRTHDAY";
    public static final String EMAIL = "EMAIL";
    public static final String FULLNAME = "FULLNAME";
    public static final String LEVEL = "LEVEL";
    public static final String PHONE = "PHONE";
    public static final String USERNAME = "USERNAME";
    public static final String PASSWORD = "PASSWORD";

    private BottomNavigationView bnv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        mappingView();
        prefs = getSharedPreferences("PREFS", MODE_PRIVATE);
        String level = prefs.getString(LEVEL, "");
        String checkNameIsBlank = prefs.getString(FULLNAME, "");
//        String userName = prefs.getString(USERNAME, "");

        if(checkNameIsBlank.isEmpty()){
            Toast.makeText(this, "ten trong", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, CapNhatThongTinActivity.class));
            finish();
        }

//        Bundle b = new Bundle();
//        b.putString(USERNAME, userName);
        if(level.equals("Bệnh Nhân")){
            addViewUsers();
        } else {
            addViewDoctors();
        }
    }

    private void addViewDoctors() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new BsHomeFragment()).commit();

        bnv.setOnNavigationItemSelectedListener(item -> {
            Fragment currentFragment = null;
            switch (item.getItemId()){
                case R.id.menu_home:
                    currentFragment = new BsHomeFragment();
                    break;
                case R.id.menu_history:
                    currentFragment = new NdHistoryFragment();
                    break;
                case R.id.menu_datLich:
                    currentFragment = new BsLichKhamFragment();
                    break;
                case R.id.menu_chat:
                    currentFragment = new NdChatFragment();
                    break;
                case R.id.menu_setting:
                    currentFragment = new NdSettingFragment();
                    break;
            }
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, currentFragment).commit();
            return true;
        });
    }

    private void addViewUsers() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new NdHomeFragment()).commit();

        bnv.setOnNavigationItemSelectedListener(item -> {
            Fragment currentFragment = null;
            switch (item.getItemId()){
                case R.id.menu_home:
                    currentFragment = new NdHomeFragment();
                    break;
                case R.id.menu_history:
                    currentFragment = new NdHistoryFragment();
                    break;
                case R.id.menu_datLich:
                    currentFragment = new NdDatLichFragment();
                    break;
                case R.id.menu_chat:
                    currentFragment = new NdChatFragment();
                    break;
                case R.id.menu_setting:
                    currentFragment = new NdSettingFragment();
                    break;
            }

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, currentFragment).commit();
            return true;
        });
    }

    private void mappingView() {
        bnv = findViewById(R.id.bottom_nav);
    }
}