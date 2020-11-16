package com.example.bucketlist

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_my_info.*
import java.time.LocalDate

class myInfo : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_info)

        modify_info.setOnClickListener {
            val intent2 = Intent(this,ModifyInfo::class.java)
            startActivity(intent2)
        }

        // ㄷㅔ이터 불러오기 45라인 username부분이 키 값
        val database = FirebaseDatabase.getInstance()
        auth = FirebaseAuth.getInstance()

        val currentUser = auth.currentUser
        val myRef = currentUser?.uid?.let { database.getReference().child(it) }

        var now = LocalDate.now().toString()
        var month = IntRange(5,6)
        now = now.slice(month)
        //Log.d("aa",now.slice(month))
        today.text = (now + "월").toString()



        if (myRef != null) {
            myRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    var title = dataSnapshot.child("bucketlist").child((now + "월").toString()).child("title").getValue(String::class.java).toString()
                    var content = dataSnapshot.child("bucketlist").child((now + "월").toString()).child("content").getValue(String::class.java).toString()
                    val name = dataSnapshot.child("username").getValue(String::class.java).toString()
                    val email = dataSnapshot.child("email").getValue(String::class.java).toString()
                    val phone = dataSnapshot.child("phone").getValue(String::class.java).toString()
                    myName.text = name
                    myEmail.text = email
                    myPhone.text = phone
                    month_do_title.text = title
                    month_do_content.text = content
                }

                override fun onCancelled(error: DatabaseError) {
                    // Failed to read value
                    Log.w("TAG", "Failed to read value.", error.toException())
                }
            })
        }
        backBtn.setOnClickListener {
            finish()
        }

        // 사용자 로그아웃
        logOut.setOnClickListener {
            val builder = AlertDialog.Builder(this,R.style.Theme_AppCompat_Light_Dialog)
            builder.setTitle("로그아웃 하시겠습니까?")
            builder.setMessage("확인 버튼 클릭 시 로그아웃 됩니다.")

            //확인 버튼 클릭 시 파이어베이스 함수를 이용하여 로그아웃 후 로그인 페이지로 이동
            builder.setPositiveButton("확인") { _, _ ->
                FirebaseAuth.getInstance().signOut()
                val intent2 = Intent(this,signInActivity::class.java)
                startActivity(intent2)
                Toast.makeText(this,"로그아웃 되었습니다.", Toast.LENGTH_SHORT).show()
            }
            builder.setNegativeButton("취소") { _, _ ->
                //No action
            }
            builder.show()
        }
    }
}

