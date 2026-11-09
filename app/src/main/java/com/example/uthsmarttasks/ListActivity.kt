package com.example.uthsmarttasks

import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ListActivity : AppCompatActivity() {

    private lateinit var rvTodoTasks: RecyclerView
    private lateinit var rvDoneTasks: RecyclerView
    private lateinit var tvPendingCount: TextView
    private lateinit var etSearch: EditText
    private lateinit var tabAll: TextView
    private lateinit var tabStudy: TextView
    private lateinit var tabLife: TextView

    private lateinit var todoAdapter: TaskAdapter
    private lateinit var doneAdapter: TaskAdapter

    private var allTasks: List<Task> = emptyList() // Lưu trữ toàn bộ dữ liệu gốc

    // Các biến trạng thái bộ lọc
    private var currentCategoryFilter: String = "All" // "All", "Study", "Life"
    private var searchQuery: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        initViews()
        setupRecyclerViews()
        setupListeners()
        fetchTasks()
    }

    private fun initViews() {
        rvTodoTasks = findViewById(R.id.rvTodoTasks)
        rvDoneTasks = findViewById(R.id.rvDoneTasks)
        tvPendingCount = findViewById(R.id.tvPendingCount)
        etSearch = findViewById(R.id.etSearch)
        tabAll = findViewById(R.id.tabAll)
        tabStudy = findViewById(R.id.tabStudy)
        tabLife = findViewById(R.id.tabLife)
    }

    private fun setupRecyclerViews() {
        rvTodoTasks.layoutManager = LinearLayoutManager(this)
        rvDoneTasks.layoutManager = LinearLayoutManager(this)

        todoAdapter = TaskAdapter(emptyList())
        doneAdapter = TaskAdapter(emptyList())

        rvTodoTasks.adapter = todoAdapter
        rvDoneTasks.adapter = doneAdapter
    }

    private fun setupListeners() {
        // Lắng nghe sự kiện gõ phím vào thanh tìm kiếm
        etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                searchQuery = s.toString().trim()
                applyFilters() // Gọi hàm lọc lại dữ liệu
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        // Lắng nghe sự kiện click các Tab phân loại
        tabAll.setOnClickListener {
            currentCategoryFilter = "All"
            updateTabUI(tabAll, tabStudy, tabLife)
            applyFilters()
        }

        tabStudy.setOnClickListener {
            currentCategoryFilter = "Study" // Tên category trả về từ API
            updateTabUI(tabStudy, tabAll, tabLife)
            applyFilters()
        }

        tabLife.setOnClickListener {
            currentCategoryFilter = "Life" // Tên category trả về từ API
            updateTabUI(tabLife, tabAll, tabStudy)
            applyFilters()
        }
    }

    // Hàm cập nhật màu sắc Tab đang được chọn
    private fun updateTabUI(selectedTab: TextView, unselected1: TextView, unselected2: TextView) {
        selectedTab.setBackgroundColor(Color.parseColor("#000000"))
        selectedTab.setTextColor(Color.parseColor("#FFFFFF"))

        unselected1.setBackgroundColor(Color.parseColor("#E0E0E0"))
        unselected1.setTextColor(Color.parseColor("#000000"))

        unselected2.setBackgroundColor(Color.parseColor("#E0E0E0"))
        unselected2.setTextColor(Color.parseColor("#000000"))
    }

    private fun fetchTasks() {
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val response = RetrofitClient.instance.getTasks()
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful && response.body() != null) {
                        val taskMap = response.body()!!
                        allTasks = taskMap.values.toList()
                        applyFilters() // Lọc và hiển thị dữ liệu lần đầu tiên
                    } else {
                        Toast.makeText(this@ListActivity, "Lỗi khi tải dữ liệu", Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@ListActivity, "Lỗi kết nối mạng", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    // Hàm thực hiện việc lọc dữ liệu dựa trên Category, Text Search và Trạng thái hoàn thành
    private fun applyFilters() {
        // 1. Lọc theo Category
        var filteredList = if (currentCategoryFilter == "All") {
            allTasks
        } else {
            // Lưu ý: Dữ liệu API trả về category phải khớp chữ hoa/thường ("Study" hoặc "Life")
            allTasks.filter { it.category.equals(currentCategoryFilter, ignoreCase = true) }
        }

        // 2. Lọc theo từ khóa tìm kiếm (Search)
        if (searchQuery.isNotEmpty()) {
            filteredList = filteredList.filter {
                it.title.contains(searchQuery, ignoreCase = true)
            }
        }

        // 3. Phân tách danh sách thành CẦN LÀM và ĐÃ XONG
        val todoList = filteredList.filter { !it.done }
        val doneList = filteredList.filter { it.done }

        // 4. Cập nhật giao diện
        todoAdapter.updateData(todoList)
        doneAdapter.updateData(doneList)
        tvPendingCount.text = "${todoList.size} việc chưa xong"
    }
}