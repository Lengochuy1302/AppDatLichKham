package com.example.datlichkham.fragment.nguoidung;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.datlichkham.DangNhapActivity;
import com.example.datlichkham.R;
import com.example.datlichkham.adapter.SettingAdapter;
import com.example.datlichkham.model.Setting;

import java.util.ArrayList;
import java.util.List;


public class NdSettingFragment extends Fragment {
    private TextView tvIdTaiKhoan;
    private ListView lvSetting;
    List<Setting> mLists;
    private Context context;

    public NdSettingFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    public static NdSettingFragment newInstance() {
        NdSettingFragment fragment = new NdSettingFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nd_setting, container, false);
        mapping(view);
        addSettingItem();
        setDataAdapter();
        return view;
    }

    private void setDataAdapter() {
        SettingAdapter settingAdapter = new SettingAdapter(context, mLists);
        lvSetting.setDivider(null);
        lvSetting.setAdapter(settingAdapter);
        lvSetting.setOnItemClickListener((parent, view, position, id) -> {
            switch (position){
                case 7:
                    context.getSharedPreferences("PREFS", Context.MODE_PRIVATE).edit().clear().commit();
                    context.startActivity(new Intent(context, DangNhapActivity.class));
                    getActivity().finish();
                    break;
                default:
                    break;
            }
        });
    }

    private void addSettingItem() {
        mLists = new ArrayList<>();

        Setting obj = new Setting();
        obj.setImage(R.drawable.ic_user_setting);
        obj.setTitle("Tài Khoản");
        mLists.add(obj);

        Setting obj2 = new Setting();
        obj2.setImage(R.drawable.ic_heart_setting);
        obj2.setTitle("Thêm sổ bảo hiểm");
        mLists.add(obj2);

        Setting obj3 = new Setting();
        obj3.setImage(R.drawable.ic_boarding_pass_setting);
        obj3.setTitle("Điều khoản dịch vụ");
        mLists.add(obj3);

        Setting obj4 = new Setting();
        obj4.setImage(R.drawable.ic_error_setting);
        obj4.setTitle("Chính sách bảo mật");
        mLists.add(obj4);

        Setting obj5 = new Setting();
        obj5.setImage(R.drawable.ic_marker_pen_setting);
        obj5.setTitle("Quy định sử dụng");
        mLists.add(obj5);

        Setting obj6 = new Setting();
        obj6.setImage(R.drawable.ic_ask_question_setting);
        obj6.setTitle("Các vấn đề thường gặp");
        mLists.add(obj6);

        Setting obj9 = new Setting();
        obj9.setImage(R.drawable.ic_3d_touch_setting);
        obj9.setTitle("Đổi mật khẩu");
        mLists.add(obj9);

        Setting obj7 = new Setting();
        obj7.setImage(R.drawable.ic_export_setting);
        obj7.setTitle("Đăng xuất");
        mLists.add(obj7);

        Setting obj8 = new Setting();
        obj8.setImage(R.drawable.ic_merge_git_setting);
        obj8.setTitle("Phiên bản ứng dụng-v1.1.8");
        mLists.add(obj8);

    }

    private void mapping(View view) {
        tvIdTaiKhoan = view.findViewById(R.id.tv_taiKhoan_setting);
        lvSetting = view.findViewById(R.id.lv_setting);

        tvIdTaiKhoan.setText("Tài khoản: "+context.getSharedPreferences("PREFS", Context.MODE_PRIVATE).getString("USERNAME", ""));
    }
}