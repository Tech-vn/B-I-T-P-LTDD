package com.example.uthsmarttasks

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeActivity : AppCompatActivity() {

    private lateinit var rvTodayTasks: RecyclerView
    private lateinit var taskAdapter: TaskAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        rvTodayTasks = findViewById<RecyclerView>(R.id.rvTodayTasks)
        rvTodayTasks.layoutManager = LinearLayoutManager(this)
        taskAdapter = TaskAdapter(emptyList())
        rvTodayTasks.adapter = taskAdapter

        val tvSeeAll = findViewById<android.widget.TextView>(R.id.tvSeeAll)
        tvSeeAll.setOnClickListener {
            val intent = android.content.Intent(this, ListActivity::class.java)
            startActivity(intent)
        }

        fetchTasks()
    }

    private fun fetchTasks() {

        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val response = RetrofitClient.instance.getTasks()

                withContext(Dispatchers.Main) {
                    if (response.isSuccessful && response.body() != null) {
                        // Chuyển từ Map<String, Task> sang List<Task>
                        val taskMap = response.body()!!
                        val taskList = taskMap.values.toList()
                        taskAdapter.updateData(taskList)
                    } else {
                        Toast.makeText(this@HomeActivity, "Lỗi khi tải dữ liệu", Toast.LENGTH_SHORT).show()
                        Log.e("API_ERROR", "Response not successful: ${response.code()}")
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@HomeActivity, "Lỗi kết nối mạng", Toast.LENGTH_SHORT).show()
                    Log.e("API_EXCEPTION", "Error fetching data", e)
                }
            }
        }
    }
}