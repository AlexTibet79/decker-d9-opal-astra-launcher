package com.decker.astra.launcher

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.KeyEvent
import androidx.appcompat.app.AppCompatActivity
import com.decker.astra.launcher.databinding.ActivityRouteComputerBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.math.sin

class RouteComputerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRouteComputerBinding
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
        binding = ActivityRouteComputerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initializeData()
        setupButtonListeners()
        uiHandler.post(updateTimer)
    }

    override fun dispatchKeyEvent(event: KeyEvent): Boolean {
        if (SteeringWheelController.handleKeyEvent(this, event.keyCode, event)) {
            return true
        }
        return super.dispatchKeyEvent(event)
    }

    private fun initializeData() {
        binding.instantConsumptionNum.text = "7"
        binding.avgConsumptionNum.text = "6"
        binding.rangeNum.text = "428"
        binding.coolantNum.text = "87"
        binding.ambientNum.text = "13"
        binding.elmIndicatorText.text = "ELM327 — з'єднано"
    }

    private fun updateDisplay() {
        val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        val now = Date()

        binding.statusTime.text = timeFormat.format(now)
        binding.largeClockDisplay.text = timeFormat.format(now)

        val instantConsumption = 6.5 + sin((elapsedSeconds / 10.0)) * 2.0
        val avgConsumption = 6.4 + sin((elapsedSeconds / 20.0)) * 0.3
        val range = 420 + (sin(elapsedSeconds / 30.0) * 20).toInt()
        val coolant = 82 + sin((elapsedSeconds / 15.0)) * 8
        val ambient = 12 + sin((elapsedSeconds / 25.0)) * 3

        binding.instantConsumptionNum.text = instantConsumption.toInt().toString()
        binding.avgConsumptionNum.text = avgConsumption.toInt().toString()
        binding.rangeNum.text = range.toString()
        binding.coolantNum.text = coolant.toInt().toString()
        binding.ambientNum.text = ambient.toInt().toString()
    }

    private fun setupButtonListeners() {
        binding.btnRouteComputer.setOnClickListener { }
        binding.btnMedia.setOnClickListener {
            startActivity(Intent(this, MediaActivity::class.java))
        }
        binding.btnRadio.setOnClickListener {
            startActivity(Intent(this, RadioActivity::class.java))
        }
        binding.btnNav.setOnClickListener {
            startActivity(Intent(this, NavigationActivity::class.java))
        }
        binding.btnCar.setOnClickListener { }
        binding.btnSettings.setOnClickListener { }
    }

    override fun onDestroy() {
        uiHandler.removeCallbacks(updateTimer)
        super.onDestroy()
    }
}
