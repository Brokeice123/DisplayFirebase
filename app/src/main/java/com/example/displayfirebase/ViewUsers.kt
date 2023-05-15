package com.example.displayfirebase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ViewUsers : AppCompatActivity() {

    lateinit var my_lists_view:ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_users)

        my_lists_view = findViewById(R.id.mlistPeople)

        var users:ArrayList<User> = ArrayList()

            var myadapter = UserAdapter(applicationContext, users)

        //Access the table

        var my_db = FirebaseDatabase.getInstance().reference.child("Names")

        my_db.addValueEventListener(object : ValueEventListener{

            override fun onDataChange(snapshot: DataSnapshot) {
                //Get data and display in array
                users.clear()
                for (snap in snapshot.children){
                    var person = snap.getValue(User::class.java)
                    users.add(person!!)
                }

                myadapter.notifyDataSetChanged()

            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(applicationContext, "Failed to Display data", Toast.LENGTH_SHORT).show()
            }

        })

      my_lists_view.adapter = myadapter

    }
}