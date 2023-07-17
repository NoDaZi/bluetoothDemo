package com.example.imageview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.imageview.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var mBinding: ActivityMainBinding?= null
    //매번 null 체크를 할 필요 없이 편의성을 위해 바인딩 변수 재 선언
    private val binding get() = mBinding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.btnToast.setOnClickListener{
            Toast.makeText(this@MainActivity, "you clicked!", Toast.LENGTH_SHORT).show() //토스트메세지 띄우기
            binding.ivProfile.setImageResource(R.drawable.ducks) // 새로운 이미지 교체
        }

    }
    override fun onDestroy() {
        mBinding = null
        super.onDestroy()

    }
}