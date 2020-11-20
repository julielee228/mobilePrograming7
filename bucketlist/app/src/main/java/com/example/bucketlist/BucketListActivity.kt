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

        val database = FirebaseDatabase.getInstance()       //
        val auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser      // 유저 확인하기
        val myRef = currentUser?.uid?.let { database.getReference().child(it) }

        if (myRef != null) {
            myRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {     //데이터 스냅샷을 통해 DB데이터 수신
                    for(i in 1..12) {
                        var title = dataSnapshot.child("bucketlist").child("${i}월").child("title").getValue(String::class.java).toString()
                        if (title != "null") {
                            // 문자열 리소스를 동적으로 로드
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

        // 월 별로 버킷 리스트 작성할 때 BucketPostActivity로 이동
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

        // 버킷리스트를 삭제할 때 deleteBucket 함수 호출
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

        // 버킷리스트가 생성된 월만 checkBox가 활성화 되어있으므로 체크 가능
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

        // 네비게이션 바 생성
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

    // 버킷리스트가 등록 되어있을 경우
    private fun checkBucket(myRef: DatabaseReference?, month:String, isChecked:Boolean) {
        val checkbox: CheckBox =
            findViewById(resources.getIdentifier("checkBox${month}", "id", packageName))

        if (myRef != null) {
            if (isChecked) {    // 체크를 하면 DB true 로 저장
                myRef.child("bucketlist").child(month+"월").child("achievement").setValue(true)
                checkbox.isChecked = true
                checkbox.jumpDrawablesToCurrentState()
            } else {    // 체크를 풀면 DB false 로 저장
                myRef.child("bucketlist").child(month+"월").child("achievement").setValue(false)
                checkbox.isChecked = false
                checkbox.jumpDrawablesToCurrentState()
            }
        }
    }

    private fun deleteBucket(myRef: DatabaseReference?, month: String) {

        // 문자열 리소스를 동적으로 로드
        val plus: ImageView= findViewById(resources.getIdentifier("plus${month}", "id", packageName))
        val bucket: RelativeLayout = findViewById(resources.getIdentifier("bucket${month}", "id", packageName))
        val checkbox: CheckBox = findViewById(resources.getIdentifier("checkBox${month}", "id", packageName))

        val builder = AlertDialog.Builder(this)     // 다이어로그 인스턴스 생성
        builder.setTitle("Are you sure you want to delete the bucket?")     // 다이어로그 제목 설정
        builder.setMessage("The bucket is deleted when the OK button is clicked.")      // 다이어로그 내용 설정

        // 확인 버튼을 눌렀을 때
        builder.setPositiveButton("Make sure") { _, _ ->
            if (myRef != null) {
                myRef.addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        // DB에서 해당하는 월의 데이터를 삭제함
                        myRef.child("bucketlist").child("${month}월").removeValue()
                        // 초기환경으로 돌아가기
                        plus.visibility = View.VISIBLE
                        bucket.visibility = View.GONE
                        checkbox.isChecked = false
                        checkbox.isClickable = false
                        // 삭제되었다는 토스트 메시지 띄우기
                        Toast.makeText(this@BucketListActivity, "Deleted", Toast.LENGTH_SHORT)
                            .show()
                    }

                    override fun onCancelled(error: DatabaseError) {
                        Log.w("TAG", "Failed to read value.", error.toException())
                    }
                })
            }
        }
        // 취소 버튼을 눌렀을 때
        builder.setNeutralButton("Cancellation", null)
            builder.show()
    }
}

