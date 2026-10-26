package com.example.smarttasksapp.Activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.smarttasksapp.R

class ForgetPasswordActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forget_password)

        val etEmail = findViewById<EditText>(R.id.etEmail)
        val btnNext = findViewById<Button>(R.id.btnNext1)

        btnNext.setOnClickListener {
            val email = etEmail.text.toString()
            if (email.isNotEmpty()) {
                val intent = Intent(this, VerificationActivity::class.java)
                intent.putExtra("KEY_EMAIL", email) // Đính kèm Email
                startActivity(intent)
            } else {
                Toast.makeText(this, "Vui lòng nhập Email", Toast.LENGTH_SHORT).show()
            }
        }
    }
}