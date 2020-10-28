package com.example.bucketlist

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
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

        val bucketAdapter = listAdapter(this, bucketList)
        bucketListView.adapter = bucketAdapter

        auth = FirebaseAuth.getInstance()

//        val currentUser = auth.currentUser
//        val database = Firebase.database
//        val myRef = database.getReference(currentUser?.uid.toString())


        // 테스트 코드
        // 하단 메뉴바에서 2번째 메뉴 누르면 로그인 페이지로 이동
        bottom_navigation.setOnNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.icList ->{
                    val intent = Intent(this, signInActivity::class.java)
                    startActivity(intent)
                }
            }
            true
        }


    }




}

class Bucket (val name: String) {

}


class listAdapter (val context : Context, val list : List<Bucket>) : BaseAdapter() {
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
