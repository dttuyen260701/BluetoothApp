package com.example.bluetoothapp.fragments;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bluetoothapp.Models.Garbage_Can;
import com.example.bluetoothapp.R;
import com.example.bluetoothapp.Utils.Constant_Values;

public class Fragment_Weight extends Fragment {
    private Garbage_Can garbage_can;
    private Button btn1, btn10, btn100, btn1000,
            btnDiv, btnAdd, btnSend;
    private TextView txtValue_Weght_Frag;
    private EditText txtStep, txtScale_value;
    private RecyclerView rcl_Weight_Frag;
    private ImageView img_Descrip_Weight_Frag;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weight, container, false);
        this.garbage_can = Constant_Values.garbage_can;
        SetUp(view);
        // Inflate the layout for this fragment
        return view;
    }

    private void SetUp(View view){
        btn1 = (Button) view.findViewById(R.id.btn1);
        btn10 = (Button) view.findViewById(R.id.btn10);
        btn100 = (Button) view.findViewById(R.id.btn100);
        btn1000 = (Button) view.findViewById(R.id.btn1000);
        btnDiv = (Button) view.findViewById(R.id.btnDiv);
        btnAdd = (Button) view.findViewById(R.id.btnAdd);
        btnSend = (Button) view.findViewById(R.id.btnSend);
        txtValue_Weght_Frag = (TextView) view.findViewById(R.id.txtValue_Weght_Frag);
        txtStep = (EditText) view.findViewById(R.id.txtStep);
        txtScale_value = (EditText) view.findViewById(R.id.txtScale_value);
        rcl_Weight_Frag = (RecyclerView) view.findViewById(R.id.rcl_Weight_Frag);
        img_Descrip_Weight_Frag= (ImageView) view.findViewById(R.id.img_Descrip_Weight_Frag);
        updateView();

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onHandle(btn1);
            }
        });

        btn10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onHandle(btn10);
            }
        });

        btn100.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onHandle(btn100);
            }
        });

        btn1000.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onHandle(btn1000);
            }
        });
    }

    private void onHandle(Button btn){
        txtStep.setText(btn.getText().toString());
    }

    public void updateView(){

    }
}
