package com.example.bluetoothapp.Utils;

import android.content.Context;
import android.net.ConnectivityManager;

public class Methods {
    private Context context;
    private static Methods Instance;

    private Methods(Context context) {
        this.context = context;
    }

    public static Methods getInstance(Context context){
        if(Instance == null)
            Instance = new Methods(context);
        return Instance;
    }


    //Kiem Tra mang
    public boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

}
