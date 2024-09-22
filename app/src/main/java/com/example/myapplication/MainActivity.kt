package com.example.myapplication

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.JobOrder  // Import your JobOrder class
import com.example.myapplication.ListManager  // Import your ListManager class

// Author: Morgan Molyneaux
// Program Purpose: Demonstrate a basic understanding of using buttons and event listeners in Kotlin.
// This program further explores the use of event listeners to manipulate data within text fields
// and subsequently display the modified data to the user.

class MainActivity : AppCompatActivity() {

    // Declare UI elements (buttons) and shared preferences
    private lateinit var clearButton: Button
    private lateinit var populateButton: Button
    private lateinit var printButton: Button
    private lateinit var deleteButton: Button
    private lateinit var sharedPreferences: SharedPreferences

    // Declare ListManager to manage the job orders
    private val listManager = ListManager()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize Shared Preferences
        // https://developer.android.com/develop/ui/views/components/settings/use-saved-values
        sharedPreferences = getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)

        // Initialize UI buttons
        clearButton = findViewById(R.id.clear_button)
        populateButton = findViewById(R.id.populate_button)
        printButton = findViewById(R.id.print_button)
        deleteButton = findViewById(R.id.delete_button)

        // Set onClick listeners for buttons
        clearButton.setOnClickListener { clearJobs() }
        populateButton.setOnClickListener { populateJobs() }
        printButton.setOnClickListener { printJobs() }
        deleteButton.setOnClickListener { deleteJob() }

        // Adding a try-catch block to catch any potential issues
        // Essential line as trying to initialize object on App Startup will cause a crash due to the
        // Session storage not existing yet.
        try {
            val savedJobCount = sharedPreferences.getInt("total_jobs", 0)
            val totalJobsTextView: TextView = findViewById(R.id.main_total_jobs_display)
            totalJobsTextView.text = savedJobCount.toString()
        } catch (e: Exception) {
            Log.e("MainActivity", "SharedPreferences fetch error:", e)
        }
    }

    // Action for "Clear Jobs" button - Clears all jobs
    private fun clearJobs() {
        listManager.clearList()  // Clear the list of jobs
        Log.i("MainActivity", "All jobs cleared")
        val totalJobsTextView: TextView = findViewById(R.id.main_total_jobs_display)
        totalJobsTextView.text = "0" // Reset the total job display to 0
    }

    // Action for "Populate Jobs" button - Adds predefined jobs
    private fun populateJobs() {
        val jobData = """
            0; April 10, 2024; 5M; Eastberry Lane; Doofus Mcgee
            1; May 5, 2024; Alpha Corp; Sunset Boulevard; John Doe
            2; June 12, 2024; Beta LLC; Maple Street; Jane Smith
            3; July 20, 2024; Gamma Inc; Oak Avenue; Rick Sanchez
            4; August 15, 2024; Delta Enterprises; Pine Road; Morty Smith
            5; September 1, 2024; Epsilon Co; Cedar Drive; Summer Smith
            6; October 30, 2024; Zeta Solutions; Birch Lane; Beth Smith
        """.trim()
        listManager.createList(jobData)
        Log.i("MainActivity", "Job list populated")
        updateJobCountDisplay()
    }

    // Action for "Print Jobs" button - Prints the list of jobs to the log
    private fun printJobs() {
        listManager.printList()
        Log.i("MainActivity", "Job list printed")
    }

    // Action for "Delete Job" button - Deletes a job by ID
    private fun deleteJob() {
        val jobIdToDelete = 3  // Example hardcoded ID; modify to delete dynamic IDs if needed
        listManager.deleteItemByID(jobIdToDelete)
        Log.i("MainActivity", "Job with ID $jobIdToDelete deleted")
        updateJobCountDisplay()
    }

    // Helper function to update the job count display
    private fun updateJobCountDisplay() {
        val totalJobsTextView: TextView = findViewById(R.id.main_total_jobs_display)
        totalJobsTextView.text = listManager.getTotalJobs().toString()

        // Save the new job count in SharedPreferences
        with(sharedPreferences.edit()) {
            putInt("total_jobs", listManager.getTotalJobs())
            apply()
        }
    }
}
