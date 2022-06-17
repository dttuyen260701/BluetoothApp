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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bluetoothapp.Activity.MainActivity;
import com.example.bluetoothapp.Models.Garbage_Can;
import com.example.bluetoothapp.R;
import com.example.bluetoothapp.Utils.Constant_Values;

import java.util.ArrayList;

public class Fragment_Weight extends Fragment {
    private Button btn1, btn10, btn100, btn1000,
            btnDiv, btnAdd, btnSend;
    private TextView txtValue_Weght_Frag;
    private EditText txtStep, txtScale_value;
    private ListView rcl_Weight_Frag;
    private ImageView img_Descrip_Weight_Frag;
    private ArrayList<String> list_arr;
    private ArrayAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weight, container, false);
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
        rcl_Weight_Frag = (ListView) view.findViewById(R.id.rcl_Weight_Frag);
        img_Descrip_Weight_Frag= (ImageView) view.findViewById(R.id.img_Descrip_Weight_Frag);
        list_arr = new ArrayList<>();

        img_Descrip_Weight_Frag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment_HTU_Dialog fragment_htu_dialog = new Fragment_HTU_Dialog(
                        Constant_Values.HTU_Weight
                );
                fragment_htu_dialog.show(getActivity().getSupportFragmentManager(), "dialog");
            }
        });

        adapter = new ArrayAdapter(
                getActivity(), android.R.layout.simple_list_item_1, list_arr
        );
        rcl_Weight_Frag.setAdapter(adapter);
        updateView("");
        list_arr.clear();
        adapter.notifyDataSetChanged();

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

        btnDiv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    float k = 0f;
                    k = Float.valueOf(txtStep.getText().toString().trim());
                    float scale = Float.parseFloat(
                            txtValue_Weght_Frag.getText().toString().trim().split(":")[1]) +k;
                    MainActivity.sendMsg("s:" + scale);
                } catch (Exception e ){
                    Toast.makeText(getContext(), "Vui lòng nhập đúng định dạng số", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnDiv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    float k = 0f;
                    k = Float.valueOf(txtStep.getText().toString().trim());
                    float scale = Float.parseFloat(
                            txtValue_Weght_Frag.getText().toString().trim().split(":")[1]) -k;
                    MainActivity.sendMsg("s:" + scale);
                } catch (Exception e ){
                    Toast.makeText(getContext(), "Vui lòng nhập đúng định dạng số", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    float k = 0f;
                    k = Float.valueOf(txtScale_value.getText().toString());
                    MainActivity.sendMsg("s:" + k);
                } catch (Exception e ){
                    Toast.makeText(getContext(), "Vui lòng nhập đúng định dạng số", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void onHandle(Button btn){
        txtStep.setText(btn.getText().toString());
    }

    public void updateView(String add){
        txtValue_Weght_Frag.setText("Scale: " + Constant_Values.garbage_can.getWeight_difference());
        if(list_arr.size() > 100)
            list_arr.clear();
        list_arr.add(add);
        adapter.notifyDataSetChanged();
        rcl_Weight_Frag.setSelection(list_arr.size()-1);
    }
}
