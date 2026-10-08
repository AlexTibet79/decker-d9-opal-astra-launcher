package com.decker.astra.launcher

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.decker.astra.launcher.databinding.ActivityMainBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val uiHandler = Handler(Looper.getMainLooper())

    private val clockUpdater = object : Runnable {
        override fun run() {
            updateClock()
            uiHandler.postDelayed(this, 1000)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bindVehicleState()
        uiHandler.post(clockUpdater)
    }

    private fun bindVehicleState() {
        binding.speedValue.text = "076"
        binding.speedBar.progress = 76
        binding.rpmValue.text = "3200"
        binding.coolantValue.text = "87°C"
        binding.batteryValue.text = "14.1V"
        binding.ambientTemp.text = "13°C"
        binding.elmStatus.text = getString(R.string.msg_elm_connected)
        binding.elmStatus.setTextColor(getColor(R.color.accent_green))
    }

    private fun updateClock() {
        val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        val dateFormat = SimpleDateFormat("dd.MM", Locale.getDefault())
        val now = Date()

        binding.systemTime.text = timeFormat.format(now)
        binding.clockDisplay.text = timeFormat.format(now)

        val dateText = dateFormat.format(now)
        binding.elmStatus.text = "${getString(R.string.msg_elm_connected)} · $dateText"
    }

    override fun onDestroy() {
        uiHandler.removeCallbacks(clockUpdater)
        super.onDestroy()
    }
}
