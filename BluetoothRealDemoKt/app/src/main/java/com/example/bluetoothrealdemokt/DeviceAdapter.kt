package com.example.bluetoothrealdemokt

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

//Device 내용 핸들링 리사이클러뷰에 내용 넣는 역할

class DeviceAdapter (val deviceList: ArrayList<Devices>) : RecyclerView.Adapter<DeviceAdapter.CustomViewHolder>(){

    //리사이클러 클릭시 함수발생내용
    override fun onCreateViewHolder(parent: ViewGroup,viewType: Int): DeviceAdapter.CustomViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item,parent,false)
        return CustomViewHolder(view).apply {
            itemView.setOnClickListener {
                val curPos : Int = adapterPosition //현재 클릭한 위치
                val device : Devices = deviceList.get(curPos)
                Toast.makeText(parent.context,"Device Name ${device.deviceName}",Toast.LENGTH_SHORT).show()
            }

        }
    }

    override fun onBindViewHolder(holder: DeviceAdapter.CustomViewHolder, position: Int) {
        holder.deviceName.text = deviceList.get(position).deviceName
        holder.deviceAddress.text = deviceList.get(position).deviceAddress

    }

    override fun getItemCount(): Int {
        return deviceList.size
    }


    //View 홀더
    class CustomViewHolder(itemView : View): RecyclerView.ViewHolder(itemView) {
        val deviceName = itemView.findViewById<TextView>(R.id.tvDeviceNameValue) //장치 이름
        val deviceAddress =itemView.findViewById<TextView>(R.id.tvDeviceAddValue) //장치 주소
    }

}