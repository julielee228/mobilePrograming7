package com.example.bucketlist

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_signup.*


class Signup : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    private lateinit var database: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        auth = FirebaseAuth.getInstance()
        database = Firebase.database.reference
        signup_btn.setOnClickListener {
            signup()
        }
    }

    public override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
    }
    fun signup(){
        var email = edit_email.text.toString()
        var pw = edit_pw.text.toString()
        var name = edit_name.text.toString()
        auth.createUserWithEmailAndPassword(email,pw).addOnCompleteListener(this){task ->
            if(task.isSuccessful){
                writeNewUser(email,name)
                Toast.makeText(this,"ok",Toast.LENGTH_SHORT).show()
            }else{

            }
        }
    }
    private fun writeNewUser(email: String?, name : String){
        val user = User(email,name)
        database.child("users").child(name).setValue(user)
    }

}

data class User (
    var email : String? = "",
    var username : String? = ""
)