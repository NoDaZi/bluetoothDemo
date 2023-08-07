package com.example.bluetoothrealdemokt

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class Fragment1 : Fragment(){

    companion object{
        private const val testData = "TEST"
        fun createFrament(devices: ArrayList<Devices>?) : Fragment1 {
            val frament = Fragment1()
            val args = Bundle()
            args.putParcelableArrayList(testData,devices)
            frament.arguments =args
            return frament
        }

    }

    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,savedInstanceState: Bundle?): View? {
        //fragment setting
        val view =inflater.inflate(R.layout.frag1, container, false)
        val recyclerView: RecyclerView = view.findViewById(R.id.rv_devicesList)
        super.onCreateView(inflater, container, savedInstanceState)

        val mainActivity = activity as? MainActivity
        val deviceList = mainActivity?.getDeviceList()

        val sampleData = arrayListOf(
            Devices("Dazi's GalaxyBook3 PRO 360","22:22:22:22"),
            Devices("Dazi's GalaxyFold","22:22:22:22"),
            Devices("Dazi's GalaxyWatch","22:22:22:22"),
            Devices("Dazi's GalaxyBud Pro2","22:22:22:22"),
            Devices("Dazi's Galaxy S21+","22:22:22:22"),
            Devices("Arduino","22:22:22:22")
        )

        if (deviceList != null){
            for (device in deviceList){
                Log.d("inMaincc",device.toString())
            }
        }else{
            Log.d("inMaincc", null.toString())
        }
        //recyclerView.layoutManager = LinearLayoutManager(Fragment1,LinearLayoutManager.VERTICAL,false)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = DeviceAdapter(deviceList!!)

        return view
    }
}
