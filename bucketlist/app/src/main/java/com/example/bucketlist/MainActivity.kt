package com.example.bucketlist

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_side_menu.*
import kotlinx.android.synthetic.main.custom_dialog.*

class MainActivity : AppCompatActivity() {
    lateinit var myDialog: Dialog
    private var mDrawerToggle: ActionBarDrawerToggle? = null

    val database = FirebaseDatabase.getInstance()
    val auth = FirebaseAuth.getInstance()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_side_menu)


        val currentUser = auth.currentUser
        val myRef = currentUser?.uid?.let { database.getReference().child(it) }

        // 사용자의 월별 버킷 리스트 달성 여부에 따른 포도 색깔 설정
        if (myRef != null) {
            myRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
//                    val hasKey = dataSnapshot.child("bucketlist").child("2월").hasChild("title")
//                    Log.d("TAG", hasKey.toString())
                    for(i in 1..12) { // 1월 ~ 12월
                        val isComplete = dataSnapshot.child("bucketlist").child("${i}월").child("achievement").value.toString()
                        val grape: ImageView= findViewById(resources.getIdentifier("mon${i}", "id", packageName))
                        if(isComplete == "true") {
                            grape.setImageResource(R.drawable.circle)
                        }
                        else {
                            grape.setImageResource(R.drawable.non_complete)
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    // Failed to read value
                    Log.w("TAG", "Failed to read value.", error.toException())
                }
            })
        }



        myDialog = Dialog(this)


        mDrawerToggle =
            ActionBarDrawerToggle(this, drawer_layout, toolBar, R.string.open, R.string.close)

        mDrawerToggle!!.syncState()

        setting.setOnClickListener {
            Toast.makeText(this, "setting", Toast.LENGTH_SHORT).show()
        }
        signOut.setOnClickListener {
            Toast.makeText(this, "logout", Toast.LENGTH_SHORT).show()
        }

        about.setOnClickListener {
            Toast.makeText(this, "about us", Toast.LENGTH_SHORT).show()
        }

        // 포도 이미지 눌렀을 때 월에 맞는 버킷 리스트 내용 보여주기 및 월별 이미지 설정
        mon1.setOnClickListener {
            showDiaLog(it, "1", "jan")
        }
        mon2.setOnClickListener {
            showDiaLog(it, "2", "feb")
        }
        mon3.setOnClickListener {
            showDiaLog(it, "3", "mar")
        }
        mon4.setOnClickListener {
            showDiaLog(it, "4", "apr")
        }
        mon5.setOnClickListener {
            showDiaLog(it, "5", "may")
        }
        mon6.setOnClickListener {
            showDiaLog(it, "6", "jun")
        }
        mon7.setOnClickListener {
            showDiaLog(it, "7", "jul")
        }
        mon8.setOnClickListener {
            showDiaLog(it, "8", "aug")
        }
        mon9.setOnClickListener {
            showDiaLog(it, "9", "sep")
        }
        mon10.setOnClickListener {
            showDiaLog(it, "10", "oct")
        }
        mon11.setOnClickListener {
            showDiaLog(it, "11", "nov")
        }
        mon12.setOnClickListener {
            showDiaLog(it, "12", "dec")
        }


        //하단 메뉴 바 아이콘의 id에 따라서 아이콘 클릭 시 액티비티 전환
        bottom_navigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.icList -> {
                    val intent = Intent(this, BucketListActivity::class.java)
                    startActivity(intent)
                }
                R.id.icMenuBar -> {
                    val intent = Intent(this, myInfo::class.java)
                    startActivity(intent)
                }

            }
            true
        }
    }

    fun showDiaLog(view: View, month: String, img: String) {
        val currentUser = auth.currentUser
        val myRef = currentUser?.uid?.let { database.getReference().child(it) }
        if (myRef != null) {
            myRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    // 달에 해당하는 버킷 리스트 제목과 상세 내용을 가져온다.
                    var title = dataSnapshot.child("bucketlist").child("${month}월").child("title").getValue(String::class.java).toString()
                    var content = dataSnapshot.child("bucketlist").child("${month}월").child("content").getValue(String::class.java).toString()

                    // 포토 버튼에 따른 월별 이미지 파일 이름을 인자로 받아서 변수에 저장
                    val resName = "@drawable/${img}"
                    // 동적으로 해당하는 이미지 파일을 불러옴
                    val resImg: Int = resources.getIdentifier(resName, "drawable", packageName)

                    // 화면에 나타낼 다이얼로그 텍스트 및 이미지에 값을 저장한다.
                    myDialog.contentImg.setImageResource(resImg)
                    myDialog.select_month.text = month + "月's bucket list"
                    myDialog.bucket_title.text = title
                    myDialog.month_detail_content.text = content

                }

                override fun onCancelled(error: DatabaseError) {
                    // Failed to read value
                    Log.w("TAG", "Failed to read value.", error.toException())
                }
            })
        }

        myDialog.setContentView(R.layout.custom_dialog) // 실제로 다이얼로그를 화면에 띄어줌
        var btnClose = myDialog.findViewById<Button>(R.id.close_dialog) // 닫기 버튼 변수

        // 닫기 버튼을 눌렀을 때
        btnClose.setOnClickListener {
            myDialog.dismiss()
        }
        myDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        myDialog.show()
    }

}