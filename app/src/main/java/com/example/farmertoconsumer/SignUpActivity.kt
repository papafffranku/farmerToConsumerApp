package com.example.farmertoconsumer

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.btn_sign_up
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        auth = FirebaseAuth.getInstance()

        op1.setVideoPath("https://v.pinimg.com/videos/mc/720p/a2/0b/9d/a20b9d59cc7bdb2b5c1748d7c3e347a6.mp4")
        op1.start()
        op1.setOnCompletionListener { mp ->
            mp.setVolume(0F,0F)
            op1.start()
            mp!!.isLooping=true
        }
        op1.setOnPreparedListener { mp ->
            mp.setVolume(0F, 0F)
            op1.start()
            mp!!.isLooping = true
        }

        btn_sign_up.setOnClickListener {
            signUpUser()
        }

        goback.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

    }
    private fun signUpUser() {

        var tv_username=findViewById<EditText>(R.id.tv_username)
        var tv_password=findViewById<EditText>(R.id.tv_password)

        if (tv_username.text.toString().isEmpty()) {
            tv_username.error = "Please enter email"
            tv_username.requestFocus()
            return
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(tv_username.text.toString()).matches()) {
            tv_username.error = "Please enter valid email"
            tv_username.requestFocus()
            return
        }

        if (tv_password.text.toString().isEmpty()) {
            tv_password.error = "Please enter password"
            tv_password.requestFocus()
            return
        }

        auth.createUserWithEmailAndPassword(tv_username.text.toString(), tv_password.text.toString())
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    user?.sendEmailVerification()
                        ?.addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                startActivity(Intent(this, MainActivity::class.java))
                                finish()
                            }
                        }
                } else {
                    Toast.makeText(
                        baseContext, "Sign Up failed. Try again after some time.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }
}