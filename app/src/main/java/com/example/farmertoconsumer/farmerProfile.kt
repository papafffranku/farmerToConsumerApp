package com.example.farmertoconsumer

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_farmer_profile.*

class farmerProfile : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_farmer_profile)

        auth = FirebaseAuth.getInstance()
        var currentUser = auth.currentUser
        if (currentUser != null) {
            farmer_user.setText(currentUser.email)
            farmer_userid.setText(currentUser.uid)
        }
        farmer_signout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(this, MainActivity::class.java))
        }
        farmer_delacc.setOnClickListener {
            currentUser.delete()
        }
        farmer_bottom_navigation.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.ic_home -> startActivity(Intent(this, farmerActivity::class.java))
                R.id.ic_profile -> startActivity(Intent(this, farmerProfile::class.java))
            }
            true
        }
    }
}