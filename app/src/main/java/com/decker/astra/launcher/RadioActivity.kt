package com.decker.astra.launcher

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.KeyEvent
import androidx.appcompat.app.AppCompatActivity
import com.decker.astra.launcher.databinding.ActivityRadioBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.math.sin

class RadioActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRadioBinding
    private val uiHandler = Handler(Looper.getMainLooper())
    private var elapsedSeconds = 0
    private var currentFreq = 105.5

    private val updateTimer = object : Runnable {
        override fun run() {
            updateDisplay()
            elapsedSeconds++
            uiHandler.postDelayed(this, 1000)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRadioBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRadioDisplay()
        setupButtonListeners()
        uiHandler.post(updateTimer)
    }

    override fun dispatchKeyEvent(event: KeyEvent): Boolean {
        if (SteeringWheelController.handleKeyEvent(this, event.keyCode, event)) {
            return true
        }
        return super.dispatchKeyEvent(event)
    }

    private fun setupRadioDisplay() {
        binding.radioFreq.text = "105"
        binding.stationName.text = "FM Радіо"
        binding.signalBar.progress = 75
    }

    private fun setupButtonListeners() {
        binding.btnRouteComputer.setOnClickListener { finish() }
        binding.btnMedia.setOnClickListener { navigateTo(MediaActivity::class.java) }
        binding.btnNav.setOnClickListener { navigateTo(NavigationActivity::class.java) }
        binding.btnCar.setOnClickListener { }
        binding.btnSettings.setOnClickListener { }
    }

    private fun updateDisplay() {
        val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        binding.statusTime.text = timeFormat.format(Date())
        currentFreq = 105.5 + sin((elapsedSeconds / 20.0)) * 5.0
        binding.radioFreq.text = currentFreq.toInt().toString()
        binding.signalBar.progress = (75 + sin(elapsedSeconds / 15.0) * 15).toInt()
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
