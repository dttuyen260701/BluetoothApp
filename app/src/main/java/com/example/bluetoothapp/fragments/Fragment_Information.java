package com.example.bluetoothapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.bluetoothapp.R;
import com.example.bluetoothapp.listeners.Listener_for_BackFragment;

public class Fragment_Information extends Fragment {
    private Button btn_specifi_Inf_Frag, btn_HTSU_Inf_Frag,
            btn_AboutUs_Inf_Frag, btn_TermofU_Inf_Frag;
    private Listener_for_BackFragment listener_for_backFragment = new Listener_for_BackFragment() {
        @Override
        public void BackFragment() {
            getActivity().getSupportFragmentManager().popBackStack();
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_information, container, false);
        SetUp(view);
        // Inflate the layout for this fragment
        return view;
    }

    private void SetUp(View view){
        btn_specifi_Inf_Frag = (Button) view.findViewById(R.id.btn_specifi_Inf_Frag);
        btn_HTSU_Inf_Frag = (Button) view.findViewById(R.id.btn_HTSU_Inf_Frag);
        btn_AboutUs_Inf_Frag = (Button) view.findViewById(R.id.btn_AboutUs_Inf_Frag);
        btn_TermofU_Inf_Frag = (Button) view.findViewById(R.id.btn_TermofU_Inf_Frag);

        btn_AboutUs_Inf_Frag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentWebview fragmentWebview = new FragmentWebview(listener_for_backFragment, false);
                back_to_AccountsFragment(fragmentWebview);
            }
        });

        btn_TermofU_Inf_Frag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentWebview fragmentWebview = new FragmentWebview(listener_for_backFragment, true);
                back_to_AccountsFragment(fragmentWebview);
            }
        });
    }

    private void back_to_AccountsFragment(Fragment fragment){
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_layout, fragment);
        transaction.addToBackStack("Account");
        transaction.commit();
    }
}
