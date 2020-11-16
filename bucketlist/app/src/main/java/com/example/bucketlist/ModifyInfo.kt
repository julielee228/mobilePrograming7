package com.example.bucketlist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.widget.Toast
import androidx.annotation.NonNull
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_modify_info.*
import kotlinx.android.synthetic.main.activity_my_info.*
import kotlinx.android.synthetic.main.activity_my_info.backBtn
import kotlinx.android.synthetic.main.activity_signup.*

class ModifyInfo : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var writeDatabase: DatabaseReference
    private lateinit var email : String

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
                    beforeName.text = name
                    beforePhone.text = phone
                }

                override fun onCancelled(error: DatabaseError) {
                    // Failed to read value
                    Log.w("TAG", "Failed to read value.", error.toException())
                }
            })
        }

        backBtn.setOnClickListener {
            finish()
        }


        modify.setOnClickListener {
            var name = editAfterName.text.toString()
            var phone = editAfterPhone.text.toString()

            if (myRef != null) {
                myRef.addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        // This method is called once with the initial value and again
                        // whenever data at this location is updated.
                        email = dataSnapshot.child("email").getValue(String::class.java).toString()
                    }
                    override fun onCancelled(error: DatabaseError) {
                        // Failed to read value
                    }
                })
            }

            Log.d("TAG", email)
            val user = NewUser(email,name,phone)
            if (currentUser != null) {
                writeDatabase.child(currentUser.uid).setValue(user)
            }
            finish()
        }


    }


}
data class NewUser (
    var email : String? = "",
    var username : String? = "",
    var phone : String? = ""
)