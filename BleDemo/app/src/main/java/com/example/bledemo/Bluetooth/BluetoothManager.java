package com.example.bledemo.Bluetooth;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.pm.PackageManager;
import android.icu.util.Output;
import android.os.Build;
import android.view.View;

import androidx.annotation.RequiresPermission;
import androidx.core.app.ActivityCompat;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.UUID;

//test
public class BluetoothManager {
    Context context;
    View view;
    BluetoothAdapter bluetoothAdapter;
    static BluetoothSocket bluetoothSocket;

    private static final UUID MY_UUID_INSECURE =
            UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    @RequiresPermission(value = "android.permission.BLUETOOTH")
    public BluetoothManager(Context context){
        this.context = context;
        this.view =view;
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    }

    public ArrayList<DeviceData> GetDeviceDataList(){


        ArrayList<DeviceData> deviceData = new ArrayList<>();
        DeviceData data;
        String Address;
        String Alias;
        String Name;
        int type;

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED){
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
        }
        for (BluetoothDevice device : bluetoothAdapter.getBondedDevices()){
            Address = device.getAddress();
            Name =device.getName();
            type = device.getType();
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R){
                Alias = device.getAlias();
            }else{
                Alias = null;
            }
            data = new DeviceData(Address,Alias,Name,type);
            deviceData.add(data);
        }
        return deviceData;
    }


    public boolean Connection(String address){
        BluetoothDevice device = bluetoothAdapter.getRemoteDevice(address);
        try{
            if (ActivityCompat.checkSelfPermission(context,Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED){
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
            }
            bluetoothSocket = device.createRfcommSocketToServiceRecord(MY_UUID_INSECURE);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bluetoothSocket.isConnected(); // 연결
    }

    public void DataOutput(String text){ //문자열을 문자형태로 변환하여 송신
        try{
            OutputStream outputStream = bluetoothSocket.getOutputStream();
            //outputStream.write(3);
            char Char_text[] = text.toCharArray();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void DataOutput(int num){ // 정수 보내기 , 변수이름 같아도 되는가..
        try{
            OutputStream outputStream = bluetoothSocket.getOutputStream();
            outputStream.write(num);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    int b ;
    public int DataInput(){
        try{
            InputStream inputStream = bluetoothSocket.getInputStream();
            inputStream.skip(inputStream.available());
            b = inputStream.read();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return b;
    }
}
