package com.example.farmertoconsumer

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_profile.*

class profile : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        auth = FirebaseAuth.getInstance()
        var currentUser = auth.currentUser
        if (currentUser != null) {
            user.setText(currentUser.email)
            userid.setText(currentUser.uid)
        }
        signout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(this, MainActivity::class.java))
        }
        delacc.setOnClickListener {
            currentUser.delete()
        }

        bottom_navigation.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.ic_home -> startActivity(Intent(this, DashboardActivity::class.java))
                R.id.ic_cart -> startActivity(Intent(this, cartactivity::class.java))
                R.id.ic_profile -> startActivity(Intent(this, profile::class.java))
            }
            true
        }
    }
}