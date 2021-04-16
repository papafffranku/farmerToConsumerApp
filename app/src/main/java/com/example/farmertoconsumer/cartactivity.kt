package com.example.farmertoconsumer

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.android.synthetic.main.activity_cartactivity.*

class cartactivity : AppCompatActivity() {

    private val firebaseFirestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    private var cartlist: List<CartModel> = ArrayList()
    private val cartListAdapter: cartListAdapter = cartListAdapter(cartlist)
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cartactivity)

        loadcartData()

        cart_list.layoutManager = LinearLayoutManager(this)
        cart_list.adapter = cartListAdapter

        bottom_navigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.ic_home -> startActivity(Intent(this, DashboardActivity::class.java))
                R.id.ic_cart -> startActivity(Intent(this, cartactivity::class.java))
                R.id.ic_profile -> startActivity(Intent(this, profile::class.java))
            }
            true
        }

        var currentUser = auth.currentUser
        if (currentUser != null) {
            hehe3.setText(currentUser.email)
        }
        firebaseFirestore.collection("cart").whereEqualTo("cartid", currentUser.uid)
                .get()
                .addOnSuccessListener { documents ->
                    var total = 0
                    for (document in documents) {
                        total += document.getString("price").toString().toInt()
                        totalprice.setText(total.toString())
                    }
                }
                .addOnFailureListener { exception ->
                    Log.w("error", "Error getting documents: ", exception)
                }
    }

    fun getcartList(): Task<QuerySnapshot> {
        auth = FirebaseAuth.getInstance()
        var currentUser = auth.currentUser

        var person =currentUser.uid
        return firebaseFirestore.collection("cart").whereEqualTo("cartid", person).get()
    }

    private fun loadcartData(){
        getcartList().addOnCompleteListener {
            if(it.isSuccessful){
                cartlist = it.result!!.toObjects(CartModel::class.java)
                cartListAdapter.cartListItem = cartlist
                cartListAdapter.notifyDataSetChanged()
            }else{
                Toast.makeText(
                    baseContext, "Error in loadPostData",
                    Toast.LENGTH_SHORT
                ).show()
            }

        }
    }
}