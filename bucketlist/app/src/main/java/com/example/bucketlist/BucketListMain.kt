package com.example.bucketlist

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.CheckBox
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_bucket_list_main.*


class BucketListMain : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bucket_list_main)


        val bucketList = listOf(
            Bucket("패러글라이딩"),
            Bucket("소풍가기"),
            Bucket("스키장가기"),
            Bucket("노가리가보기")
        )
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
                    val value = dataSnapshot.child("username").getValue(String::class.java)

                    Log.d("TAG", "Value is: $value")
                }

                override fun onCancelled(error: DatabaseError) {
                    // Failed to read value
                    Log.w("TAG", "Failed to read value.", error.toException())
                }
            })
        }

        val bucketAdapter = listAdapter(this, bucketList)
        bucketListView.adapter = bucketAdapter

        bottom_navigation.setOnNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.icMenuBar ->{
                    val intent = Intent(this, myInfo::class.java)
                    startActivity(intent)
                }
                R.id.icHome -> {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                }

            }
            true
        }

    }




}

class Bucket(val name: String) {

}


class listAdapter(val context: Context, val list: List<Bucket>) : BaseAdapter() {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = LayoutInflater.from(context).inflate(R.layout.item_list, null)

        val checkBox = view.findViewById<CheckBox>(R.id.bucketCheckBox)
//        val name = view.findViewById<TextView>(R.id.item)
//        val num = view.findViewById<TextView>(R.id.num)
        checkBox.text = list[position].name
//        name.text = list[position].name
//        num.text = list[position].num.toString()
        return view
    }

    override fun getItem(position: Int): Any {
        return list[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return list.count()
    }
}
