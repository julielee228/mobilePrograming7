package com.example.bucketlist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_my_info.*

class myInfo : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_info)

        // ㄷㅔ이터 불러오기 45라인 username부분이 키 값
        val database = FirebaseDatabase.getInstance()
        auth = FirebaseAuth.getInstance()

        val currentUser = auth.currentUser
        val myRef = currentUser?.uid?.let { database.getReference().child(it) }

        if (myRef != null) {
            myRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    val name = dataSnapshot.child("username").getValue(String::class.java).toString()
                    val email = dataSnapshot.child("email").getValue(String::class.java).toString()
                    myName.text = name
                    myEmail.text = email
                }

                override fun onCancelled(error: DatabaseError) {
                    // Failed to read value
                    Log.w("TAG", "Failed to read value.", error.toException())
                }
            })
        }
        backBtn.setOnClickListener {
            val intent = Intent(this,BucketListMain::class.java)
            startActivity(intent)
        }
    }
}