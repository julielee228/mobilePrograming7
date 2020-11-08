package com.example.bucketlist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_bucket_list.*
import kotlinx.android.synthetic.main.activity_bucket_list.bottom_navigation


class BucketListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bucket_list)

        title = "BucketList"

        val database = FirebaseDatabase.getInstance()
        val auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser
        val myRef = currentUser?.uid?.let { database.getReference().child(it) }

        if (myRef != null) {
            myRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    var title = dataSnapshot.child("bucketlist").child("1월").child("title").getValue(String::class.java).toString()
                    if (title != "null") {
                        plus1.visibility = View.GONE
                        title1.visibility = View.VISIBLE
                        title1.text = title
                    }

                    title = dataSnapshot.child("bucketlist").child("2월").child("title").getValue(String::class.java).toString()
                    if (title != "null") {
                        plus2.visibility = View.GONE
                        title2.visibility = View.VISIBLE
                        title2.text = title
                    }

                    title = dataSnapshot.child("bucketlist").child("3월").child("title").getValue(String::class.java).toString()
                    if (title != "null") {
                        plus3.visibility = View.GONE
                        title3.visibility = View.VISIBLE
                        title3.text = title
                    }

                    title = dataSnapshot.child("bucketlist").child("4월").child("title").getValue(String::class.java).toString()
                    if (title != "null") {
                        plus4.visibility = View.GONE
                        title4.visibility = View.VISIBLE
                        title4.text = title
                    }

                    title = dataSnapshot.child("bucketlist").child("5월").child("title").getValue(String::class.java).toString()
                    if (title != "null") {
                        plus5.visibility = View.GONE
                        title5.visibility = View.VISIBLE
                        title5.text = title
                    }

                    title = dataSnapshot.child("bucketlist").child("6월").child("title").getValue(String::class.java).toString()
                    if (title != "null") {
                        plus6.visibility = View.GONE
                        title6.visibility = View.VISIBLE
                        title6.text = title
                    }

                    title = dataSnapshot.child("bucketlist").child("7월").child("title").getValue(String::class.java).toString()
                    if (title != "null") {
                        plus7.visibility = View.GONE
                        title7.visibility = View.VISIBLE
                        title7.text = title
                    }

                    title = dataSnapshot.child("bucketlist").child("8월").child("title").getValue(String::class.java).toString()
                    if (title != "null") {
                        plus8.visibility = View.GONE
                        title8.visibility = View.VISIBLE
                        title8.text = title
                    }

                    title = dataSnapshot.child("bucketlist").child("9월").child("title").getValue(String::class.java).toString()
                    if (title != "null") {
                        plus9.visibility = View.GONE
                        title9.visibility = View.VISIBLE
                        title9.text = title
                    }

                    title = dataSnapshot.child("bucketlist").child("10월").child("title").getValue(String::class.java).toString()
                    if (title != "null") {
                        plus10.visibility = View.GONE
                        title10.visibility = View.VISIBLE
                        title10.text = title
                    }

                    title = dataSnapshot.child("bucketlist").child("11월").child("title").getValue(String::class.java).toString()
                    if (title != "null") {
                        plus11.visibility = View.GONE
                        title11.visibility = View.VISIBLE
                        title11.text = title
                    }

                    title = dataSnapshot.child("bucketlist").child("12월").child("title").getValue(String::class.java).toString()
                    if (title != "null") {
                        plus12.visibility = View.GONE
                        title12.visibility = View.VISIBLE
                        title12.text = title
                    }

                }

                override fun onCancelled(error: DatabaseError) {
                    Log.w("TAG", "Failed to read value.", error.toException())
                }
            })
        }

        // 월 별로 버킷 리스트 작성
        val nextIntent = Intent(this, BucketPostActivity::class.java)

        plus1.setOnClickListener { nextIntent.putExtra("month", "1")
            startActivity(nextIntent)}
        plus2.setOnClickListener { nextIntent.putExtra("month", "2")
            startActivity(nextIntent)}
        plus3.setOnClickListener { nextIntent.putExtra("month", "3")
            startActivity(nextIntent)}
        plus4.setOnClickListener { nextIntent.putExtra("month", "4")
            startActivity(nextIntent)}
        plus5.setOnClickListener { nextIntent.putExtra("month", "5")
            startActivity(nextIntent)}
        plus6.setOnClickListener { nextIntent.putExtra("month", "6")
            startActivity(nextIntent)}
        plus7.setOnClickListener { nextIntent.putExtra("month", "7")
            startActivity(nextIntent)}
        plus8.setOnClickListener { nextIntent.putExtra("month", "8")
            startActivity(nextIntent)}
        plus9.setOnClickListener { nextIntent.putExtra("month", "9")
            startActivity(nextIntent)}
        plus10.setOnClickListener { nextIntent.putExtra("month", "10")
            startActivity(nextIntent)}
        plus11.setOnClickListener { nextIntent.putExtra("month", "11")
            startActivity(nextIntent)}
        plus12.setOnClickListener { nextIntent.putExtra("month", "12")
            startActivity(nextIntent)}

        // 네비게이션 바
        bottom_navigation.setOnNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.icHome ->{
                    val intent = Intent(this, MainActivity::class.java)
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
