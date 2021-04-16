package com.example.farmertoconsumer

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.android.synthetic.main.activity_dashboard.*

class DashboardActivity : AppCompatActivity(), (PostModel) -> Unit {

    private lateinit var auth: FirebaseAuth
    private lateinit var mDatabase: DatabaseReference
    private val firebaseFirestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    private var postList: List<PostModel> = ArrayList()
    private val postListAdapter: PostListAdapter = PostListAdapter(postList, this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        auth = FirebaseAuth.getInstance()

        mDatabase = FirebaseDatabase.getInstance().getReference("products")

        //refresh activity every 300000 milliseconds
        val handler = Handler()
        val runnable = object : Runnable {
            override fun run() {
                try {
                    recreate()
                } catch (e: Exception) {}
                handler.postDelayed(this, 300000)
            }
        }
        handler.postDelayed(runnable, 300000)

        var currentUser = auth.currentUser
        if (currentUser != null) {
            hehe.setText(currentUser.email)
        }
        loadPostData()

        firestore_list.layoutManager = LinearLayoutManager(this)
        firestore_list.adapter = postListAdapter

        //FragmentsAlternativeUnfortunately

        bottom_navigation.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.ic_home -> startActivity(Intent(this, DashboardActivity::class.java))
                R.id.ic_cart -> startActivity(Intent(this, cartactivity::class.java))
                R.id.ic_profile -> startActivity(Intent(this, profile::class.java))
            }
            true
        }
    }



    fun getPostList(): Task<QuerySnapshot> {
        return firebaseFirestore.collection("products")
                .orderBy("price", Query.Direction.ASCENDING).get()
    }


    private fun loadPostData(){
        getPostList().addOnCompleteListener {
            if(it.isSuccessful){
                postList = it.result!!.toObjects(PostModel::class.java)
                postListAdapter.postListItem = postList
                postListAdapter.notifyDataSetChanged()
        }else{
                Toast.makeText(
                        baseContext, "Error in loadPostData",
                        Toast.LENGTH_SHORT
                ).show()
            }

        }
    }

    override fun invoke(postModel: PostModel) {
        Toast.makeText(this,"clicked on item: ${postModel.name}", Toast.LENGTH_LONG).show()
        var name1=postModel.name
        var price1=postModel.price
        var image1=postModel.image
        var desc1=postModel.desc
        val intent = Intent (this, productpage::class.java)
        intent.putExtra("name",name1)
        intent.putExtra("price",price1)
        intent.putExtra("image",image1)
        intent.putExtra("desc",desc1)
        startActivity(intent)
    }
}
    //function to get data back without recycler
    /*private fun getData(){
        val db = FirebaseFirestore.getInstance()
        db.collection("products")
            .get()
            .addOnCompleteListener {

                val result : StringBuffer = StringBuffer()
                if (it.isSuccessful){
                    for(document in it.result!!){
                        result.append(document.data.getValue("name")).append(" ")
                            .append(document.data.getValue("price")).append("\n\n")
                    }
                }
            }*/
