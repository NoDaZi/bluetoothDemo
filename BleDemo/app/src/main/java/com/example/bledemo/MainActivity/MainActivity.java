package com.example.bledemo.MainActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.example.bledemo.Bluetooth.BluetoothManager;
import com.example.bledemo.Bluetooth.DeviceData;
import com.example.bledemo.R;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    TextView TextView_BluetoothName;
    BottomNavigationItemView BottomNavigationView_SelectButton;
    BluetoothManager bluetoothManager;

    private ArrayList<DeviceData> arrayList;
    private MainAdapter mainAdapter;
    private RecyclerView recyclerView;
    private int e;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bluetoothManager = new BluetoothManager(getApplicationContext());


    }
}