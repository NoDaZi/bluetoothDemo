package com.example.bluetoothrealdemokt

import android.Manifest
import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

open class MainActivity : AppCompatActivity() {

    companion object{
        private const val REQUEST_BLUETOOTH_PERMISSION = 1
        private const val REQUEST_ENABLE_BLUETOOTH =1
    }
    //블루투스 매니저 선언 , Adapter를 객체를 획득할 때 사용
    val bluetoothManager: BluetoothManager by lazy {
        getSystemService(BluetoothManager::class.java)
    }
    val bluetoothAdapter: BluetoothAdapter? by lazy {
        bluetoothManager.adapter
    }

    //권한 관련
    private val PERMISSIONS = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.BLUETOOTH_SCAN,
        Manifest.permission.BLUETOOTH_CONNECT
    )
    private val PERMISSIONS_S_ABOVE = arrayOf(
        Manifest.permission.BLUETOOTH_SCAN,
        Manifest.permission.BLUETOOTH_CONNECT,
        Manifest.permission.ACCESS_FINE_LOCATION
    )
    private val REQUEST_ALL_PERMISSION = 2
    //TODO: 권한 요청 에러 해결

    //Toast Message 띄우기
    private fun showMessage(mainActivity: MainActivity, text: String) {
        println(text)
        Toast.makeText(this@MainActivity, "${text}", Toast.LENGTH_SHORT).show()
    }
    //권한 확인
    private fun hasPermissions(context: Context, permissions: Array<String>): Boolean {
        for (permission in permissions) {
            if (ActivityCompat.checkSelfPermission(context, permission)
                != PackageManager.PERMISSION_GRANTED
            ) {
                return false
            }
        }
        return true
    }
    val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)


    //블루투스 켜는 버튼 호출 함수
    private fun requestTurnOnBluetooth(){
        showMessage(this, "블루투스 기능을 켜주시기 바랍니다.")
        startActivityForResult(enableBtIntent, REQUEST_ENABLE_BLUETOOTH)
    }

    //앱 켤시 시작
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //권한요청
        requestBluetoothPermission()

        //TextView
        val text01 = findViewById<TextView>(R.id.tvTest)
        val tvPer = findViewById<TextView>(R.id.tvPer)

        //Button
        val startBtn = findViewById<Button>(R.id.startBtn)
        val scanBtn = findViewById<Button>(R.id.scanBtn)
        val btnFra1 = findViewById<Button>(R.id.btn_fragment1)
        val btnFra2 = findViewById<Button>(R.id.btn_fragment2)
        val btnFra3 = findViewById<Button>(R.id.btn_fragment3)

        //Btn 기능
        startBtn.setOnClickListener {
            if (bluetoothAdapter?.isEnabled == false) {
                tvPer.text = "블루투스가 OFF 상태입니다.\n" +
                        " 켜주시기 바랍니다."
                requestTurnOnBluetooth()
            } else {
                tvPer.text = "블루투스가 ON 상태입니다."
                showMessage(this, "Bluetooth Ready")
            }
        }
        scanBtn.setOnClickListener{
            if (bluetoothAdapter?.isEnabled == false) {
                requestTurnOnBluetooth()
            } else {
                if (ActivityCompat.checkSelfPermission(
                        this,
                        Manifest.permission.BLUETOOTH_SCAN
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                }
                bluetoothAdapter?.cancelDiscovery()
                showMessage(this, "스캔 시작")
                bluetoothAdapter?.startDiscovery()
            }
        }
        //TODO: 기능추가

        //Fragment 전환 버튼
        btnFra1.setOnClickListener{
            setFrag(1)
        }
        btnFra2.setOnClickListener{
            if(bluetoothAdapter?.isEnabled == false){
                requestTurnOnBluetooth()
            }else {
                setFrag(2)
            }
        }
        btnFra3.setOnClickListener{
            if(bluetoothAdapter?.isEnabled == false){
                requestTurnOnBluetooth()
            }else {
                setFrag(3)
            }
        }

        //블루투스 권한 확인
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            if (!hasPermissions(this, PERMISSIONS_S_ABOVE)) {
                requestPermissions(PERMISSIONS_S_ABOVE, REQUEST_ALL_PERMISSION)
            }
        } else {
            if (!hasPermissions(this, PERMISSIONS)) {
                requestPermissions(PERMISSIONS, REQUEST_ALL_PERMISSION)
            }
        }

        //블루투스를 지원하지 않는 장비면 강제종료
        if (bluetoothAdapter == null) {
            showMessage(this, "블루투스를 지원하지 않는 기기 입니다.")
            finish() //종료

        } else { //블루투스 지원하는 기기일때
            //귄한 확인
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.BLUETOOTH_CONNECT
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return
            }

            if (bluetoothAdapter?.isEnabled == false) { //블루투스 on/off 체크하기
                tvPer.text = "블루투스가 OFF 상태입니다.\n ON 시켜주세요."
                requestTurnOnBluetooth()
            } else {
                tvPer.text = "블루투스가 ON 상태입니다."
            }

            text01.text = "hello ${Build.VERSION_CODES.S} , SDK${Build.VERSION.SDK_INT} "
        }
    }


    //페어링된 리스트 Devices 형식으로 리턴
    fun getDeviceList(): ArrayList<Devices>? {
        if (ActivityCompat.checkSelfPermission(
                this,Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED
        ){
            return null
        }
        val pairedDevices: MutableSet<BluetoothDevice>? = bluetoothAdapter!!.bondedDevices

        var deviceList = ArrayList<Devices>()
        for (device in pairedDevices!!){
            var deviceName = device.name
            var deviceAddress = device.address

            //Log.d("deviceName", device.toString())
            deviceList.add(Devices(deviceName,deviceAddress))
        }
        return deviceList
    }

    //Fragment 설정
    fun setFrag(fragNum : Int) {
        val ft = supportFragmentManager.beginTransaction()
        when(fragNum)
        {
            1-> {
                ft.replace(R.id.main_frame, Fragment1()).commit()
            }
            2-> {
                ft.replace(R.id.main_frame, Fragment2()).commit()
            }
            3-> {
                ft.replace(R.id.main_frame, Fragment3()).commit()
            }
        }

    }

    //블루투스 권한 요청
    private fun requestBluetoothPermission(){
        val permissionsToRequest  = mutableListOf<String>()
        //Log.d("dazi","${permissionsToRequest.toString()}")
        if (ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.BLUETOOTH
        ) != PackageManager.PERMISSION_GRANTED
        ){
            permissionsToRequest.add(android.Manifest.permission.BLUETOOTH)
        }
        if (ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.BLUETOOTH_ADMIN
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            permissionsToRequest.add(android.Manifest.permission.BLUETOOTH_ADMIN)
        }
        if(permissionsToRequest.isNotEmpty()){
            ActivityCompat.requestPermissions(
                this,
                permissionsToRequest.toTypedArray(),
                REQUEST_BLUETOOTH_PERMISSION
            )
        }
    }
    //블루투스 상태 체크 함수
    private fun checkBluetoothState(){
        bluetoothAdapter?.let{
            if(!it.isEnabled){
                showMessage(this,"블루투스를 켜주시기 바랍니다.")
            }else{
                showMessage(this,"Bluetooth Ready")
            }
        }
    }

    //permission check
    private fun checkPermission(){
        var permission = mutableMapOf<String,String>()
        permission["BLUETOOTH"] = Manifest.permission.BLUETOOTH
        permission["BLUETOOTH_ADMIN"] = Manifest.permission.BLUETOOTH_ADMIN

        var denied = permission.count{ContextCompat.checkSelfPermission(this,it.value)==PackageManager.PERMISSION_DENIED}
        //마시멜로 버전 이후
        if(denied > 0 && Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            requestPermissions(permission.values.toTypedArray(),REQUEST_BLUETOOTH_PERMISSION)
        }
    }
    override fun onRequestPermissionsResult(requestCode: Int,permissions: Array<String?>,grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            REQUEST_ALL_PERMISSION ->{
                if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    showMessage(this,"Permissions granted!")
                }else{
                    requestPermissions(permissions,REQUEST_ALL_PERMISSION)
                    showMessage(this,"Permissions must be granted")
                }
            }
        }
    }

    //블루투스 권한 요청 후 처리 함수
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode ==REQUEST_ENABLE_BLUETOOTH){
            if(resultCode == Activity.RESULT_OK){
            }else{
                showMessage(this,"블루투스를 켜주시기 바랍니다.")
                requestTurnOnBluetooth()
            }
        }
    }
}

