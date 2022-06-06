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

import com.example.bluetoothapp.Models.Garbage_Can;
import com.example.bluetoothapp.R;
import com.example.bluetoothapp.Utils.Constant_Values;

public class Fragment_Weight extends Fragment {
    Garbage_Can garbage_can;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weight, container, false);
        SetUp(view);
        // Inflate the layout for this fragment
        return view;
    }

    private void SetUp(View view){

        updateView();


    }

    public void updateView(){

    }
}
