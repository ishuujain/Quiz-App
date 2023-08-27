package com.example.quizapp.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.quizapp.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_loginactivity.btnSignUp
import kotlinx.android.synthetic.main.activity_loginactivity.btnlogin
import kotlinx.android.synthetic.main.activity_loginactivity.etEmailAddress
import kotlinx.android.synthetic.main.activity_loginactivity.etPassword

class Loginactivity : AppCompatActivity() {
    lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loginactivity)
        firebaseAuth=FirebaseAuth.getInstance()
        btnlogin.setOnClickListener(){
            login()
        }
        btnSignUp.setOnClickListener(){
            val intent=Intent(this, SignUpactivity::class.java)
            startActivity(intent)
            finish()
        }
    }
    private fun login(){
        val email=etEmailAddress.text.toString()
        val password=etPassword.text.toString()

        if(email.isBlank() || password.isBlank()){
            Toast.makeText(this, "Email/Password cannot be Empty", Toast.LENGTH_SHORT).show()
            return
        }
        firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this){
            if(it.isSuccessful){
                Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()
                val intent=Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
            else{
                Toast.makeText(this, "Authentication Failed", Toast.LENGTH_SHORT).show()
            }
        }
    }
}