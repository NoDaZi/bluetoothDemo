package com.example.navigationview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import android.widget.Toast.makeText
import androidx.core.view.GravityCompat
import com.example.navigationview.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity(),NavigationView.OnNavigationItemSelectedListener {
    //전역 변수로 바인딩 객체 선언
    private var mBinding: ActivityMainBinding? = null
    //매번 null 체크를 할 필요 없이 편의성을 위해 바인딩 변수 재 선언
    private val binding get() = mBinding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnNavi.setOnClickListener {

            binding.layoutDrawer.openDrawer(GravityCompat.START) //START : LEFT , END :  right
        }

        binding.naviView.setNavigationItemSelectedListener (this) //네이게이션 메뉴 아이템에 클릭 속성부여
    }


    override fun onDestroy() {
        mBinding = null
        super.onDestroy()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.access -> makeText(applicationContext, "접근성", Toast.LENGTH_LONG).show()
            R.id.email -> makeText(applicationContext, "이메일", Toast.LENGTH_LONG).show()
            R.id.send -> makeText(applicationContext, "메세지", Toast.LENGTH_LONG).show()

        }
        binding.layoutDrawer.closeDrawers() // 네비게이션 뷰 닫기
        return false
    }

    override fun onBackPressed() {
        if(binding.layoutDrawer.isDrawerOpen(GravityCompat.START)){
            binding.layoutDrawer.closeDrawers()
        }
        else{
            super.onBackPressed()
        }
    }
}

