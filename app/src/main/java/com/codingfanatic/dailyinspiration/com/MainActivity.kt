package com.codingfanatic.dailyinspiration.com

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "KotlinActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

         readQuote()
    }

    //Read a String from the Realtime Database and use it as the TextView
    fun readQuote() {

        //Get the day of the week
        val dateFormat: DateFormat = SimpleDateFormat("EEEE")
        val today = dateFormat.format(Date()).toUpperCase()
        Log.d(TAG, today)

        //Get the number for the day of the week
        val dayNumber = when (today) {
            "SUNDAY" -> "0"
            "MONDAY" -> "1"
            "TUESDAY" -> "2"
            "WEDNESDAY" -> "3"
            "THURSDAY" -> "4"
            "FRIDAY" -> "5"
            "SATURDAY" -> "6"
            else -> ""
        }

        //Create the String for fetching the Realtime Database row
        val messageColumnInFirebase = "quoteToBeDisplayed" + "$dayNumber"

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
}