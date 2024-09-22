package com.example.myapplication// Author: Morgan Molyneaux
// Purpose of program: Demonstrate basic knowledge and skill utilizing Kotlin classes.
// Program mocks a basic app demo where the user can see all jobs created that day in a list.

class JobOrder(val job_num: Int, val date: String, val company: String, val address: String, val manager_name: String) {
    override fun toString(): String {
        return "JobOrder(job_num=$job_num, date='$date', company='$company', address='$address', manager_name='$manager_name')"
    }
}

class ListManager {
    private val jobOrders = mutableListOf<JobOrder>()

    fun createList(jobData: String) {
        val jobLines = jobData.split("\n")
        for (line in jobLines) {
            val properties = line.trim().split(";").map { it.trim() }
            val jobOrder = JobOrder(
                job_num = properties[0].toInt(),
                date = properties[1],
                company = properties[2],
                address = properties[3],
                manager_name = properties[4]
            )
            jobOrders.add(jobOrder)
        }
    }

    fun getItemByIndex(index: Int): JobOrder? {
        return if (index in jobOrders.indices) jobOrders[index] else null
    }

    fun getItemByID(jobNum: Int): JobOrder? {
        return jobOrders.find { it.job_num == jobNum }
    }

    fun addItem(jobOrder: JobOrder) {
        jobOrders.add(jobOrder)
    }

    fun deleteItemByID(jobNum: Int) {
        val jobOrder = getItemByID(jobNum)
        if (jobOrder != null) {
            jobOrders.remove(jobOrder)
        } else {
            println("ID does not exist.")
        }
    }

    // Add this function to clear the list of jobs
    fun clearList() {
        jobOrders.clear()
    }

    fun printList() {
        val result = jobOrders.joinToString(separator = "\n") { jobOrder ->
            "Job ${jobOrder.job_num}: ${jobOrder.date}, ${jobOrder.company}, ${jobOrder.address}, ${jobOrder.manager_name}"
        }
        println(result)
    }

    // Function to return the total number of jobs
    fun getTotalJobs(): Int {
        return jobOrders.size
    }
}

