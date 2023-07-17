package com.example.intentkt

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.intentkt.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    //전역 변수로 바인딩 객체 선언
    private var mBinding: ActivityMainBinding?= null
    //매번 null 체크를 할 필요 없이 편의성을 위해 바인딩 변수 재 선언
    private val binding get() = mBinding!!


    override fun onCreate(savedInstanceState: Bundle?) {  //앱이 최초 실행됐을때 수행.
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        //버튼 A를 눌렀을때 실행
        binding.btnA.setOnClickListener{
            //var : 변수 , val : 값 변경 할 수 없는 변수
            val intent = Intent(this,SubActivity::class.java) //다음화면으로 이동하기 위한 인탠트 객체 생성
            intent.putExtra("msg",binding.tvSendmsg.text.toString()) // Hello World 라는 텍스트 값을 담은 뒤 msg라는 키로 잠굼
            startActivity(intent)
            finish() // 자기 자신 엑티비티 파괴. 즉 앱이 꺼진다.
        }

    }

    override fun onDestroy() {
        mBinding = null
        super.onDestroy()

    }
}