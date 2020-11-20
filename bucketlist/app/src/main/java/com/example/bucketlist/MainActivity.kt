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
    lateinit var myDialog: Dialog // 다이얼로그 객체 생성 준비
    private var mDrawerToggle: ActionBarDrawerToggle? = null

    val database = FirebaseDatabase.getInstance()
    val auth = FirebaseAuth.getInstance()
    private var lists = arrayListOf<String>() // 월 별 버킷 리스트 제목을 담을 배열 선언


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_side_menu)


        val currentUser = auth.currentUser
        val myRef = currentUser?.uid?.let { database.getReference().child(it) }



        // 사용자의 월별 버킷 리스트 달성 여부에 따른 포도 색깔 설정
        if (myRef != null) {
            myRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    for(i in 1..12) { // 1월 ~ 12월
                        val title = dataSnapshot.child("bucketlist").child("${i}월").child("title").value.toString() // 버킷 리스트 제목 추출
                        lists.add(title) // arrayList 배열에 1~12월에 해당하는 버킷 리스트 제목을 추가

                        // 페이지 로드 시 월별 버킷 리스트가 달성되어 있는지 확인 후 달성 여부에 따라서 메인 액티비티 포도 이미지 지정
                        val isComplete = dataSnapshot.child("bucketlist").child("${i}월").child("achievement").value.toString()

                        // 동적으로 id 값을 불러와서 해당 id의 ImageView 에 포도 이미지 설정
                        val grape: ImageView= findViewById(resources.getIdentifier("mon${i}", "id", packageName))
                        if(isComplete == "true") {
                            grape.setImageResource(R.drawable.circle)
                        }
                        else {
                            grape.setImageResource(R.drawable.non_complete)
                        }
                    }

                    // 포도 버튼 클릭 시 해당 월의 버킷 리스트 상세 내용 출력
                    // 각 월별로 버튼 클릭 시 리스트에 저장되어 있는 버킷 리스트 제목을 참조
                    // 리스트 제목이 null 일 경우에는 그 달에 버킷 리스트가 없다는 뜻이므로 상세 내용 다이얼로그를 출력하지 않음
                    if(!lists[0].equals("null")) {
                        mon1.setOnClickListener {
                            showDiaLog(it, "1", "jan")
                        }
                    }
                    if(!lists[1].equals("null")) {
                        mon2.setOnClickListener {
                            showDiaLog(it, "2", "feb")
                        }
                    }
                    if(!lists[2].equals("null")) {
                        mon3.setOnClickListener {
                            showDiaLog(it, "3", "mar")
                        }
                    }
                    if(!lists[3].equals("null")) {
                        mon4.setOnClickListener {
                            showDiaLog(it, "4", "apr")
                        }
                    }
                    if(!lists[4].equals("null")) {
                        mon5.setOnClickListener {
                            showDiaLog(it, "5", "may")
                        }
                    }
                    if(!lists[5].equals("null")) {
                        mon6.setOnClickListener {
                            showDiaLog(it, "6", "jun")
                        }
                    }
                    if(!lists[6].equals("null")) {
                        mon7.setOnClickListener {
                            showDiaLog(it, "7", "jul")
                        }
                    }
                    if(!lists[7].equals("null")) {
                        mon8.setOnClickListener {
                            showDiaLog(it, "8", "aug")
                        }
                    }
                    if(!lists[8].equals("null")) {
                        mon9.setOnClickListener {
                            showDiaLog(it, "9", "sep")
                        }
                    }
                    if(!lists[9].equals("null")) {
                        mon10.setOnClickListener {
                            showDiaLog(it, "10", "oct")
                        }
                    }
                    if(!lists[10].equals("null")) {
                        mon11.setOnClickListener {
                            showDiaLog(it, "11", "nov")
                        }
                    }
                    if(!lists[11].equals("null")) {
                        mon12.setOnClickListener {
                            showDiaLog(it, "12", "dec")
                        }
                    }

                }

                override fun onCancelled(error: DatabaseError) {
                    // Failed to read value
                    Log.w("TAG", "Failed to read value.", error.toException())
                }
            })
        }



        myDialog = Dialog(this) // 다이얼로그 객체 생성


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

    // 상세 내용을 보여주는 다이얼로그 함수
    fun showDiaLog(view: View, month: String, img: String) {
        // 현재 유저 정보를 불러옴
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