import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.myapplication.JobOrder

class JobOrderDatabase(context: Context) : SQLiteOpenHelper(context, "jobs.db", null, 4) {

    // Data class object for job details pertaining to drops.
    data class JobDetail(
        val detailId: Int,
        val jobNum: Int,
        val taskDescription: String,
        val status: String
    )

    override fun onCreate(db: SQLiteDatabase) {
        // Create Table 1 containing main information pertaining to Job Order
        db.execSQL(
            """CREATE TABLE JobOrders (
                job_num INTEGER PRIMARY KEY AUTOINCREMENT,
                date TEXT NOT NULL,
                company TEXT NOT NULL,
                address TEXT)"""
//                manager_name TEXT)"""
        )

        // Table 2: JobDetails for storing task descriptions and statues
        db.execSQL(
            """CREATE TABLE JobDetails (
                detail_id INTEGER PRIMARY KEY AUTOINCREMENT,
                job_num INTEGER,
                task_description TEXT,
                status TEXT,
                FOREIGN KEY(job_num) REFERENCES JobOrders(job_num))"""
        )

    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS JobDetails")
        db.execSQL("DROP TABLE IF EXISTS JobOrders")
        onCreate(db)
    }


    // Insert a JobOrder into the database
    fun insertJobOrder(jobOrder: JobOrder): Boolean {
        val values = ContentValues().apply {
            put("date", jobOrder.date)
            put("company", jobOrder.company)
            put("address", jobOrder.address)
//            put("manager_name", jobOrder.manager_name)
        }
        val result = writableDatabase.insert("JobOrders", null, values)
        return result != -1L
    }

    // Insert a JobDetail into the database
    fun insertJobDetail(jobNum: Int, taskDescription: String, status: String): Boolean {
        val values = ContentValues().apply {
            put("job_num", jobNum)
            put("task_description", taskDescription)
            put("status", status)
        }
        val result = writableDatabase.insert("JobDetails", null, values)
        return result != -1L
    }

    // Delete a JobOrder by its ID
    fun deleteJobOrderById(jobNum: Int): Boolean {
        val result = writableDatabase.delete("JobOrders", "job_num=?", arrayOf(jobNum.toString()))
        return result > 0
    }

    // Retrieve all JobOrders from the database
    fun getAllJobOrders(): List<JobOrder> {
        val cursor = readableDatabase.rawQuery("SELECT * FROM JobOrders", null)
        val jobs = mutableListOf<JobOrder>()

        while (cursor.moveToNext()) {
            val job = JobOrder(
                job_num = cursor.getInt(0),
                date = cursor.getString(1),
                company = cursor.getString(2),
                address = cursor.getString(3),
//                manager_name = cursor.getString(4)
            )
            jobs.add(job)
        }
        cursor.close()
        return jobs
    }

    // Retrieve all JobDetails from the database
    fun getAllJobDetails(): List<JobDetail> {
        val cursor = readableDatabase.rawQuery("SELECT * FROM JobDetails", null)
        val details = mutableListOf<JobDetail>()

        while (cursor.moveToNext()) {
            val detail = JobDetail(
                detailId = cursor.getInt(0),
                jobNum = cursor.getInt(1),
                taskDescription = cursor.getString(2),
                status = cursor.getString(3)
            )
            details.add(detail)
        }
        cursor.close()
        return details
    }


}