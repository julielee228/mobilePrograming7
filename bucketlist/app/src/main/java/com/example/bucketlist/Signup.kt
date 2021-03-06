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

        // editText에 입력된 문자를 가져와 변수에 저장
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
            Toast.makeText(this,"Please enter all information..",Toast.LENGTH_SHORT).show()
        }
        // 비밀번호, 비밀번호 확인 문자열 일치 확인
        else if(pw != pw_confirm) {Toast.makeText(this, "The password is different..",Toast.LENGTH_SHORT).show()}
        // 비밀번호 유효성 확인
        else if(!matcher_vali.find()) {
            Toast.makeText(this, "Please enter the password in 8 to 15 characters, including numbers, letters, and special characters.",Toast.LENGTH_SHORT).show()
        }
        else{

            // 회원가입 기능
            auth.createUserWithEmailAndPassword(email,pw).addOnCompleteListener(this){task ->
                if(task.isSuccessful){
                    val currentUser = auth.currentUser
                    // 입력받은 회원 정보를 DB에 저장하는 코드
                    if (currentUser != null) {
                        writeNewUser(email,name,currentUser.uid,phone)
                    }
                    Toast.makeText(this,"Membership registration is complete.",Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, signInActivity::class.java) //회원가입 성공 시 로그인 페이지로 이동
                    startActivity(intent)
                }
                else
                {
                    Toast.makeText(this,"Please check your email or password",Toast.LENGTH_SHORT).show()
                }
            }
        }

    }

    // DB에 회원정보를 저장하는 함수
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