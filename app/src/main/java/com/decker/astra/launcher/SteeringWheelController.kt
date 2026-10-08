package com.decker.astra.launcher

import android.content.Intent
import android.media.AudioManager
import android.view.KeyEvent
import androidx.appcompat.app.AppCompatActivity

object SteeringWheelController {

    fun handleKeyEvent(activity: AppCompatActivity, keyCode: Int, event: KeyEvent): Boolean {
        if (event.action != KeyEvent.ACTION_DOWN) return false

        val audioManager = activity.getSystemService(AudioManager::class.java)

        when (keyCode) {
            KeyEvent.KEYCODE_VOLUME_UP -> {
                audioManager?.adjustStreamVolume(
                    AudioManager.STREAM_MUSIC,
                    AudioManager.ADJUST_RAISE,
                    AudioManager.FLAG_SHOW_UI
                )
                return true
            }

            KeyEvent.KEYCODE_VOLUME_DOWN -> {
                audioManager?.adjustStreamVolume(
                    AudioManager.STREAM_MUSIC,
                    AudioManager.ADJUST_LOWER,
                    AudioManager.FLAG_SHOW_UI
                )
                return true
            }

            KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE -> {
                if (activity !is MediaActivity) {
                    activity.startActivity(Intent(activity, MediaActivity::class.java))
                }
                return true
            }

            KeyEvent.KEYCODE_MEDIA_NEXT -> {
                if (activity !is MediaActivity) {
                    activity.startActivity(Intent(activity, MediaActivity::class.java))
                }
                return true
            }

            KeyEvent.KEYCODE_MEDIA_PREVIOUS -> {
                if (activity !is MediaActivity) {
                    activity.startActivity(Intent(activity, MediaActivity::class.java))
                }
                return true
            }

            KeyEvent.KEYCODE_CALL -> {
                // Placeholder: phone / hands-free key. No-op in launcher mode.
                return true
            }

            KeyEvent.KEYCODE_ENDCALL -> {
                return true
            }

            KeyEvent.KEYCODE_HEADSETHOOK -> {
                if (activity !is MediaActivity) {
                    activity.startActivity(Intent(activity, MediaActivity::class.java))
                }
                return true
            }

            KeyEvent.KEYCODE_DPAD_UP -> {
                if (activity !is RadioActivity) {
                    activity.startActivity(Intent(activity, RadioActivity::class.java))
                }
                return true
            }

            KeyEvent.KEYCODE_DPAD_DOWN -> {
                if (activity !is MediaActivity) {
                    activity.startActivity(Intent(activity, MediaActivity::class.java))
                }
                return true
            }
        }

        return false
    }
}
