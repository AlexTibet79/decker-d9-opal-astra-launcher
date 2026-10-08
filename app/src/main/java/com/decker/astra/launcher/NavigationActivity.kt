package com.decker.astra.launcher

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.decker.astra.launcher.databinding.ActivityNavigationBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.math.sin

class NavigationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNavigationBinding
    private val uiHandler = Handler(Looper.getMainLooper())
    private var elapsedSeconds = 0

    private val updateTimer = object : Runnable {
        override fun run() {
            updateDisplay()
            elapsedSeconds++
            uiHandler.postDelayed(this, 1000)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNavigationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupNavDisplay()
        setupButtonListeners()
        uiHandler.post(updateTimer)
    }

    private fun setupNavDisplay() {
        // Placeholder for GPS/navigation data
    }

    private fun setupButtonListeners() {
        binding.btnRouteComputer.setOnClickListener {
            navigateTo(RouteComputerActivity::class.java)
        }

        binding.btnMedia.setOnClickListener {
            navigateTo(MediaActivity::class.java)
        }

        binding.btnRadio.setOnClickListener {
            navigateTo(RadioActivity::class.java)
        }

        binding.btnCar.setOnClickListener {
            // TODO: Launch car diagnostics
        }

        binding.btnSettings.setOnClickListener {
            // TODO: Launch settings
        }
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
