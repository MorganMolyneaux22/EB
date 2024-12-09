package com.example.myapplication

import JobOrderDatabase
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class NewProduction : AppCompatActivity() {
    lateinit var save_button: Button // lateinit (essentially null safety feature)
    lateinit var job_number_edit: EditText
    lateinit var segment_id: EditText
    lateinit var new_foreman_name: EditText
    lateinit var new_address_field: EditText
    private lateinit var jobOrderDatabase: JobOrderDatabase

    @SuppressLint("MissingInflatedId") // Stops IDE from throwing error when fetching by ID
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_production)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main_table)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Initialize JobOrderDatabase
        jobOrderDatabase = JobOrderDatabase(this)

        // Initialize UI elements
        save_button = findViewById(R.id.new_save_button) // pointer for button in App
        job_number_edit = findViewById(R.id.new_job_number)
        segment_id = findViewById(R.id.new_id_number)
        new_foreman_name = findViewById(R.id.new_foreman_name)
        new_address_field = findViewById(R.id.new_address_field)

        // Set the save button's click listener
        save_button.setOnClickListener {
            // Converts values to INT (even though they already should be).
            try {
                val jobNumber = job_number_edit.text.toString().toInt()
                val segment = segment_id.text.toString().toInt()
                val foremanName = new_foreman_name.text.toString()
                val address = new_address_field.text.toString()
                do_save_button(jobNumber, segment, foremanName, address) // Pass the integer value to the function
            } catch (e: Exception) {
                Log.i("Error Thrown", "$e")
            }
        }
    }

    // Function to save the new production and return to MainActivity
    fun do_save_button(jobNumber: Int, segment: Int, foremanName: String, address: String) {
        // Log the job number to the console
//        Log.i("CS3680", "Saving production with Job Number: $jobNumber, ID: $segment, Address: $address, Foreman: $foremanName")
        // Create a JobOrder object with the provided data
        val newJobOrder = JobOrder(
            job_num = jobNumber,
            date = "Today",
            company = "Segment $segment",
            address = address
        )

        // Insert the JobOrder into the database
        jobOrderDatabase.insertJobOrder(newJobOrder)

        // Log the job details to verify insertion
        Log.i("CS3680", "Saved job: $newJobOrder")

        // Pass intent values back to home screen for possible future use (maybe)
        val intent = Intent(this, MainActivity::class.java).apply {
            putExtra("Job_Number", jobNumber)
            putExtra("Segment_ID", segment)
            putExtra("Foreman_Name", foremanName)
            putExtra("Address", address)
        }
        startActivity(intent)
    }
}
