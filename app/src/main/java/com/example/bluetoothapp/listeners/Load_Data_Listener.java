package com.example.bluetoothapp.listeners;

import com.example.bluetoothapp.Models.Garbage_Can;

public interface Load_Data_Listener {
    public void onPre();
    public void onEnd(Boolean done, Garbage_Can garbage_can);
}
