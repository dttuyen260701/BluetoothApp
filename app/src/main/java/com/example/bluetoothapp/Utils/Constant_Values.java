package com.example.bluetoothapp.Utils;

import com.example.bluetoothapp.Models.Garbage_Can;

public class Constant_Values {
    public static Garbage_Can garbage_can;
    public static float Volume_Machine = 7f;
    public static int TIME_TO_UPDATE_GARBAGE = 1;
    public static String HTU_Control = "You can chang mode from Auto to Control. In control mode you can attack servo and make Support shaft down the way you want.";
    public static String HTU_Weight = "You can set Scale when to you see right measure of weight.";
    public static String SERVER_URL = "https://dbdc-171-244-210-156.ngrok.io";
    public static String HTU_Status = "Blue is smaller than threshold and Green is bigger than threshold.";
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
