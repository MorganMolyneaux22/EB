package com.example.myapplication

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
// Morgan Molyneaux, Purpose of Application: This page will serve as the overview for production created by the user.
// Allowing the user to view a detailed table of all files, with ability to edit, search by  and view basic overview of file details.

class ProductionOverview : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_production_overview)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main_table)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
//        // Retrieve the values from the intent from new production
//        val jobNumber = intent.getIntExtra("Job_Number", -1)
//        val segmentId = intent.getIntExtra("Segment_ID", -1)
//        val quantity = intent.getIntExtra("Quantity", -1)
//        Log.i("MainCS3680", "Values from new production - Job Number: $jobNumber, Segment ID: $segmentId, Quantity: $quantity")
//        // Check if the values are different from their default values (-1) before logging
//        if (jobNumber != -1 && segmentId != -1 && quantity != -1) {
//            Log.i("MainCS3680", "Values from new production - Job Number: $jobNumber, Segment ID: $segmentId, Quantity: $quantity")
//        } else {
//            Log.i("MainCS3680", "No values received from new production")
//        }
    }
}