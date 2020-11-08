package com.example.bucketlist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_bucket_post.*

class BucketPostActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bucket_post)


        // 월만 받아서 갱신
        // 만약 save 버튼을 누르면 DB에 저장
        // 다시 목록페이지로 돌아갈 때 제목을 intent로 보내줌
        // key -> bucketlist -> 1월 -> 제목, 내용

        title = "Bucket Post"

        auth = FirebaseAuth.getInstance()
        database = Firebase.database.reference

        val currentUser = auth.currentUser

        if (intent.hasExtra("month"))
        {
            month.setText(intent.getStringExtra("month") + '月')
        }

        Cancel.setOnClickListener {
            val nextIntent = Intent(this, BucketListActivity::class.java)
            startActivity(nextIntent)
        }

        Save.setOnClickListener {

            database.child("GWq5opuMfDQxjHnpQ4qwKQErQP73").child("bucketlist").child("1월").child("title").setValue(bucketTitle.text.toString())

            database.child("GWq5opuMfDQxjHnpQ4qwKQErQP73").child("bucketlist").child(intent.getStringExtra("month")+"월").child("title").setValue(bucketTitle.text.toString())
            database.child("GWq5opuMfDQxjHnpQ4qwKQErQP73").child("bucketlist").child(intent.getStringExtra("month")+"월").child("content").setValue(content.text.toString())


            if (currentUser != null) {
                //database.child(currentUser.uid).child("bucketlist").child("1월").child("title").setValue("content")

                database.child(currentUser.uid).child("bucketlist").child(intent.getStringExtra("month")+"월").child("title").setValue(bucketTitle.text.toString())
                database.child(currentUser.uid).child("bucketlist").child(intent.getStringExtra("month")+"월").child("content").setValue(content.text.toString())
            }

            var intent = Intent(this, BucketListActivity::class.java)
            intent.putExtra("title", bucketTitle.text.toString())
            startActivity(intent)
        }







    }
}