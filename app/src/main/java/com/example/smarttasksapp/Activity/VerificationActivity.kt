package com.example.smarttasksapp.Activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import com.example.smarttasksapp.R

class VerificationActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verification)

        // Nhận dữ liệu từ màn hình trước
        val receivedEmail = intent.getStringExtra("KEY_EMAIL")

        val btnBack = findViewById<ImageButton>(R.id.btnBack2)
        val etOtp = findViewById<EditText>(R.id.etOtp)
        val btnNext = findViewById<Button>(R.id.btnNext2)

        btnBack.setOnClickListener { finish() } // Quay lại

        btnNext.setOnClickListener {
            val otp = etOtp.text.toString()
            if (otp.isNotEmpty()) {
                val intent = Intent(this, ResetPasswordActivity::class.java)
                intent.putExtra("KEY_EMAIL", receivedEmail) // Truyền tiếp Email
                intent.putExtra("KEY_OTP", otp)             // Đính kèm thêm OTP
                startActivity(intent)
            }
        }
    }
}