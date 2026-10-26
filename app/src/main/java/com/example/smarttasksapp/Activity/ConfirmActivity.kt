package com.example.smarttasksapp.Activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.example.smarttasksapp.R

class ConfirmActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirm)

        val tvEmail = findViewById<TextView>(R.id.tvConfirmEmail)
        val tvOtp = findViewById<TextView>(R.id.tvConfirmOtp)
        val tvPassword = findViewById<TextView>(R.id.tvConfirmPassword)
        val btnSubmit = findViewById<Button>(R.id.btnSubmit)

        // Lấy dữ liệu cuối cùng
        val email = intent.getStringExtra("KEY_EMAIL")
        val otp = intent.getStringExtra("KEY_OTP")
        val pass = intent.getStringExtra("KEY_PASS")

        // Gán lên TextView
        tvEmail.text = email
        tvOtp.text = otp
        tvPassword.text = pass // Thực tế nên format thành "***"

        // Xử lý nút Submit quay về màn hình 1 (xóa các Activity ở giữa khỏi stack)
        btnSubmit.setOnClickListener {
            val intent = Intent(this, ForgetPasswordActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            finish()
        }
    }
}