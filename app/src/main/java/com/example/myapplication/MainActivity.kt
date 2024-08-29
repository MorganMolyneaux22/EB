package com.example.myapplication

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    lateinit var new_button: Button
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // Initialize Shared Preferences
        // https://developer.android.com/develop/ui/views/components/settings/use-saved-values
        sharedPreferences = getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)

        // Adding a try-catch block to catch any potential issues
        // Essential line as trying to initalize object on App Startup will cause a crash due to the
        // Session storage not existing yet.
        try {
            val saved_job_count = sharedPreferences.getInt("total_jobs", 0)
            val saved_quantity_count = sharedPreferences.getInt("total_quantity", 0)
            Log.d("MainActivity", "Saved job count: $saved_job_count")
            Log.d("MainActivity", "Saved quantity count: $saved_quantity_count")
            val total_jobs: TextView = findViewById(R.id.main_total_jobs_display)
            total_jobs.text = saved_job_count.toString()
        } catch (e: Exception) {
            Log.e("MainActivity", "SharedPreferences fetch error:", e)
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main_table)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        // Set listener to track and find when new button is clicked.
        new_button = findViewById(R.id.new_production)
        new_button.setOnClickListener { do_new_button() }

        // Retrieve the values from the intent from new production page that is passed over
        val jobNumber = intent.getIntExtra("Job_Number", -1)
        val segmentId = intent.getIntExtra("Segment_ID", -1)
        val quantity = intent.getIntExtra("Quantity", -1)
        // Log.i("MainCS3680", "Values from new production - Job Number: $jobNumber, Segment ID: $segmentId, Quantity: $quantity")

        // Check for valid user input from New Production page and avoid adding jobs completed when not actually.
        if (jobNumber != -1 && segmentId != -1 && quantity != -1) {
            Log.i("MainCS3680", "Values from new production - Job Number: $jobNumber, Segment ID: $segmentId, Quantity: $quantity")
            list_total_jobs()
            add_quantities(quantity)
        } else {
            Log.i("MainCS3680", "No values received from new production")
        }
    }

    fun do_new_button() {
        // Moves user from MainActivity Page to New Production
        Log.i("CS3680", "Creating New Production")
        val intent = Intent(this, NewProduction::class.java)
        startActivity(intent)
    }

    fun list_total_jobs() {
        // List the Total Jobs compeleted for the day, and increments by 1 to update to SharePreference Session storage
        val total_jobs: TextView = findViewById(R.id.main_total_jobs_display)
        val current_value = total_jobs.text.toString().toInt()

        val new_value = current_value + 1

        // Save the new value in Shared Preferences
        with(sharedPreferences.edit()) {
            putInt("total_jobs", new_value)
            apply()
        }

        total_jobs.text = new_value.toString()
    }

    fun add_quantities(quantity: Int) {
        // Adds the quantities from all production reports created for that day (or each span).
        val total_footage : TextView = findViewById(R.id.main_total_footage_display)
        val saved_quantity = sharedPreferences.getInt("total_quantity", 0)

        val new_value = saved_quantity + quantity

        // Save the new value in Shared Preferences
        with(sharedPreferences.edit()) {
            putInt("total_quantity", new_value)
            apply()
        }
        // Set the total_footage equal to the new footage after adding
        total_footage.text = new_value.toString()

    }
}