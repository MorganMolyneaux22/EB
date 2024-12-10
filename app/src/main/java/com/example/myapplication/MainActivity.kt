package com.example.myapplication

import JobOrderDatabase
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
// Morgan Molyneaux, CS 3680, Purpose of program:
// This app allows users to manage job orders and task details using an SQLite database schema.
// Users can add, view, and delete job orders and associated task information through the interface.
// <----- Mock job data is pre-loaded into the database for demonstration purposes. ---->
// <----- All code is of my own design and creation unless otherwise stated ----->

class MainActivity : AppCompatActivity() {

    // Declare UI elements and shared preferences
    private lateinit var clearButton: Button
    private lateinit var populateButton: Button
    private lateinit var printButton: Button
    private lateinit var deleteButton: Button
    private lateinit var newProductionButton: Button
    private lateinit var deleteIdField: EditText
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var searchButton: Button
    private lateinit var searchField: EditText

    // Initialize ListManager and JobOrderDatabase
    private val listManager = ListManager()
    private lateinit var jobOrderDatabase: JobOrderDatabase

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize Shared Preferences
        sharedPreferences = getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)

        // Initialize JobOrderDatabase
        jobOrderDatabase = JobOrderDatabase(this)

        // Initialize UI buttons
        clearButton = findViewById(R.id.clear_button)
        populateButton = findViewById(R.id.populate_button)
        printButton = findViewById(R.id.print_button)
        deleteButton = findViewById(R.id.delete_button)
        deleteIdField = findViewById(R.id.job_id_text_field)
        newProductionButton = findViewById(R.id.new_production)
        searchButton = findViewById(R.id.search_button)
        searchField = findViewById(R.id.company_name_text_field)

        // Set onClick listeners for buttons
        clearButton.setOnClickListener { clearJobs() }
        populateButton.setOnClickListener { populateJobsInDatabase() }
