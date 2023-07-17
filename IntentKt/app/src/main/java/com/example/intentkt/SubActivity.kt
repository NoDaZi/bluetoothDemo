package com.example.intentkt

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.intentkt.databinding.ActivityMainBinding
import com.example.intentkt.databinding.ActivitySubBinding

class SubActivity : AppCompatActivity() {
    private var mBinding: ActivitySubBinding?= null
    //매번 null 체크를 할 필요 없이 편의성을 위해 바인딩 변수 재 선언
    private val binding get() = mBinding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sub)
        mBinding = ActivitySubBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(intent.hasExtra("msg")) { //
            binding.tvGetMsg.text = intent.getStringExtra("msg")
        }



    }
    override fun onDestroy() {
        mBinding = null
        super.onDestroy()

    }
}