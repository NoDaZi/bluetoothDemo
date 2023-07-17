package com.example.listview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Adapter
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import com.example.listview.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    //전역 변수로 바인딩 객체 선언
    private var mBinding: ActivityMainBinding?= null
    //매번 null 체크를 할 필요 없이 편의성을 위해 바인딩 변수 재 선언
    private val binding get() = mBinding!!

    var UserList = arrayListOf<User>(
        User(R.drawable.pikachu,"다지","29","하이 아임 다지"),
        User(R.drawable.pikachu,"송이","27","하이 아임 송대장"),
        User(R.drawable.pikachu,"젤다","22","하이 아임 젤다"),
        User(R.drawable.pikachu,"링크","22","하이 아임 링크")

    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        val item = arrayOf("사과","바나나","배","딸기")
//        //context란 한 액티비티의 모든
//        binding.listView.adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1,item)
        val Adapter = UserAdapter (this,UserList)
        binding.listView.adapter= Adapter
        binding.listView.onItemClickListener = AdapterView.OnItemClickListener{parent, view, position, id ->
            val selectItem = parent.getItemAtPosition(position) as User  //모델형태
            Toast.makeText(this,selectItem.name,Toast.LENGTH_SHORT).show()
        }

    }
    override fun onDestroy() {
        mBinding = null
        super.onDestroy()

    }
}