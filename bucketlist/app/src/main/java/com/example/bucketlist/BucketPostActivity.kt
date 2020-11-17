package com.example.bucketlist

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_bucket_post.*

class BucketPostActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bucket_post)

        title = "Bucket Post"

        auth = FirebaseAuth.getInstance()
        database = Firebase.database.reference

        val currentUser = auth.currentUser

        if (intent.hasExtra("month"))
        {
            month.setText(intent.getStringExtra("month") + "月")
        }

        Cancel.setOnClickListener {
            val nextIntent = Intent(this, BucketListActivity::class.java)
            startActivity(nextIntent)
        }

        Save.setOnClickListener {

            if (currentUser != null) {
                database.child(currentUser.uid).child("bucketlist").child(intent.getStringExtra("month")+"월").child("title").setValue(bucketTitle.text.toString())
                database.child(currentUser.uid).child("bucketlist").child(intent.getStringExtra("month")+"월").child("content").setValue(content.text.toString())
                database.child(currentUser.uid).child("bucketlist").child(intent.getStringExtra("month")+"월").child("achievement").setValue(false)
            }

            var nextIntent = Intent(this, BucketListActivity::class.java)

            startActivity(nextIntent)
        }
    }
}