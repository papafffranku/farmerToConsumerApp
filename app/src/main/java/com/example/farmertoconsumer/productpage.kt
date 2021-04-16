package com.example.farmertoconsumer

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_productpage.*

class productpage : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_productpage)
        var intent = intent
        var name = (intent.getStringExtra("name")).toString()
        var price = (intent.getStringExtra("price")).toString()
        var image = (intent.getStringExtra("image")).toString()
        var desc = (intent.getStringExtra("desc")).toString()
        var ohgod = please.text.toString()

        auth = FirebaseAuth.getInstance()
        var currentUser = auth.currentUser

        var userid =currentUser.uid

        Glide.with(this).load(image).into(image_post_image)
        image_post_title.text = name
        desc_post_desc.text = desc
        pprice.text = price

        addtocart.setOnClickListener {
            val db=FirebaseFirestore.getInstance()
            val cart: MutableMap<String, Any> = HashMap()
            cart["cartid"] = userid
            cart["qty"] = ohgod
            cart["name"] = name
            cart["price"] = price
            cart["image"] = image


            db.collection("cart").add(cart).addOnSuccessListener {
                Toast.makeText(this, "added to cart successfully ", Toast.LENGTH_SHORT ).show()
            }
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