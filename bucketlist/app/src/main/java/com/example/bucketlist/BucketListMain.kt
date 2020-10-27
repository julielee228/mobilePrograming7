package com.example.bucketlist

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.CheckBox
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_bucket_list_main.*

class BucketListMain : AppCompatActivity() {
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