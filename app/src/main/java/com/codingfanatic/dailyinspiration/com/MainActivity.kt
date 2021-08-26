package com.codingfanatic.dailyinspiration.com

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import java.time.DayOfWeek
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

         readQuote()
         //writeQuote()
    }

    override fun onResume() {
        super.onResume()
        setContentView(R.layout.activity_main)
        readQuote()
    }

    //Read a String from the Realtime Database and use it as the TextView
    fun readQuote() {
        val randomQuote = (0..6).random()
        val messageColumnInFirebase = "quoteToBeDisplayed" + "$randomQuote"
        val database = Firebase.database //Create a Firebase object variable
        val myRef = database.getReference(messageColumnInFirebase)
        val quoteTextView = findViewById<TextView>(R.id.quoteTextView) as TextView

        // Read from the database via listener
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                val value = dataSnapshot.getValue<String>()
                quoteTextView.setText(value)
                Log.d(TAG, "Value is: $value")
            }
            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException())
            }
        })
    }
/*
    //Write a new quote to the Realtime Datatbase
    fun writeQuote() {
        val messageColumnInFirebase = "quoteToBeDisplayed"
        //START WRITING MESSAGE TO DB
        //Writing to your database.
        val database = Firebase.database //Create a Firebase object variable
        val myRef = database.getReference(messageColumnInFirebase)
        myRef.setValue("You are the master of your judgements, your decisions, and your actions.")
    }
            */
}