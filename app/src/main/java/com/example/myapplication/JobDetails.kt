package com.example.myapplication

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class JobDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_job_details)

        // Find views and populate them with said data pulled from intent
        val jobNumView: TextView = findViewById(R.id.job_num)
        val companyView: TextView = findViewById(R.id.company_name)
        val addressView: TextView = findViewById(R.id.address)
//        val managerNameView: TextView = findViewById(R.id.manager_name)
        val dateView: TextView = findViewById(R.id.date)


        val jobNum = intent.getIntExtra("job_num", -1)
        val company = intent.getStringExtra("company")
        val address = intent.getStringExtra("address")
//        val managerName = intent.getStringExtra("manager_name")
        val date = intent.getStringExtra("date")


        jobNumView.text = "Job Number: $jobNum"
        companyView.text = "Company: $company"
        addressView.text = "Address: $address"
//        managerNameView.text = "Manager: $managerName"
        dateView.text = "Date: $date"
    }
}
