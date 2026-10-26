package com.example.bttuan5b.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.bttuan5b.R
import com.example.bttuan5b.adapters.OnboardingAdapter
import com.example.bttuan5b.models.OnboardingItem

class OnboardingActivity : AppCompatActivity() {

    private lateinit var onboardingAdapter: OnboardingAdapter
    private lateinit var buttonAction: Button
    private lateinit var textSkip: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding)

        val viewPager = findViewById<ViewPager2>(R.id.viewPagerOnboarding)
        buttonAction = findViewById(R.id.buttonAction)
        textSkip = findViewById(R.id.textSkip)

        setupOnboardingItems(viewPager)

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                if (position == onboardingAdapter.itemCount - 1) {
                    buttonAction.setText(R.string.get_started)
                } else {
                    buttonAction.setText(R.string.continue_action)
                }
            }
        })

        buttonAction.setOnClickListener {
            if (viewPager.currentItem + 1 < onboardingAdapter.itemCount) {
                viewPager.currentItem += 1
            } else {
                navigateToHome()
            }
        }

        textSkip.setOnClickListener {
            navigateToHome()
        }
    }

    private fun setupOnboardingItems(viewPager: ViewPager2) {
        val onboardingItems = listOf(
            OnboardingItem(
                R.drawable.uth,
                "Mọi việc, gọn trong một chỗ",
                "Ghi lại bài tập, việc nhà, lịch hẹn. Không còn quên việc nào."
            ),
            OnboardingItem(
                R.drawable.uth,
                "Chia theo Học tập và Cuộc sống",
                "Mỗi việc có nhóm riêng, nhìn là biết hôm nay cần lo gì."
            ),
            OnboardingItem(
                R.drawable.uth,
                "Nhắc đúng lúc",
                "Bật thông báo để UTH Todo nhắc bạn trước hạn chót."
            )
        )

        onboardingAdapter = OnboardingAdapter(onboardingItems)
        viewPager.adapter = onboardingAdapter
    }

    private fun navigateToHome() {
        startActivity(Intent(applicationContext, com.example.bttuan5b.MainActivity::class.java))
        finish()
    }
}