package com.example.farmertoconsumer

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    var farmcount=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        op.setVideoPath("https://v.pinimg.com/videos/mc/720p/65/30/b0/6530b0d8e5baf6f463996fd7180e0a18.mp4")
        op.start()
        op.setOnCompletionListener { mp ->
            mp.setVolume(0F,0F)
            op.start()
            mp!!.isLooping=true
        }
        op.setOnPreparedListener { mp ->
            mp.setVolume(0F, 0F)
            op.start()
            mp!!.isLooping = true
        }

        auth = FirebaseAuth.getInstance()

        btn_sign_up.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
            finish()
        }

        btn_log_in.setOnClickListener {
            doLogin()
        }

        farmerlogin.setOnClickListener {
            doLogin()
            farmcount=1
        }
    }

    private fun doLogin() {
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

        auth.signInWithEmailAndPassword(tv_username.text.toString(), tv_password.text.toString())
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    updateUI(user)
                } else {

                    updateUI(null)
                }
            }
    }
    public override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        updateUI(currentUser)
    }

    private fun updateUI(currentUser: FirebaseUser?) {

        if (currentUser != null) {
            if(currentUser.isEmailVerified) {
                if(farmcount==1){
                    startActivity(Intent(this, farmerActivity::class.java))
                    finish()
                }else{
                    startActivity(Intent(this, DashboardActivity::class.java))
                    finish()
                }
            }else{
                Toast.makeText(
                    baseContext, "Please verify your email address.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}
