package com.example.bucketlist

import android.content.Intent
import android.os.Bundle
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

    // 로그인 액티비티에서 뒤로가기 버튼 클릭 시 내정보 페이지로 넘어가는 오류 막기 위함
    override fun onBackPressed() {

    }


    fun signin(){
        var email = email_content.text.toString()
        var password = pw_content.text.toString()
        val currentUser = auth.currentUser
        if (email.length == 0 || password.length == 0) {
            Toast.makeText(this, "Please enter your email or password.", Toast.LENGTH_SHORT).show()
        }else {
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        val user = auth.currentUser
                        val intent = Intent(this,MainActivity::class.java)
                        startActivity(intent)

                    }
                    else {
                        Toast.makeText(baseContext, "Authentication failed.", Toast.LENGTH_SHORT).show()

                    }
                }
        }
    }
}