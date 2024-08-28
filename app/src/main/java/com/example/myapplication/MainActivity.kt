package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    lateinit var new_button : Button // lateinit (essentially null safety feature)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main_table)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        new_button = findViewById(R.id.new_production) // pointer for button in App
        new_button.setOnClickListener { do_new_button() }

        // Retrieve the values from the intent from new production
        val jobNumber = intent.getIntExtra("Job_Number", -1)
        val segmentId = intent.getIntExtra("Segment_ID", -1)
        val quantity = intent.getIntExtra("Quantity", -1)
        Log.i("MainCS3680", "Values from new production - Job Number: $jobNumber, Segment ID: $segmentId, Quantity: $quantity")
        // Check if the values are different from their default values (-1) before logging
        if (jobNumber != -1 && segmentId != -1 && quantity != -1) {
            Log.i("MainCS3680", "Values from new production - Job Number: $jobNumber, Segment ID: $segmentId, Quantity: $quantity")
        } else {
            Log.i("MainCS3680", "No values received from new production")
        }

    }

    fun do_new_button(){
        Log.i("CS3680", "Creating New Production")
        val intent = Intent(this, NewProduction:: class.java)
        startActivity(intent)
    }

    fun add_quantiies(){

    }

    fun list_total_jobs(){

    }



}