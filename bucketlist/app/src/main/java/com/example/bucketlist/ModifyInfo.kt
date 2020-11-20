package com.example.bucketlist

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_modify_info.*

class ModifyInfo : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var writeDatabase: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_modify_info)

        writeDatabase = Firebase.database.reference

        val database = FirebaseDatabase.getInstance()
        auth = FirebaseAuth.getInstance()

        val currentUser = auth.currentUser
        val myRef = currentUser?.uid?.let { database.getReference().child(it) }


        if (myRef != null) {
            myRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val name = dataSnapshot.child("username").getValue(String::class.java).toString()
                    val phone = dataSnapshot.child("phone").getValue(String::class.java).toString()

                    beforeName.text = name
                    beforePhone.text = phone
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.w("TAG", "Failed to read value.", error.toException())
                }
            })
        }

        back_btn.setOnClickListener {
            finish()
        }


        modify.setOnClickListener {
            var name = editAfterName.text.toString()
            var phone = editAfterPhone.text.toString()

            // 아무것도 입력하지 않고 수정 버튼 클릭시 경고창 띄우는 코드
            if(name.equals("") && phone.equals(""))
            {
                val builder = AlertDialog.Builder(this)
                builder.setTitle("변경되는 정보가 없습니다.")
                builder.setMessage("다시 확인해주세요.")
                builder.setPositiveButton("check",null)
                builder.show()
            }
            // 핸드폰 번호만 바꾸고 싶을 경우, 핸드폰 번호만 업데이트
            else if(name.equals("") && !phone.equals(""))
            {
                val user = hashMapOf<String, Any>(
                    "/${currentUser?.uid}/phone" to phone
                )
                if (currentUser != null) {
                    writeDatabase.updateChildren(user)
                }
                finish()
            }
            // 이름만 바꾸고 싶을 경우, 이름 정보만 업데이트
            else if(!name.equals("") && phone.equals(""))
            {

                val user = hashMapOf<String, Any>(
                    "/${currentUser?.uid}/username" to name
                )
                if (currentUser != null) {
                    writeDatabase.updateChildren(user)
                }
                finish()
            }

            // 둘 다 바꾸고 싶을 때, 두 정보 모두 업데이트
            else
            {
                val user = hashMapOf<String, Any>(
                    "/${currentUser?.uid}/username" to name,
                    "/${currentUser?.uid}/phone" to phone
                )
                if (currentUser != null) {
                    writeDatabase.updateChildren(user)
                }
                finish()
            }
        }


    }


}