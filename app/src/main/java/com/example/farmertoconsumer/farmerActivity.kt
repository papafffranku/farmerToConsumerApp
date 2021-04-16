package com.example.farmertoconsumer

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_farmer.*

class farmerActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var mDatabase: DatabaseReference
    private val firebaseFirestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_farmer)

        auth = FirebaseAuth.getInstance()

        addtoproduct.setOnClickListener {
            val db=FirebaseFirestore.getInstance()
            val products: MutableMap<String, Any> = HashMap()
            products["name"] = farm_username.text
            products["desc"] = farm_desc.text
            products["price"] = farm_price.text
            products["image"] = farm_image.text

            db.collection("products").add(products).addOnSuccessListener {
                Toast.makeText(this, "added to cart successfully ", Toast.LENGTH_SHORT ).show()
            }
        }

        var currentUser = auth.currentUser
        if (currentUser != null) {
            heheFarmer.setText(currentUser.email)
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