package com.example.myapplication

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class JobAdapter(
    private val context: Context,
    private val jobList: List<JobOrder>
) : RecyclerView.Adapter<JobAdapter.JobViewHolder>() {

    inner class JobViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val jobNum: TextView = itemView.findViewById(R.id.job_num)
        val companyName: TextView = itemView.findViewById(R.id.company_name)
        val address: TextView = itemView.findViewById(R.id.address)
        val date: TextView = itemView.findViewById(R.id.date)

        init {
            // Pass in values to jobDetails that are used in display additional information within table menu
            itemView.setOnClickListener {
                val position = adapterPosition
                val job = jobList[position]
                val intent = Intent(context, JobDetailActivity::class.java).apply {
                    putExtra("job_num", job.job_num)
                    putExtra("company", job.company)
                    putExtra("address", job.address)
                    putExtra("date", job.date)
                }
                context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JobViewHolder {
        // Grabs the job_item view to display the correct information
        val view = LayoutInflater.from(context).inflate(R.layout.job_item, parent, false)
        return JobViewHolder(view)
    }

    override fun onBindViewHolder(holder: JobViewHolder, position: Int) {
        val job = jobList[position]
        holder.jobNum.text = "Job #: ${job.job_num}"
        holder.companyName.text = "Company: ${job.company}"
        holder.address.text = "Address: ${job.address}"
        holder.date.text = "Date: ${job.date}"
    }

    override fun getItemCount(): Int = jobList.size
}
