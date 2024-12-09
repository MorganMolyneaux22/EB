package com.example.myapplication

import JobOrderDatabase
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ProductionOverview : AppCompatActivity() {

    private lateinit var jobOrderDatabase: JobOrderDatabase
    private lateinit var jobAdapter: JobAdapter
    private lateinit var backButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_jobs)

        val recyclerView: RecyclerView = findViewById(R.id.jobsRecyclerView)
        jobOrderDatabase = JobOrderDatabase(this)

        backButton = findViewById(R.id.back_button)

        // Set onclick listener for back button
        backButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent) }
        // Fetch jobs from the database
        val jobs = jobOrderDatabase.getAllJobOrders()

        // Set up the RecyclerView with the adapter
        jobAdapter = JobAdapter(this, jobs)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = jobAdapter
    }
}
