import android.Manifest
import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.bletest.R

class BluetoothActivity : AppCompatActivity() {

    private lateinit var bluetoothAdapter: BluetoothAdapter
    private lateinit var discoveredDevices: MutableList<BluetoothDevice>
    private lateinit var listView: ListView

    private val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            when (intent.action) {
                BluetoothDevice.ACTION_FOUND -> {
                    val device =
                        intent.getParcelableExtra<BluetoothDevice>(BluetoothDevice.EXTRA_DEVICE)
                    device?.let {
                        discoveredDevices.add(it)
                        val deviceName = if (it.name != null) it.name else "Unknown Device"
                        val deviceAddress = it.address
                        val deviceInfo = "$deviceName\n$deviceAddress"
                        (listView.adapter as ArrayAdapter<String>).add(deviceInfo)
                    }
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bluetooth)

        listView = findViewById(R.id.device_list)
        discoveredDevices = mutableListOf()
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1)
        listView.adapter = adapter

        // 블루투스 어댑터 가져오기
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()

        // 기기의 블루투스가 지원되지 않는 경우
        if (bluetoothAdapter == null) {
            Toast.makeText(this, "블루투스가 지원되지 않습니다.", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        // 블루투스가 비활성화된 경우, 사용자에게 활성화를 요청합니다.
        if (!bluetoothAdapter.isEnabled) {
            val enableBluetoothIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            startActivityForResult(enableBluetoothIntent, REQUEST_ENABLE_BT)
        }

        // 블루투스 디바이스 검색을 시작합니다.
        startBluetoothDiscovery()
    }

    private fun startBluetoothDiscovery() {
        // 블루투스 디바이스 검색 시작
        if (bluetoothAdapter.isDiscovering) {
            bluetoothAdapter.cancelDiscovery()
        }

        // 디바이스 검색 결과를 받기 위한 BroadcastReceiver 등록
        val filter = IntentFilter(BluetoothDevice.ACTION_FOUND)
        registerReceiver(receiver, filter)

        // 블루투스 디바이스 검색 시작
        bluetoothAdapter.startDiscovery()
    }

    override fun onDestroy() {
        super.onDestroy()
        // 액티비티가 종료될 때 BroadcastReceiver 등록 해제 및 블루투스 검색 중지
        unregisterReceiver(receiver)
        bluetoothAdapter.cancelDiscovery()
    }

    companion object {
        const val REQUEST_ENABLE_BT = 1
    }
}
