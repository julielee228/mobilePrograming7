package com.example.bucketlist

import android.graphics.drawable.PaintDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import kotlinx.android.synthetic.main.activity_side_menu.*

class MainActivity : AppCompatActivity() {

    private var mDrawerToggle : ActionBarDrawerToggle? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_side_menu)

        mDrawerToggle = ActionBarDrawerToggle(this,drawer_layout,toolBar,R.string.open,R.string.close)

        mDrawerToggle!!.syncState()

        setting.setOnClickListener {
            Toast.makeText(this,"setting",Toast.LENGTH_SHORT).show()
        }
        signOut.setOnClickListener {
            Toast.makeText(this,"signout",Toast.LENGTH_SHORT).show()
        }

        about.setOnClickListener {
            Toast.makeText(this,"about us",Toast.LENGTH_SHORT).show()
        }


    }

}