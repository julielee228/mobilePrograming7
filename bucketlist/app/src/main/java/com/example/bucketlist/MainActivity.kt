package com.example.bucketlist

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_side_menu.*

class MainActivity : AppCompatActivity() {
    lateinit var myDialog:Dialog

    private var mDrawerToggle : ActionBarDrawerToggle? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_side_menu)

        myDialog = Dialog(this)


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

        // 1월의 포도 이미지 눌렀을 때 상세 내용 다이얼로그 창 실행
        Jan.setOnClickListener {
            showDiaLog(it)
        }



        // 테스트 코드
        // 하단 메뉴바에서 2번째 메뉴 누르면 로그인 페이지로 이동
        bottom_navigation.setOnNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.icList ->{
                    val intent = Intent(this, BucketListActivity::class.java)
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

    fun showDiaLog(view: View) {
        myDialog.setContentView(R.layout.custom_dialog) // 다이얼로그 이미지 설정
        var btnClose = myDialog.findViewById<Button>(R.id.close_dialog) // 닫기 버튼 눌렀을 때

        btnClose.setOnClickListener {
            myDialog.dismiss()
        }
        myDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        myDialog.show()
    }

}