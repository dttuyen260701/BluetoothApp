package com.example.bluetoothapp.Activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.RemoteViews;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.bluetoothapp.Models.Garbage_Can;
import com.example.bluetoothapp.R;
import com.example.bluetoothapp.Utils.Constant_Values;
import com.example.bluetoothapp.Utils.MyBluetoothService;
import com.example.bluetoothapp.fragments.Fragment_Control;
import com.example.bluetoothapp.fragments.Fragment_Information;
import com.example.bluetoothapp.fragments.Fragment_Status;
import com.example.bluetoothapp.fragments.Fragment_Weight;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@SuppressLint("HandlerLeak")
public class MainActivity extends AppCompatActivity {
    BluetoothAdapter bluetoothAdapter;
    BluetoothSocket btSocket = null;
    Boolean isBtConnected = false;

    private static final String CHANNEL_ID = "Notification";
    private static final int NOTIFICATION_ID = 121;
    private static BottomNavigationView bottom_Navigation;
    private Fragment fragment_Status, fragment_Control, fragment_Information, fragment_weight;
    private Garbage_Can garbage_can;
    private static CountDownTimer countDownTimer;
    MyBluetoothService bluetoothService = null;
    static final UUID myUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    private final static int REQUEST_CODE_ASK_PERMISSIONS = 1;
    private static final String[] REQUIRED_SDK_PERMISSIONS = new String[]{
            Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.BLUETOOTH_SCAN};

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(garbage_can == null)
            garbage_can = new Garbage_Can("", true,-451, 100f, 100f, -475);
        Constant_Values.garbage_can = garbage_can;
        AnhXa();
        setUp();

        BluetoothManager bluetoothManager = getSystemService(BluetoothManager.class);
        bluetoothAdapter = bluetoothManager.getAdapter();
        if (bluetoothAdapter == null) {
            // Device doesn't support Bluetooth
        }
        if (!bluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {

            }
            startActivityForResult(enableBtIntent, 1);
        }
        Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();
        if (pairedDevices.size() > 0) {
            // There are paired devices. Get the name and address of each paired device.
            for (BluetoothDevice device : pairedDevices) {
                String deviceName = device.getName();
                String deviceHardwareAddress = device.getAddress(); // MAC address
                Log.e("device", deviceName + " :" + deviceHardwareAddress);
//                Toast.makeText(MainActivity.this, "Address" +
//                        deviceHardwareAddress, Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void resetConnection() {


        if (btSocket != null) {
            try {btSocket.close();

            } catch (Exception e) {

            }
            btSocket = null;

        }

    }

    protected void checkPermissions() {
        final List<String> missingPermissions = new ArrayList<String>();
        // check all required dynamic permissions
        for (final String permission : REQUIRED_SDK_PERMISSIONS) {
            final int result = ContextCompat.checkSelfPermission(this, permission);
            if (result != PackageManager.PERMISSION_GRANTED) {
                missingPermissions.add(permission);
            }
        }
        if (!missingPermissions.isEmpty()) {
            // request all missing permissions
            final String[] permissions = missingPermissions
                    .toArray(new String[missingPermissions.size()]);
            ActivityCompat.requestPermissions(this, permissions, REQUEST_CODE_ASK_PERMISSIONS);
        } else {
            final int[] grantResults = new int[REQUIRED_SDK_PERMISSIONS.length];
            Arrays.fill(grantResults, PackageManager.PERMISSION_GRANTED);
            onRequestPermissionsResult(REQUEST_CODE_ASK_PERMISSIONS, REQUIRED_SDK_PERMISSIONS,
                    grantResults);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode,permissions,grantResults);
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS:
                for (int index = permissions.length - 1; index >= 0; --index) {
                    if (grantResults[index] != PackageManager.PERMISSION_GRANTED) {
                        // exit the app if one permission is not granted
//                        Toast.makeText(this, "Required permission '" + permissions[index]
//                                + "' not granted, exiting", Toast.LENGTH_LONG).show();
//                        finish();
                        return;
                    }
                }
                // all permissions were granted

                break;
        }
    }

