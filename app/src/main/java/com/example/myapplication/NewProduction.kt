package com.example.myapplication

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
    lateinit var save_button : Button // lateinit (essentially null safety feature)
    lateinit var job_number_edit : EditText
    lateinit var segment_id : EditText
    lateinit var quantity_value  : EditText
    @SuppressLint("MissingInflatedId") // Stops IDE from throwing error when fetching by ID

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_new_production)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main_table)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        // Listen for user click on save button
        save_button = findViewById(R.id.new_save_button) // pointer for button in App
        job_number_edit = findViewById(R.id.new_job_number)
        segment_id = findViewById(R.id.new_id_number)
        quantity_value = findViewById(R.id.new_quantity_number)

        save_button.setOnClickListener {
            // Converts values to INT (even though they already should be).
            try {
                val jobNumber = job_number_edit.text.toString().toInt()
                val segment = segment_id.text.toString().toInt()
                val quantity = quantity_value.text.toString().toInt()
                do_save_button(jobNumber, segment, quantity) // Pass the integer value to the function
            }
            catch (e: Exception) {
                Log.i("Error Thrown", " $e")
            }
        }


    }

    fun do_save_button(jobNumber: Int, segment: Int, quantity: Int) {
        // Log the job number to the console
        Log.i("CS3680", "Saving production with Job Number: $jobNumber, ID: $segment, Quantity $quantity")

        // Saves data from new production Page, and reroutes to Home Screen (MainActivity)
        val intent = Intent(this, MainActivity::class.java)
//        intent.putExtras("Job_Number", jobNumber, "Segment_ID", segment, "Quantity", quantity)
        intent.putExtra("Job_Number", jobNumber)
        intent.putExtra("Segment_ID", segment)
        intent.putExtra("Quantity", quantity)
        startActivity(intent)
    }
}