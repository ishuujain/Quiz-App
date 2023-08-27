package com.example.quizapp.activities
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.quizapp.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_sign_upactivity.btnSignUp
import kotlinx.android.synthetic.main.activity_sign_upactivity.etsEmailAddress
import kotlinx.android.synthetic.main.activity_sign_upactivity.etsPassword
import kotlinx.android.synthetic.main.activity_sign_upactivity.etsconfirmpassword
import kotlinx.android.synthetic.main.activity_sign_upactivity.txtSignUp

class SignUpactivity : AppCompatActivity() {
    lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_upactivity)
        firebaseAuth=FirebaseAuth.getInstance()
        btnSignUp.setOnClickListener{
            signUpUser()
        }
        txtSignUp.setOnClickListener(){
            val intent= Intent(this, Loginactivity::class.java)
            startActivity(intent)
            finish()
        }

    }
    private fun signUpUser(){
        val email=etsEmailAddress.text.toString()
        val password=etsPassword.text.toString()
        val confirmpassword=etsconfirmpassword.text.toString()
        if(email.isBlank() || password.isBlank() || confirmpassword.isBlank()){
            Toast.makeText(this, "Email and Password cant be blank", Toast.LENGTH_SHORT).show()
            return
        }
        if(password!=confirmpassword){
            Toast.makeText(this, "Password and confirm password do not match", Toast.LENGTH_SHORT).show()
            return
        }

        firebaseAuth.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener(this){
                if(it.isSuccessful){
                    Toast.makeText(this, "Login successfull", Toast.LENGTH_SHORT).show()
                    val intent=Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                else{
                    Toast.makeText(this, "Error creating user", Toast.LENGTH_SHORT).show()
                }
            }
    }
}