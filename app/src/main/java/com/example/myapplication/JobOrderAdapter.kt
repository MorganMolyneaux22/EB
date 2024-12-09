package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class JobOrderAdapter(
    private val jobOrders: List<JobOrder>,
    private val itemClickListener: (JobOrder) -> Unit
) : RecyclerView.Adapter<JobOrderAdapter.JobOrderViewHolder>() {

    class JobOrderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val jobNum: TextView = view.findViewById(R.id.job_num)
        // Uncomment to display all data within ProductionOverview
//        val companyName: TextView = view.findViewById(R.id.company_name)
//        val address: TextView = view.findViewById(R.id.address)
//        val managerName: TextView = view.findViewById(R.id.manager_name)
//        val date: TextView = view.findViewById(R.id.date)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JobOrderViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_production_overview, parent, false)
        return JobOrderViewHolder(view)
    }

    override fun onBindViewHolder(holder: JobOrderViewHolder, position: Int) {
        val jobOrder = jobOrders[position]
        holder.jobNum.text = "Job Number: ${jobOrder.job_num}"
//        holder.companyName.text = "Company: ${jobOrder.company}"
//        holder.address.text = "Address: ${jobOrder.address}"
//        holder.managerName.text = "Manager: ${jobOrder.manager_name}"
//        holder.date.text = "Date: ${jobOrder.date}"

        // Set up click listener for the item
        holder.itemView.setOnClickListener {
            itemClickListener(jobOrder)
        }
    }

    override fun getItemCount(): Int = jobOrders.size
}
