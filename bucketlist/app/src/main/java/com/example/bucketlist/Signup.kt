package com.example.bucketlist

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_signup.*
import java.util.regex.Pattern


class Signup : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    private lateinit var database: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        go_sign_in.setOnClickListener {
            val intent = Intent(this,signInActivity::class.java)
            startActivity(intent)
        }

        auth = FirebaseAuth.getInstance()
        database = Firebase.database.reference
        signup_btn.setOnClickListener {
            signup()
        }
    }

    public override fun onStart() {
        super.onStart()

    }
    fun signup(){


        var email = edit_email.text.toString()
        var phone = edit_phone.text.toString()
        var pw = edit_pw.text.toString()
        var name = edit_name.text.toString()
        var pw_confirm = edit_pw_confirm.text.toString()

        //비밀번호는 숫자, 문자, 특수문자를 포함하여 8~15글자 사이로 지정
        val val_validation = "^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[\$@\$!%*#?&]).{8,15}.\$"

        val pattern_validation = Pattern.compile(val_validation)

        val matcher_vali = pattern_validation.matcher(pw)

        // 입력하지 않은 회원 정보 확인
        if(pw=="" || pw_confirm=="" || email=="" || phone=="" || name=="") {
            Toast.makeText(this,"회원정보를 모두 입력해주세요.",Toast.LENGTH_SHORT).show()
        }
        // 비밀번호, 비밀번호 확인 문자열 일치 확인
        else if(pw != pw_confirm) {Toast.makeText(this, "비밀번호가 일치하지 않습니다.",Toast.LENGTH_SHORT).show()}
        // 비밀번호 유효성 확인
        else if(!matcher_vali.find()) {
            Toast.makeText(this, "비밀번호는 숫자,문자,특수문자를 포함하여 8~15글자로 입력해주세요.",Toast.LENGTH_SHORT).show()
        }
        else{
            auth.createUserWithEmailAndPassword(email,pw).addOnCompleteListener(this){task ->
                if(task.isSuccessful){
                    val currentUser = auth.currentUser
                    if (currentUser != null) {
                        writeNewUser(email,name,currentUser.uid,phone)
                    }
                    Toast.makeText(this,"회원가입이 완료되었습니다.",Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, signInActivity::class.java) //회원가입 성공 시 로그인 페이지로 이동
                    startActivity(intent)
                }
            }
        }

    }
    private fun writeNewUser(email: String?, name : String,uid : String,phone : String){
        val user = User(email,name,phone)
        database.child(uid).setValue(user)
    }

}



data class User (
    var email : String? = "",
    var username : String? = "",
    var phone : String? = ""
)