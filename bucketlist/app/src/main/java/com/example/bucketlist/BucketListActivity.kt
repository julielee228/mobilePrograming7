package com.example.bucketlist

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.text.Layout
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
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
                            val plus: ImageView= findViewById(resources.getIdentifier("plus${i}", "id", packageName))
                            val bucket: RelativeLayout = findViewById(resources.getIdentifier("bucket${i}", "id", packageName))
                            val bucketTitle: TextView = findViewById(resources.getIdentifier("title${i}", "id", packageName))
                            val checkbox: CheckBox = findViewById(resources.getIdentifier("checkBox${i}", "id", packageName))

                            val check = dataSnapshot.child("bucketlist").child("${i}월").child("achievement").getValue().toString()

                            plus.visibility = View.GONE
                            bucket.visibility = View.VISIBLE
                            bucketTitle.text = title
                            if (check != "null") {
                                checkbox.isChecked = check.toBoolean()
                                checkbox.isClickable = true
                            }
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

        minus1.setOnClickListener { deleteBucket(myRef, "1") }
        minus2.setOnClickListener { deleteBucket(myRef, "2") }
        minus3.setOnClickListener { deleteBucket(myRef, "3") }
        minus4.setOnClickListener { deleteBucket(myRef, "4") }
        minus5.setOnClickListener { deleteBucket(myRef, "5") }
        minus6.setOnClickListener { deleteBucket(myRef, "6") }
        minus7.setOnClickListener { deleteBucket(myRef, "7") }
        minus8.setOnClickListener { deleteBucket(myRef, "8") }
        minus9.setOnClickListener { deleteBucket(myRef, "9") }
        minus10.setOnClickListener { deleteBucket(myRef, "10") }
        minus11.setOnClickListener { deleteBucket(myRef, "11") }
        minus12.setOnClickListener { deleteBucket(myRef, "12") }

        checkBox1.setOnCheckedChangeListener { _, isChecked -> checkBucket(myRef, "1", isChecked) }
        checkBox2.setOnCheckedChangeListener { _, isChecked -> checkBucket(myRef, "2", isChecked) }
        checkBox3.setOnCheckedChangeListener { _, isChecked -> checkBucket(myRef, "3", isChecked) }
        checkBox4.setOnCheckedChangeListener { _, isChecked -> checkBucket(myRef, "4", isChecked) }
        checkBox5.setOnCheckedChangeListener { _, isChecked -> checkBucket(myRef, "5", isChecked) }
        checkBox6.setOnCheckedChangeListener { _, isChecked -> checkBucket(myRef, "6", isChecked) }
        checkBox7.setOnCheckedChangeListener { _, isChecked -> checkBucket(myRef, "7", isChecked) }
        checkBox8.setOnCheckedChangeListener { _, isChecked -> checkBucket(myRef, "8", isChecked) }
        checkBox9.setOnCheckedChangeListener { _, isChecked -> checkBucket(myRef, "9", isChecked) }
        checkBox10.setOnCheckedChangeListener { _, isChecked -> checkBucket(myRef, "10", isChecked) }
        checkBox11.setOnCheckedChangeListener { _, isChecked -> checkBucket(myRef, "11", isChecked) }
        checkBox12.setOnCheckedChangeListener { _, isChecked -> checkBucket(myRef, "12", isChecked) }

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

    private fun checkBucket(myRef: DatabaseReference?, month:String, isChecked:Boolean) {
        val checkbox: CheckBox =
            findViewById(resources.getIdentifier("checkBox${month}", "id", packageName))

        if (myRef != null) {
            if (isChecked) {
                myRef.child("bucketlist").child(month+"월").child("achievement").setValue(true)
                checkbox.isChecked = true
                checkbox.jumpDrawablesToCurrentState()
            } else {
                myRef.child("bucketlist").child(month+"월").child("achievement").setValue(false)
                checkbox.isChecked = false
                checkbox.jumpDrawablesToCurrentState()
            }
        }
    }

    private fun deleteBucket(myRef: DatabaseReference?, month: String) {

        val plus: ImageView= findViewById(resources.getIdentifier("plus${month}", "id", packageName))
        val bucket: RelativeLayout = findViewById(resources.getIdentifier("bucket${month}", "id", packageName))
        val checkbox: CheckBox = findViewById(resources.getIdentifier("checkBox${month}", "id", packageName))

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Are you sure you want to delete the bucket?")
        builder.setMessage("The bucket is deleted when the OK button is clicked.")

        builder.setPositiveButton("Make sure") { _, _ ->
            if (myRef != null) {
                myRef.addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        myRef.child("bucketlist").child("${month}월").removeValue()
                        plus.visibility = View.VISIBLE
                        bucket.visibility = View.GONE
                        checkbox.isChecked = false
                        checkbox.isClickable = false
                        Toast.makeText(this@BucketListActivity, "Deleted", Toast.LENGTH_SHORT)
                            .show()
                    }

                    override fun onCancelled(error: DatabaseError) {
                        Log.w("TAG", "Failed to read value.", error.toException())
                    }
                })
            }
        }
        builder.setNeutralButton("Cancellation", null)
            builder.show()
    }
}

