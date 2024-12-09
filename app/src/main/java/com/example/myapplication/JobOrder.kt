package com.example.myapplication
// Author: Morgan Molyneaux
// Purpose of program: Demonstrate basic knowledge and skill utilizing Kotlin classes.
// This program mocks a basic app demo where the user can see all jobs created that day in a list.

data class JobOrder(
    val job_num: Int? = null,
    val date: String,
    val company: String,
    val address: String,
//    val manager_name: String
) {
    override fun toString(): String {
//        return "JobOrder(id=$job_num, date='$date', company='$company', address='$address', manager_name='$manager_name')"
        return "JobOrder(id=$job_num, date='$date', company='$company', address='$address')"
    }
}

class ListManager {
    private val jobOrders = mutableListOf<JobOrder>()

    fun createList(jobData: String) {
        // Creates and appends data passed from screen into JobOrder object.
        val jobLines = jobData.split("\n")
        for (line in jobLines) {
            val properties = line.trim().split(";").map { it.trim() }
            val jobOrder = JobOrder(
                job_num = null,
                date = properties[1],
                company = properties[2],
                address = properties[3],
//                manager_name = properties[4]
            )
            jobOrders.add(jobOrder)
        }
    }


    fun createListFromDatabase(dbJobs: List<JobOrder>) {
        jobOrders.clear() // Clear the list to avoid duplication
        jobOrders.addAll(dbJobs) // Add all jobs from the database to the list
    }

    fun getAllItems(): List<JobOrder> = jobOrders.toList()

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

    fun clearList() {
        jobOrders.clear()
    }

    fun printListToLog(tag: String) {
        if (jobOrders.isEmpty()) {
            println("No jobs to print.")
        } else {
            jobOrders.forEach { jobOrder ->
                println("$tag: ${jobOrder.toString()}")
            }
        }
    }

    fun getTotalJobs(): Int {
        return jobOrders.size
    }

}









//class JobOrder(val job_num: Int, val date: String, val company: String, val address: String, val manager_name: String) {
//    override fun toString(): String {
//        return "JobOrder(job_num=$job_num, date='$date', company='$company', address='$address', manager_name='$manager_name')"
//    }
//}
//
//data class JobOrder(
//    val id: Int,
//    val date: String,
//    val company: String,
//    val address: String,
//    val managerName: String
//)
//
//
//class ListManager {
//    private val jobOrders = mutableListOf<JobOrder>()
//
//    fun createList(jobData: String) {
//        // Creates and appends data passed from screen into JobOrder object.
//        val jobLines = jobData.split("\n")
//        for (line in jobLines) {
//            val properties = line.trim().split(";").map { it.trim() }
//            val jobOrder = JobOrder(
//                job_num = properties[0].toInt(),
//                date = properties[1],
//                company = properties[2],
//                address = properties[3],
//                manager_name = properties[4]
//            )
//            jobOrders.add(jobOrder)
//        }
//    }
//
//    fun getAllItems(): List<JobOrder> {
//        // Returns all objects from class
//        return jobOrders.toList()
//    }
//
//    fun getItemByID(jobNum: Int): JobOrder? {
//        // Returns JobOrder object given unqiue job id in field.
//        return jobOrders.find { it.job_num == jobNum }
//    }
//
//    fun addItem(jobOrder: JobOrder) {
//        // Adds JobOrder object into list.
//        jobOrders.add(jobOrder)
//    }
//
//    fun deleteItemByID(jobNum: Int) {
//        // Given job ID, delete marked job within list if applicable.
//        val jobOrder = getItemByID(jobNum)
//        if (jobOrder != null) {
//            jobOrders.remove(jobOrder)
//        } else {
//            println("ID does not exist.")
//        }
//    }
//
//    fun clearList() {
//        // Clears list of all jobs.
//        jobOrders.clear()
//    }
//
//    // Function to print jobs to the log for Android's logcat
//    fun printListToLog(tag: String) {
//        if (jobOrders.isEmpty()) {
//            println("No jobs to print.") // why does this not reach logic?
//        } else {
//            jobOrders.forEach { jobOrder ->
//                println("$tag: ${jobOrder.toString()}")
//            }
//        }
//    }
//
//    fun getTotalJobs(): Int {
//        // Get total number of jobs (list size) and return int value.
//        return jobOrders.size
//    }
//
//
//}
//    fun printList() {
//        val result = jobOrders.joinToString(separator = "\n") { jobOrder ->
//            "Job ${jobOrder.job_num}: ${jobOrder.date}, ${jobOrder.company}, ${jobOrder.address}, ${jobOrder.manager_name}"
//        }
//        println(result)
//    }

//    fun getAllItems(): List<JobOrder> {
////        return jobOrders.toList()
////    }