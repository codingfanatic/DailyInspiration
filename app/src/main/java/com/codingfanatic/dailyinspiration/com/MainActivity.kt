package com.codingfanatic.dailyinspiration.com

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue

class MainActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "KotlinActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        writeQuote()
        readQuote()
    }
    fun readQuote() {
        val messageColumnInFirebase = "quoteToBeDisplayed"
        val database = Firebase.database //Create a Firebase object variable
        val myRef = database.getReference(messageColumnInFirebase)
        val randomStringHere = ""
        
        // Read from the database
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                val value = dataSnapshot.getValue<String>()
                Log.d(TAG, "Value is: $value")
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException())
            }
        })

    }

    fun writeQuote() {
        val messageColumnInFirebase = "quoteToBeDisplayed"
        //START WRITING MESSAGE TO DB
        //Writing to your database.
        val database = Firebase.database //Create a Firebase object variable
        val myRef = database.getReference(messageColumnInFirebase)
        myRef.setValue("You are the master of your judgements, your decisions, and your actions.")
    }
}