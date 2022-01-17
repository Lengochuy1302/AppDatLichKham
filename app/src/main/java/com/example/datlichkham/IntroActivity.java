package com.example.datlichkham;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.datlichkham.adapter.IntroAdapter;
import com.example.datlichkham.model.IntroItem;

import java.util.ArrayList;
import java.util.List;

public class IntroActivity extends AppCompatActivity {
    private IntroAdapter introAdapter;
    private LinearLayout circleLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        getSupportActionBar().hide();
        ViewPager2 introViewpager = findViewById(R.id.intro_viewpager);
        circleLayout = findViewById(R.id.intro_circleLayout);
        /*
        * Khởi tạo introAdapter
        * thêm data cho adapter
        * gắn adapter cho viewpager2
        * */
        setupItem();
        introViewpager.setAdapter(introAdapter);
        /*Vẽ circle view cho linearLayout
        *set active/inactive circle view
        * */
        setupCircle();
        setActiveCircle(0);
        introViewpager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                setActiveCircle(position);
            }
        });

        findViewById(R.id.intro_btnDangNhap).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(IntroActivity.this, DangNhapActivity.class));
                finish();
            }
        });
    }

    private void setupItem(){
        List<IntroItem> introItems = new ArrayList<>();

        IntroItem item1 = new IntroItem();
        item1.setImageView(R.drawable.intro1);
        item1.setTextView("Sử dụng APP Đặt hẹn khám BV, " +
                "người bệnh có thể đăng ký khám tức thời, " +
                "tiết kiệm thời gian, chủ động trong thời gian cần khám.");
        IntroItem item2 = new IntroItem();
        item2.setImageView(R.drawable.intro2);
        item2.setTextView("Người bệnh nhận được các thông báo nhắc hẹn từ bệnh viện như ngày khám, giờ khám, phòng khám, số thứ tự đăng ký" +
                "và nhận các thông báo về các chương trình khám chữa bệnh");
        IntroItem item3 = new IntroItem();
        item3.setImageView(R.drawable.intro3);
        item3.setTextView("Lưu trữ, tra cứu thông tin các lần khám chữa bệnh");

        introItems.add(item1);
        introItems.add(item2);
        introItems.add(item3);
        introAdapter = new IntroAdapter(introItems);
    }

    private void setupCircle(){
        ImageView [] circles = new ImageView[introAdapter.getItemCount()];
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(8, 0, 8, 0);
        for(int i = 0; i < circles.length; i++){
            circles[i] = new ImageView(getApplicationContext());
            circles[i].setImageDrawable(ContextCompat.getDrawable(
                    getApplicationContext(), R.drawable.circle_inactive
            ));
            circles[i].setLayoutParams(params);
            circleLayout.addView(circles[i]);
        }
    }

    private void setActiveCircle(int index){
        int childCount = circleLayout.getChildCount();
        for(int i = 0; i < childCount; i++){
            ImageView circleView = (ImageView) circleLayout.getChildAt(i);
            if(i == index){
                circleView.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.circle_active));
            } else {
                circleView.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.circle_inactive));
            }
        }
    }
}