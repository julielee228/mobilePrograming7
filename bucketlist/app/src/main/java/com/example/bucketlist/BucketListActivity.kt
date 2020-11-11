package com.example.bucketlist

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.text.Layout
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_bucket_list.*


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
                    for(i in 1..12) {
                        var title = dataSnapshot.child("bucketlist").child("${i}월").child("title").getValue(String::class.java).toString()
                        if (title != "null") {
                            val idPlus = "plus${i}"
                            val idBucket = "bucket${i}"
                            val idBucketTitle = "title${i}"

                            val plus: ImageView= findViewById(resources.getIdentifier(idPlus, "id", packageName))
                            val bucket: RelativeLayout = findViewById(resources.getIdentifier(idBucket, "id", packageName))
                            val bucketTitle: TextView = findViewById(resources.getIdentifier(idBucketTitle, "id", packageName))
                            plus.visibility = View.GONE
                            bucket.visibility = View.VISIBLE
                            bucketTitle.text = title
                        }
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

        minus1.setOnClickListener {

            val builder = AlertDialog.Builder(this)
            builder.setTitle("버킷을 삭제하시겠습니까?")
            builder.setMessage("확인 버튼 클릭 시 버킷이 삭제 됩니다.")

            builder.setPositiveButton("확인") { dialogInterface: DialogInterface, i: Int ->
                if (myRef != null) {
                    myRef.addValueEventListener(object : ValueEventListener {
                        override fun onDataChange(dataSnapshot: DataSnapshot) {
                            myRef.child("bucketlist").child("1월").removeValue()
                            //dataSnapshot.child("bucketlist").child("1월").setValue(null);
                            plus1.visibility = View.VISIBLE
                            bucket1.visibility = View.GONE
                            Toast.makeText(this@BucketListActivity, "삭제 되었습니다.", Toast.LENGTH_SHORT)
                                .show()
                        }

                        override fun onCancelled(error: DatabaseError) {
                            Log.w("TAG", "Failed to read value.", error.toException())
                        }

                    })
                }
            }
            builder.setNeutralButton("취소", null)
            builder.show()
        }

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
