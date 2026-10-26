package com.example.smarttasksapp.Activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import com.example.smarttasksapp.R

class ResetPasswordActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_password)

        val receivedEmail = intent.getStringExtra("KEY_EMAIL")
        val receivedOtp = intent.getStringExtra("KEY_OTP")

        val btnBack = findViewById<ImageButton>(R.id.btnBack3)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val etConfirmPassword = findViewById<EditText>(R.id.etConfirmPassword)
        val btnNext = findViewById<Button>(R.id.btnNext3)

        btnBack.setOnClickListener { finish() }

        btnNext.setOnClickListener {
            val pass = etPassword.text.toString()
            val confirmPass = etConfirmPassword.text.toString()

            if (pass.isNotEmpty() && pass == confirmPass) {
                val intent = Intent(this, ConfirmActivity::class.java)
                intent.putExtra("KEY_EMAIL", receivedEmail)
                intent.putExtra("KEY_OTP", receivedOtp)
                intent.putExtra("KEY_PASS", pass) // Đính kèm Pass
                startActivity(intent)
            } else {
                // Xử lý báo lỗi mật khẩu không khớp
            }
        }
    }
}