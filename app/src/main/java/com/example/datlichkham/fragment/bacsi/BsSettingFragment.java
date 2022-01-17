package com.example.datlichkham.fragment.bacsi;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.datlichkham.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BsSettingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BsSettingFragment extends Fragment {


    public BsSettingFragment() {
        // Required empty public constructor
    }


    public static BsSettingFragment newInstance() {
        BsSettingFragment fragment = new BsSettingFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bs_setting, container, false);
    }
}