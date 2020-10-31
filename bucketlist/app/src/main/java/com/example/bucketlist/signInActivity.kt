package com.example.bucketlist

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_sign_in.*

class signInActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    //private lateinit var database: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        auth = FirebaseAuth.getInstance()
        go_sign_up.setOnClickListener {
            val intent = Intent(this,Signup::class.java)
            startActivity(intent)
        }

        signin_btn.setOnClickListener {
                signin()
        }
    }

    public override fun onStart() {
        super.onStart()
    }


    fun signin(){
        var email = email_content.text.toString()
        var password = pw_content.text.toString()
        val currentUser = auth.currentUser
        if (email.length == 0 || password.length == 0) {
            Toast.makeText(this, "이메일 또는 비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show()
        }else {
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d("aa", "signInWithEmail:success")
                        val user = auth.currentUser
                        val intent = Intent(this,BucketListMain::class.java)
                        startActivity(intent)
                        //updateUI(user)
                    }
                    else {
                        // If sign in fails, display a message to the user.
                        //Log.w(TAG, "signInWithEmail:failure", task.exception)
                        Toast.makeText(baseContext, "Authentication failed.",
                            Toast.LENGTH_SHORT).show()
                        //updateUI(null)
                        // ...
                    }

                    // ...
                }

        }

    }
}