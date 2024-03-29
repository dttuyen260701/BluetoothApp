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

import com.example.bluetoothapp.Activity.MainActivity;
import com.example.bluetoothapp.Models.Garbage_Can;
import com.example.bluetoothapp.R;
import com.example.bluetoothapp.Utils.Constant_Values;

public class Fragment_Status extends Fragment {
    private Button btnchangeThread_Status_Frag, btnReload_Status_Frag;
    private ImageView img_Descrip_Status_Frag, img_NonRecycle_Status_frag,
            img_Recycle_Status_frag;
    private TextView txtPer_NonRecycle_Status_Frag, txtPer_Recycle_Status_Frag,
            txtThreadvalue_Frag, txtValue, txtMode;
    private MainActivity mainActivity;
    private long mLastClick_Reload = 0, mLastClick_TakePhoto = 0;

    public Fragment_Status(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_status, container, false);
        SetUp(view);
        // Inflate the layout for this fragment
        return view;
    }

    private void SetUp(View view){
        btnchangeThread_Status_Frag = (Button) view.findViewById(R.id.btnchangeThread_Status_Frag);
        img_Descrip_Status_Frag = (ImageView) view.findViewById(R.id.img_Descrip_Status_Frag);
        img_NonRecycle_Status_frag = (ImageView) view.findViewById(R.id.img_NonRecycle_Status_frag);
        img_Recycle_Status_frag = (ImageView) view.findViewById(R.id.img_Recycle_Status_frag);
        txtPer_NonRecycle_Status_Frag = (TextView) view.findViewById(R.id.txtPer_NonRecycle_Status_Frag);
        txtPer_Recycle_Status_Frag = (TextView) view.findViewById(R.id.txtPer_Recycle_Status_Frag);
        txtThreadvalue_Frag = (TextView) view.findViewById(R.id.txtThreadvalue_Frag);
        txtValue = (TextView) view.findViewById(R.id.txtValue);
        txtMode = (TextView) view.findViewById(R.id.txtMode);
        btnReload_Status_Frag = (Button) view.findViewById(R.id.btnReload_Status_Frag);

        updateView();

        btnReload_Status_Frag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivity.clickConnect();
                String connect = (MainActivity.getBtConnected()) ? "Disconect" : "Connect";
                btnReload_Status_Frag.setText(connect);
            }
        });

        img_Descrip_Status_Frag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment_HTU_Dialog fragment_htu_dialog = new Fragment_HTU_Dialog(
                        Constant_Values.HTU_Status
                );
                fragment_htu_dialog.show(getActivity().getSupportFragmentManager(), "dialog");
            }
        });

        btnchangeThread_Status_Frag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (SystemClock.elapsedRealtime() - mLastClick_TakePhoto < 2000){
                    return;
                }
                Dialog dialog = new Dialog(getContext());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.fragment_dialog_image);
                Window window = dialog.getWindow();
                if(window == null){
                    return;
                }
                window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
                window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                WindowManager.LayoutParams windowAttributes = window.getAttributes();
                windowAttributes.gravity = Gravity.CENTER;
                dialog.setCancelable(true);

                EditText txt_thread =dialog.findViewById(R.id.txt_thread);
                Button btn_cancel_dialog = dialog.findViewById(R.id.btn_cancel_dialog);
                btn_cancel_dialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

                Button btn_change_dialog = dialog.findViewById(R.id.btn_change_dialog);
                btn_change_dialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            float k = 0f;
                            k = Float.parseFloat(txt_thread.getText().toString());
                            MainActivity.sendMsg("t:"+ k);
                            dialog.dismiss();
                        } catch (Exception e){
                            Toast.makeText(getContext(), "Vui lòng nhập đúng định dạng số", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                dialog.show();
            }
        });
    }

    public void updateView(){
        txtThreadvalue_Frag.setText(" " + Constant_Values.garbage_can.getThread());
        float per_Recycle = Constant_Values.garbage_can.getVolume_recycle()/ Constant_Values.Volume_Machine,
                per_NonRecycle = Constant_Values.garbage_can.getVolume_nonRecycle()/Constant_Values.Volume_Machine;
        img_NonRecycle_Status_frag.setImageLevel((int) (10000*per_NonRecycle));
        img_Recycle_Status_frag.setImageLevel((int) (10000*per_Recycle));
        String connect = (MainActivity.getBtConnected()) ? "Disconect" : "Connect";
        btnReload_Status_Frag.setText(connect);
        per_NonRecycle = ((float) Math.round(per_NonRecycle*10000)/100);
        per_Recycle = ((float) Math.round(per_Recycle*10000)/100);
        txtMode.setText("Mode: " + (Constant_Values.garbage_can.isMode() ? "Control" : "Auto"));
        txtPer_NonRecycle_Status_Frag.setText(per_NonRecycle + "%");
        txtPer_Recycle_Status_Frag.setText(per_Recycle + "%");
        txtValue.setText("Value: " +Constant_Values.garbage_can.getValue() + "g");
        txtThreadvalue_Frag.setText(Constant_Values.garbage_can.getThread()+"");
    }
}
