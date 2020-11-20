package com.example.bucketlist

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_modify_info.*

class ModifyInfo : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var writeDatabase: DatabaseReference
    private lateinit var email : String
    private lateinit var basePhone : String
    private lateinit var baseName : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_modify_info)

        writeDatabase = Firebase.database.reference

        val database = FirebaseDatabase.getInstance()
        auth = FirebaseAuth.getInstance()

        val currentUser = auth.currentUser
        val myRef = currentUser?.uid?.let { database.getReference().child(it) }


        if (myRef != null) {
            myRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.

                    val name = dataSnapshot.child("username").getValue(String::class.java).toString()
                    val phone = dataSnapshot.child("phone").getValue(String::class.java).toString()
                    email = dataSnapshot.child("email").getValue(String::class.java).toString()
                    basePhone = dataSnapshot.child("phone").getValue(String::class.java).toString()
                    baseName = dataSnapshot.child("username").getValue(String::class.java).toString()
                    beforeName.text = name
                    beforePhone.text = phone


                }

                override fun onCancelled(error: DatabaseError) {
                    // Failed to read value
                    Log.w("TAG", "Failed to read value.", error.toException())
                }
            })
        }

        back_btn.setOnClickListener {
            finish()
        }


        modify.setOnClickListener {
            var name = editAfterName.text.toString()
            var phone = editAfterPhone.text.toString()

            if(name.equals("") && phone.equals(""))
            {

                val builder = AlertDialog.Builder(this)
                builder.setTitle("변경되는 정보가 없습니다.")
                builder.setMessage("다시 확인해주세요.")
                builder.setPositiveButton("check",null)
                builder.show()
            }
            else if(name.equals("") && !phone.equals(""))
            {
                val user = hashMapOf<String, Any>(
                    "/${currentUser?.uid}/phone" to phone
                )
                if (currentUser != null) {
                    writeDatabase.updateChildren(user)
                }
                finish()
            }
            else if(!name.equals("") && phone.equals(""))
            {

                val user = hashMapOf<String, Any>(
                    "/${currentUser?.uid}/username" to name
                )
                if (currentUser != null) {
                    writeDatabase.updateChildren(user)
                }
                finish()
            }

            else
            {

                val user = hashMapOf<String, Any>(
                    "/${currentUser?.uid}/username" to name,
                    "/${currentUser?.uid}/phone" to phone
                )
                if (currentUser != null) {
                    writeDatabase.updateChildren(user)
                }
                finish()
            }
        }


    }


}