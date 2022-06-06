package com.example.bluetoothapp.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.fragment.app.Fragment;

import com.example.bluetoothapp.Models.Garbage_Can;
import com.example.bluetoothapp.R;
import com.example.bluetoothapp.Utils.Constant_Values;

public class Fragment_Control extends Fragment {
    private TextView txtPower_Control_Frag;
    private ImageButton btn_down_left, btn_down_right,
            btnMode_control_Frag;
    private ImageView img_Descrip_Control_Frag, btn_servor;
    private VideoView video_Preview_Control;
    private Garbage_Can garbage_can;
    private long mLastClick_Mode = 0, mLastClick_Sweep = 0,
            mLastClick_LRDoor = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_control, container, false);
        this.garbage_can = Constant_Values.garbage_can;
        SetUp(view);
        // Inflate the layout for this fragment
        return view;
    }

    private void SetUp(View view){
        btn_down_left = (ImageButton) view.findViewById(R.id.btn_down_left);
        btn_down_right = (ImageButton) view.findViewById(R.id.btn_down_right);
        btnMode_control_Frag = (ImageButton) view.findViewById(R.id.btnMode_control_Frag);
        img_Descrip_Control_Frag = (ImageView) view.findViewById(R.id.img_Descrip_Control_Frag);
        btn_servor = (ImageView) view.findViewById(R.id.btn_servor);
        video_Preview_Control = (VideoView) view.findViewById(R.id.video_Preview_Control);

        if(garbage_can.isMode()){
            isAuto();
        } else {
            isControl();
        }

        img_Descrip_Control_Frag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment_HTU_Dialog fragment_htu_dialog = new Fragment_HTU_Dialog(
                        Constant_Values.HTU_Control
                );
                fragment_htu_dialog.show(getActivity().getSupportFragmentManager(), "dialog");
            }
        });

        btn_down_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (SystemClock.elapsedRealtime() - mLastClick_LRDoor < 4000){
                    return;
                }
                mLastClick_LRDoor = SystemClock.elapsedRealtime();
                String path = "android.resource://" + getActivity().getPackageName() + "/" + R.raw.tournado_rev;
                video_Preview_Control.setVideoURI(Uri.parse(path));
                video_Preview_Control.start();
            }
        });

        btn_down_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (SystemClock.elapsedRealtime() - mLastClick_LRDoor < 4000){
                    return;
                }
                mLastClick_LRDoor = SystemClock.elapsedRealtime();
                String path = "android.resource://" + getActivity().getPackageName() + "/" + R.raw.wind_mill;
                video_Preview_Control.setVideoURI(Uri.parse(path));
                video_Preview_Control.start();
            }
        });

        btn_servor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (SystemClock.elapsedRealtime() - mLastClick_Sweep < 4000){
                    return;
                }
                mLastClick_Sweep = SystemClock.elapsedRealtime();
                String path = "android.resource://" + getActivity().getPackageName() + "/" + R.raw.vertical_rect;
                video_Preview_Control.setVideoURI(Uri.parse(path));
                video_Preview_Control.start();
            }
        });

        btnMode_control_Frag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (SystemClock.elapsedRealtime() - mLastClick_Mode < 4000){
                    return;
                }
                garbage_can.setMode(!garbage_can.isMode());
                if(garbage_can.isMode()){
                    isAuto();
                } else {
                    isControl();
                }
                mLastClick_Mode = SystemClock.elapsedRealtime();
                String path = "android.resource://" + getActivity().getPackageName() + "/" + R.raw.vertical_rect;
                video_Preview_Control.setVideoURI(Uri.parse(path));
                video_Preview_Control.start();
            }
        });
    }

    private void isAuto(){
        //dong cua k nhan rac
        btnMode_control_Frag.setImageResource(R.drawable.power_on);
        btn_down_left.setEnabled(false);
        btn_down_right.setEnabled(false);
        btn_servor.setEnabled(false);
    }

    private void isControl(){
        //mo cua thung rac
        btnMode_control_Frag.setImageResource(R.drawable.power_off);
        btn_servor.setEnabled(true);
        btn_down_left.setEnabled(true);
        btn_down_right.setEnabled(true);
    }
}
