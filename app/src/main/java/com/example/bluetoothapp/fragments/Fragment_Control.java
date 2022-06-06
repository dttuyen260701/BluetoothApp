package com.example.bluetoothapp.fragments;

import android.os.Bundle;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.bluetoothapp.Models.Garbage_Can;
import com.example.bluetoothapp.R;
import com.example.bluetoothapp.Utils.Constant_Values;

public class Fragment_Control extends Fragment {
    private TextView txtPower_Control_Frag;
    private ImageButton btn_Compartment, btn_Inside_door,
            btnPower_Ctrl_Frag;
    private ImageView img_Descrip_Control_Frag;
    private Garbage_Can garbage_can;
    private long mLastClick_Power = 0, mLastClick_Close = 0,
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
        btn_Compartment = (ImageButton) view.findViewById(R.id.btn_down_left);
        btn_Inside_door = (ImageButton) view.findViewById(R.id.btn_down_right);
        btnPower_Ctrl_Frag = (ImageButton) view.findViewById(R.id.btnPower_Ctrl_Frag);
        img_Descrip_Control_Frag = (ImageView) view.findViewById(R.id.img_Descrip_Control_Frag);
        updateView();

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

        btn_Compartment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (SystemClock.elapsedRealtime() - mLastClick_Close < 4000){
                    return;
                }

            }
        });

        btn_Inside_door.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (SystemClock.elapsedRealtime() - mLastClick_LRDoor < 4000){
                    return;
                }
                mLastClick_LRDoor = SystemClock.elapsedRealtime();

            }
        });

        btnPower_Ctrl_Frag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (SystemClock.elapsedRealtime() - mLastClick_Power < 4000){
                    return;
                }
                mLastClick_Power = SystemClock.elapsedRealtime();

            }
        });
    }

    private void isControl(){
        //dong cua k nhan rac
        btnPower_Ctrl_Frag.setImageResource(R.drawable.power_on);
        btn_Compartment.setEnabled(false);
        btn_Inside_door.setEnabled(false);
    }

    private void isAuto(){
        //mo cua thung rac
        btnPower_Ctrl_Frag.setImageResource(R.drawable.power_off);
        btn_Compartment.setEnabled(true);
        btn_Inside_door.setEnabled(true);
    }


    public void updateView(){

    }
}