//        printButton.setOnClickListener { printJobsFromDatabase() }
        deleteButton.setOnClickListener { deleteJob() }
        newProductionButton.setOnClickListener {
            val intent = Intent(this, NewProduction::class.java)
            startActivity(intent)
        }
        printButton.setOnClickListener {
            printJobsFromDatabase()
            val intent = Intent(this, ProductionOverview::class.java)
            startActivity(intent)
        }

        // Search button listener for query by company
        searchButton.setOnClickListener {
            val company = searchField.text.toString()
            if (company.isNotEmpty()) {
                queryJobsByCompany(company)
                queryJobsWithDetails()
            } else {
                Log.e("MainActivity", "Search field is empty.")
            }
        }


        // Load and display saved job count
        updateJobCountDisplay()
    }

    // Functions that clears jobs from DB list and resets the counter back to 0.
    private fun clearJobs() {
        listManager.clearList()
        jobOrderDatabase.writableDatabase.execSQL("DELETE FROM JobOrders")
        Log.i("MainActivity", "All jobs cleared from list and database")
        updateJobCountDisplay()
    }

    // Injects mock data into DB data for JobOrders
    private fun populateJobsInDatabase() {
        val mockJobs = listOf(
            JobOrder(4, "April 10, 2024", "5M", "Eastberry Lane"),
            JobOrder(45, "May 5, 2024", "BJC", "Sunset Boulevard"),
            JobOrder(22, "June 12, 2024", "Beta LLC", "Maple Street"),
            JobOrder(33, "July 3, 2024", "Omega Networks", "Riverbend Road"),
            JobOrder(16, "August 8, 2024", "FiberWave", "Pine Street"),
            JobOrder(87, "September 15, 2024", "DataLink", "Broadway Avenue"),
            JobOrder(23, "October 20, 2024", "SignalWorks", "Cypress Lane"),
            JobOrder(9, "November 11, 2024", "TeleNet", "Sunrise Boulevard"),
            JobOrder(54, "December 1, 2024", "QuickConnect", "Elm Street"),
            JobOrder(37, "January 14, 2025", "LightPath", "Willow Drive"),
            JobOrder(12, "February 10, 2025", "Nova Fiber", "Ash Court"),
            JobOrder(68, "March 23, 2025", "BrightLine", "Oak Avenue"),
            JobOrder(99, "March 21, 2025", "5M", "Oak Avenue")

        )


        mockJobs.forEach { job ->
            jobOrderDatabase.insertJobOrder(job)
        }
        // Inject Mock details into JobDetails table
        // Which is having the problem of not wanting to work as the Table wont initialize.
        // Thus resulting in a console error whenever I try to populate the list
        val mockJobDetails = listOf(
            Triple(4, "Install cables", "In Progress"),
            Triple(45, "Network inspection", "Completed"),
            Triple(22, "Router maintenance", "Pending"),
            Triple(33, "Fiber installation", "In Progress"),
            Triple(16, "Signal calibration", "Completed"),
            Triple(87, "Data transmission test", "Pending"),
            Triple(23, "System update", "In Progress"),
            Triple(9, "Cable replacement", "Completed"),
            Triple(54, "Device setup", "In Progress"),
            Triple(37, "New connection", "Pending"),
            Triple(12, "Fiber optic cleaning", "Completed"),
            Triple(68, "Bandwidth analysis", "In Progress"),
            Triple(99, "Install additional cables", "Pending")
        )

        mockJobDetails.forEach { (jobNum, task, status) ->
            jobOrderDatabase.insertJobDetail(jobNum, task, status)
        }

        Log.i("MainActivity", "Mock jobs populated into database")
        updateJobCountDisplay()
    }

    // Prints all jobsOrder from DB to the console for easier viewing
    // In the even the app UI isn't displaying correctly
    private fun printJobsFromDatabase() {
        val jobs = jobOrderDatabase.getAllJobOrders()

        // Add jobs to ListManager and log them to the console
        listManager.createListFromDatabase(jobs)
        listManager.getAllItems().forEach { job ->
            Log.i("CS3680", job.toString())
        }

        Log.i("CS3680", "All jobs printed to the console")
    }

    // Action for "Delete Job" button to delete a job by ID
    private fun deleteJob() {
        val jobIdText = deleteIdField.text.toString()
        if (jobIdText.isNotEmpty()) {
            try {
                val jobIdToDelete = jobIdText.toInt()
                val deleted = jobOrderDatabase.deleteJobOrderById(jobIdToDelete)
                if (deleted) {
                    Log.i("MainActivity", "Job with ID $jobIdToDelete deleted")
                    updateJobCountDisplay()
                } else {
                    Log.e("MainActivity", "No job found with ID $jobIdToDelete")
                }
            } catch (e: NumberFormatException) {
                Log.e("MainActivity", "Invalid job ID entered", e)
            }
        } else {
            Log.e("MainActivity", "Job ID field is empty")
        }
    }
    // Prints all jobsOrder from DB to the console for easier viewing
    // In the even the app UI isn't displaying correctly
    private fun printJobDetailsToConsole() {
        val jobDetails = jobOrderDatabase.getAllJobDetails()

        jobDetails.forEach { detail ->
            Log.i("CS3680", "Detail: $detail")
        }

        Log.i("CS3680", "All job details printed to the console")
    }

    // Helper function to update the job count display
    private fun updateJobCountDisplay() {
        val totalJobs = jobOrderDatabase.getAllJobOrders().size
        val totalJobsTextView: TextView = findViewById(R.id.drops_completed_value)
        totalJobsTextView.text = totalJobs.toString()

        // Save the new job count in SharedPreferences
        with(sharedPreferences.edit()) {
            putInt("total_jobs", totalJobs)
            apply()
        }
    }

    fun queryJobsByCompany(company: String) {
        // Query that will return all companies with name given by user on homescreen
        val query = "SELECT * FROM JobOrders WHERE company = ?"
        val cursor = jobOrderDatabase.readableDatabase.rawQuery(query, arrayOf(company))

        if (cursor.count > 0) {
            val jobs = mutableListOf<JobOrder>()

            // Iterate over all rows and collect jobs into a list
            while (cursor.moveToNext()) {
                val jobOrder = JobOrder(
                    job_num = cursor.getInt(0),
                    date = cursor.getString(1),
                    company = cursor.getString(2),
                    address = cursor.getString(3)
                )
                jobs.add(jobOrder)
            }

            // Log each job found for the given company
            jobs.forEach { job ->
                Log.i("CS3680", "Query 1: Found job: $job")
            }

        } else {
            Log.i("CS3680", "Query 1: No jobs found for company: $company")
        }
        cursor.close()
    }


    fun queryJobsWithDetails() {
        // Joins information from Table 1 & 2 and joins together into query statement.
        // The following will give us the job #, Date, Company, Task assigned and the Status of said job
        val query = """
        SELECT JobOrders.job_num, JobOrders.company, JobOrders.address, 
               JobDetails.task_description, JobDetails.status
        FROM JobOrders
        INNER JOIN JobDetails ON JobOrders.job_num = JobDetails.job_num
    """
        val cursor = jobOrderDatabase.readableDatabase.rawQuery(query, null)

        if (cursor.count > 0) {
            while (cursor.moveToNext()) {
                val jobNum = cursor.getInt(0)
                val company = cursor.getString(1)
                val address = cursor.getString(2)
                val taskDescription = cursor.getString(3)
                val status = cursor.getString(4)

                Log.i("CS3680", "Query 2: Job #$jobNum, Company: $company, Address: $address, Task: $taskDescription, Status: $status")
            }
        } else {
            Log.i("CS3680", "Query 2: No job details found.")
        }
        cursor.close()
    }


}




//JobOrder(4, "April 10, 2024", "5M", "Eastberry Lane", "Morgan Molyneaux"),
//JobOrder(45, "May 5, 2024", "BJC", "Sunset Boulevard", "Morgan Molyneaux"),
//JobOrder(22, "June 12, 2024", "Beta LLC", "Maple Street", "Morgan Molyneaux"),
//JobOrder(33, "July 3, 2024", "Omega Networks", "Riverbend Road", "Morgan Molyneaux"),
//JobOrder(16, "August 8, 2024", "FiberWave", "Pine Street", "Morgan Molyneaux"),
//JobOrder(87, "September 15, 2024", "DataLink", "Broadway Avenue", "Morgan Molyneaux"),
//JobOrder(23, "October 20, 2024", "SignalWorks", "Cypress Lane", "Morgan Molyneaux"),
//JobOrder(9, "November 11, 2024", "TeleNet", "Sunrise Boulevard", "Morgan Molyneaux"),
//JobOrder(54, "December 1, 2024", "QuickConnect", "Elm Street", "Morgan Molyneaux"),
//JobOrder(37, "January 14, 2025", "LightPath", "Willow Drive", "Morgan Molyneaux"),
//JobOrder(12, "February 10, 2025", "Nova Fiber", "Ash Court", "Morgan Molyneaux"),
//JobOrder(68, "March 23, 2025", "BrightLine", "Oak Avenue", "Morgan Molyneaux")