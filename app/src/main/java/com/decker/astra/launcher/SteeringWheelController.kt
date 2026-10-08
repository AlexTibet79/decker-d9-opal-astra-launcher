package com.decker.astra.launcher

import android.content.Intent
import android.media.AudioManager
import android.view.KeyEvent
import androidx.appcompat.app.AppCompatActivity

/**
 * SteeringWheelController handles all steering wheel button inputs for Decker D9-005 head unit.
 *
 * Button mapping:
 * - KEYCODE_VOLUME_UP / VOLUME_DOWN: Audio volume control
 * - KEYCODE_MEDIA_NEXT / MEDIA_PREVIOUS: Track navigation (Media mode only)
 * - KEYCODE_MEDIA_PLAY_PAUSE: Play/Pause toggle (Media mode only)
 * - KEYCODE_HEADSETHOOK: Source/Mode switch (cycles: RouteComputer → Radio → Media → Navigation → RouteComputer)
 * - KEYCODE_CALL / ENDCALL: Phone call handling (placeholder for future Bluetooth integration)
 */
object SteeringWheelController {

    fun handleKeyEvent(activity: AppCompatActivity, keyCode: Int, event: KeyEvent): Boolean {
        if (event.action != KeyEvent.ACTION_DOWN) return false

        return when (keyCode) {
            KeyEvent.KEYCODE_VOLUME_UP -> handleVolumeUp(activity)
            KeyEvent.KEYCODE_VOLUME_DOWN -> handleVolumeDown(activity)
            KeyEvent.KEYCODE_MEDIA_NEXT -> handleMediaNext(activity)
            KeyEvent.KEYCODE_MEDIA_PREVIOUS -> handleMediaPrevious(activity)
            KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE -> handlePlayPause(activity)
            KeyEvent.KEYCODE_HEADSETHOOK -> handleSourceSwitch(activity)
            KeyEvent.KEYCODE_CALL -> handleCallAnswer(activity)
            KeyEvent.KEYCODE_ENDCALL -> handleCallEnd(activity)
            else -> false
        }
    }

    /**
     * Handle volume up: increase media volume regardless of current activity
     */
    private fun handleVolumeUp(activity: AppCompatActivity): Boolean {
        val audioManager = activity.getSystemService(AudioManager::class.java)
        audioManager?.adjustStreamVolume(
            AudioManager.STREAM_MUSIC,
            AudioManager.ADJUST_RAISE,
            AudioManager.FLAG_SHOW_UI
        )
        return true
    }

    /**
     * Handle volume down: decrease media volume regardless of current activity
     */
    private fun handleVolumeDown(activity: AppCompatActivity): Boolean {
        val audioManager = activity.getSystemService(AudioManager::class.java)
        audioManager?.adjustStreamVolume(
            AudioManager.STREAM_MUSIC,
            AudioManager.ADJUST_LOWER,
            AudioManager.FLAG_SHOW_UI
        )
        return true
    }

    /**
     * Handle next track: only active in Media activity
     */
    private fun handleMediaNext(activity: AppCompatActivity): Boolean {
        return if (activity is MediaActivity) {
            activity.onSteeringWheelNext()
            true
        } else {
            false
        }
    }

    /**
     * Handle previous track: only active in Media activity
     */
    private fun handleMediaPrevious(activity: AppCompatActivity): Boolean {
        return if (activity is MediaActivity) {
            activity.onSteeringWheelPrevious()
            true
        } else {
            false
        }
    }

    /**
     * Handle play/pause: only active in Media activity
     */
    private fun handlePlayPause(activity: AppCompatActivity): Boolean {
        return if (activity is MediaActivity) {
            activity.onSteeringWheelPlayPause()
            true
        } else {
            false
        }
    }

    /**
     * Handle source/mode switch: cycles through activities in order
     * RouteComputer → Radio → Media → Navigation → RouteComputer
     */
    private fun handleSourceSwitch(activity: AppCompatActivity): Boolean {
        val nextActivity = when (activity) {
            is RouteComputerActivity -> RadioActivity::class.java
            is RadioActivity -> MediaActivity::class.java
            is MediaActivity -> NavigationActivity::class.java
            is NavigationActivity -> RouteComputerActivity::class.java
            else -> null
        }

        return if (nextActivity != null) {
            activity.startActivity(Intent(activity, nextActivity))
            activity.finish()
            true
        } else {
            false
        }
    }

    /**
     * Handle call answer: placeholder for Bluetooth integration
     */
    private fun handleCallAnswer(activity: AppCompatActivity): Boolean {
        // TODO: Wire Bluetooth HFP (Hands-Free Profile) call answering
        return true
    }

    /**
     * Handle call end: placeholder for Bluetooth integration
     */
    private fun handleCallEnd(activity: AppCompatActivity): Boolean {
        // TODO: Wire Bluetooth HFP call termination
        return true
    }
}