    private final BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            String action = intent.getAction();
            if (BluetoothDevice.ACTION_ACL_CONNECTED.equals(action)) {

                isBtConnected = true;
                sendMsg("1");
            }
            if (BluetoothDevice.ACTION_ACL_DISCONNECTED.equals(action)) {
                Toast.makeText(getApplicationContext(), "Device is Disconnect", Toast.LENGTH_SHORT).show();

                isBtConnected = false;
                resetConnection();
            }
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                if (ActivityCompat.checkSelfPermission(getApplication(), Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                    checkPermissions();
                }

                if (device.getName() != null) {
                    String deviceName = device.getName();
                    String deviceHardwareAddress = device.getAddress();
                    if(deviceHardwareAddress.equals("00:21:09:01:15:8A")){
                        ConnectDevice(deviceHardwareAddress);
                    }
                    Toast.makeText(MainActivity.this,
                            "Device" + deviceName + " " + deviceHardwareAddress,
                            Toast.LENGTH_SHORT).show();
                    Log.e("Device", deviceName + " " + deviceHardwareAddress);
                }
            }
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        filter.addAction(BluetoothDevice.ACTION_ACL_CONNECTED);
        filter.addAction(BluetoothDevice.ACTION_ACL_DISCONNECTED);
        registerReceiver(receiver, filter);
    }

    private void sendMsg(String msg){
        if (btSocket != null) {

            try { // Converting the string to bytes for transferring

                btSocket.getOutputStream().write((msg+"/").toString().getBytes());
//                Toast.makeText(MainActivity.this, msg+"/", Toast.LENGTH_SHORT).show();

            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    void ConnectDevice(String macAddr) {
        try {
            if (btSocket == null || !isBtConnected) {


                // This will connect the device with address as passed
                BluetoothDevice hc = bluetoothAdapter.getRemoteDevice(macAddr);
                if ((ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) && (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED)) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.

                }
                btSocket = hc.createRfcommSocketToServiceRecord(myUUID);
                bluetoothAdapter.cancelDiscovery();
                btSocket.connect();

                bluetoothService = new MyBluetoothService(mHandler,btSocket);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }


    private final Handler mHandler;

    {
        mHandler = new Handler() {
            @SuppressLint({"HandlerLeak", "ResourceAsColor"})
            @Override
            public void handleMessage(Message msg) {
                if(msg.what == Constant_Values.MESSAGE_READ){
                    byte[] readBuf = (byte[]) msg.obj;
                    // construct a string from the valid bytes in the buffer
                    String readMessage = new String(readBuf, 0, msg.arg1);
                    //Log.e("Meow",  readMessage);
                    String [] res = readMessage.split("/");
                    for( String r : res){
//                            Log.e("Meow", r);
                    }
                }
            }
        };
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }

    private void AnhXa(){
        bottom_Navigation = (BottomNavigationView) findViewById(R.id.bottom_Navigation);
        bottom_Navigation.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);
    }

    private void setUp(){
        createNotification();
        fragment_Status = new Fragment_Status();
        fragment_Control = new Fragment_Control();
        fragment_Information = new Fragment_Information();
        fragment_weight = new Fragment_Weight();
        chang_Menu(fragment_Status);
        if(countDownTimer != null){
            countDownTimer.cancel();
        }
        countDownTimer = new CountDownTimer(60*60*60,
                //Constant_Values.TIME_TO_UPDATE_GARBAGE*60*1000) {
            5000){
            @Override
            public void onTick(long l) {
                if(garbage_can.getVolume_nonRecycle() >= 100)
                    garbage_can.setVolume_nonRecycle(0);
                if(garbage_can.getVolume_recycle() >= 100)
                    garbage_can.setVolume_recycle(0);
                garbage_can.setVolume_nonRecycle(garbage_can.getVolume_nonRecycle() + 10);
                garbage_can.setVolume_recycle(garbage_can.getVolume_recycle() + 10);
                switch (bottom_Navigation.getSelectedItemId()){
                    case R.id.bottom_Status:
                        ((Fragment_Status) fragment_Status).updateView();
                        break;
                    case R.id.bottom_setting:
                        ((Fragment_Weight) fragment_weight).updateView();
                        break;
                    default:
                        break;
                }
                createNotification();
            }
            @Override
            public void onFinish() {
                this.start();
            }
        };
        countDownTimer.start();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener(){
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.bottom_Status:
                            chang_Menu(fragment_Status);
                            return true;
                        case R.id.bottom_Control:
                            chang_Menu(fragment_Control);
                            return true;
                        case R.id.bottom_setting:
                            chang_Menu(fragment_weight);
                            return true;
                        case R.id.bottom_Information:
                            chang_Menu(fragment_Information);
                            return true;
                    }
                    return false;
                }
    };

    private void chang_Menu(Fragment fragment){
        if(getSupportFragmentManager().getBackStackEntryCount() > 0){
            for(int i = 0; i < getSupportFragmentManager().getBackStackEntryCount(); ++i){
                getSupportFragmentManager().popBackStack();
            }
        }
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_layout, fragment);
        transaction.commit();
    }

    private void createNotification(){
        check_SDK_Notification();

        float per_Recycle = garbage_can.getVolume_recycle()/ Constant_Values.Volume_Machine,
                per_NonRecycle = garbage_can.getVolume_nonRecycle()/Constant_Values.Volume_Machine;


        RemoteViews notification_layout =
                new RemoteViews(getPackageName(), R.layout.notification_view);

        notification_layout.setImageViewResource(R.id.img_NonRecycle_noti_view, R.drawable.clip_image);
        notification_layout.setInt(R.id.img_NonRecycle_noti_view, "setImageLevel", (int) (10000*per_NonRecycle));
        notification_layout.setImageViewResource(R.id.img_Recycle_noti_view, R.drawable.clip_image2);
        notification_layout.setInt(R.id.img_Recycle_noti_view, "setImageLevel", (int) (10000*per_Recycle));

        per_NonRecycle = ((float) Math.round(per_NonRecycle*10000)/100);
        per_Recycle = ((float) Math.round(per_Recycle*10000)/100);
        notification_layout.setTextViewText(R.id.txtPer_NonRecycle_noti_view, per_NonRecycle + "%");
        notification_layout.setTextViewText(R.id.txtPer_Recycle_noti_view, per_Recycle + "%");

        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID);
        Intent intent = new Intent(MainActivity.this, MainActivity.class);
        //set quay lại màn hình chính dùng set Action + add  Category
        intent.setAction(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        // Create the PendingIntent, FLAG_UPDATE_CURRENT: mở được nheièu lần, FLAG_ONE_SHOT: mở được 1 lần
        PendingIntent pendingIntent = PendingIntent.getActivity(MainActivity.this, 0,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setSmallIcon(R.drawable.iconstrash80);
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
        builder.setCustomBigContentView(notification_layout).
                setStyle(new NotificationCompat.DecoratedCustomViewStyle());
        builder.setContentIntent(pendingIntent);
        builder.setVisibility(NotificationCompat.VISIBILITY_PUBLIC);
        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(getApplicationContext());
        managerCompat.notify(NOTIFICATION_ID, builder.build());
    }

    private void check_SDK_Notification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            //channel cho phiên bản mới
            CharSequence name = "Notification";
            String des = "This is my Personal Notification";
            int important = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel( CHANNEL_ID, name, important);
            channel.setDescription(des);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
    }
}