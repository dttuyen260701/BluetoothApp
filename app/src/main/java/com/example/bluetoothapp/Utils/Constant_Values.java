package com.example.bluetoothapp.Utils;

import com.example.bluetoothapp.Models.Garbage_Can;

public class Constant_Values {
    public static Garbage_Can garbage_can;
    public static float Volume_Machine = 100f;
    public static int TIME_TO_UPDATE_GARBAGE = 1;
    public static String HTU_Control = "You can open(<|>)/close(>|<) door by first button."
            + "\nYou can open(▼)/close(▲) left/right door by two button next."
            + "\nThe last button can help you turn on(Green)/off(Blue) power of machine."
            + "\nNote: You must turn on machine to open all door.";

    public static String SERVER_URL = "https://dbdc-171-244-210-156.ngrok.io";
    public static String HTU_Status = SERVER_URL + "";
    public static String TAKE_PIC = SERVER_URL +"/takepic";
    public static String IMAGE_URL = SERVER_URL +"/static/img/img2.jpg";
    public static String URL_ABOUT_US = "https://www.facebook.com";
    public static String URL_TERM_OF_SERVICE = "https://www.youtube.com";

    // Message types sent from the BluetoothChatService Handler
    public static int MESSAGE_STATE_CHANGE1 = 1;
    public static int MESSAGE_READ = 2;
    public static int MESSAGE_WRITE = 3;
    public static int MESSAGE_DEVICE_NAME = 4;
    public static int MESSAGE_TOAST = 5;

}
