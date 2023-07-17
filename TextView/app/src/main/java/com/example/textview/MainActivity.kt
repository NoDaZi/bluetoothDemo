package com.example.textview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import com.example.textview.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    //전역 변수로 바인딩 객체 선언
    private var mBinding: ActivityMainBinding?= null
    //매번 null 체크를 할 필요 없이 편의성을 위해 바인딩 변수 재 선언
    private val binding get() = mBinding!!


    override fun onCreate(savedInstanceState: Bundle?) {  //앱이 최초 실행됐을때 수행.
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.tvTitle.setText("안녕하세욧 도로롱입니다.")

    }

    override fun onDestroy() {
        mBinding = null
        super.onDestroy()

    }
}