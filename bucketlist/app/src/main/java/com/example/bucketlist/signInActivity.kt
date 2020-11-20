package com.example.bucketlist

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_sign_in.*

class signInActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        auth = FirebaseAuth.getInstance()

        // 회원가입 페이지로 이동하기
        go_sign_up.setOnClickListener {
            val intent = Intent(this,Signup::class.java)
            startActivity(intent)
        }
        // 로그인 버튼 클릭 시 signin() 함수 호출
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

    // 이메일, 비밀번호 입력 여부 및 로그인 성공 여부 구현 함수
    fun signin(){
        var email = email_content.text.toString()
        var password = pw_content.text.toString()
        if (email.length == 0 || password.length == 0) {
            Toast.makeText(this, "Please enter your email or password.", Toast.LENGTH_SHORT).show()
        }else {
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
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