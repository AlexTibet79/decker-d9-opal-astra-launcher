package com.decker.astra.launcher

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.KeyEvent
import androidx.appcompat.app.AppCompatActivity
import com.decker.astra.launcher.databinding.ActivityNavigationBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class NavigationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNavigationBinding
    private val uiHandler = Handler(Looper.getMainLooper())

    private val updateTimer = object : Runnable {
        override fun run() {
            updateDisplay()
            uiHandler.postDelayed(this, 1000)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNavigationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupButtonListeners()
        uiHandler.post(updateTimer)
    }

    override fun dispatchKeyEvent(event: KeyEvent): Boolean {
        if (SteeringWheelController.handleKeyEvent(this, event.keyCode, event)) {
            return true
        }
        return super.dispatchKeyEvent(event)
    }

    private fun setupButtonListeners() {
        binding.btnRouteComputer.setOnClickListener { finish() }
        binding.btnMedia.setOnClickListener { navigateTo(MediaActivity::class.java) }
        binding.btnRadio.setOnClickListener { navigateTo(RadioActivity::class.java) }
        binding.btnCar.setOnClickListener { }
        binding.btnSettings.setOnClickListener { }
    }

    private fun updateDisplay() {
        val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        binding.statusTime.text = timeFormat.format(Date())
    }

    private fun navigateTo(activityClass: Class<*>) {
        startActivity(Intent(this, activityClass))
        finish()
    }

    override fun onDestroy() {
        uiHandler.removeCallbacks(updateTimer)
        super.onDestroy()
    }
}
