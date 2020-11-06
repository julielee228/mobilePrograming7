package com.example.bucketlist

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_side_menu.*

class MainActivity : AppCompatActivity() {

    private var mDrawerToggle : ActionBarDrawerToggle? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_side_menu)

        mDrawerToggle = ActionBarDrawerToggle(this,drawer_layout,toolBar,R.string.open,R.string.close)

        mDrawerToggle!!.syncState()

        setting.setOnClickListener {
            Toast.makeText(this,"setting",Toast.LENGTH_SHORT).show()
        }
        signOut.setOnClickListener {
            Toast.makeText(this,"signout",Toast.LENGTH_SHORT).show()
        }

        about.setOnClickListener {
            Toast.makeText(this,"about us",Toast.LENGTH_SHORT).show()
        }


        Jan.setOnClickListener {
            Toast.makeText(this, "Jan PoDo",Toast.LENGTH_SHORT).show()
        }

        // 테스트 코드
        // 하단 메뉴바에서 2번째 메뉴 누르면 로그인 페이지로 이동
        bottom_navigation.setOnNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.icList ->{
                    val intent = Intent(this, BucketListMain::class.java)
                    startActivity(intent)
                }
                R.id.icMenuBar ->{
                    val intent = Intent(this, myInfo::class.java)
                    startActivity(intent)
                }

            }
            true
        }
    }

}