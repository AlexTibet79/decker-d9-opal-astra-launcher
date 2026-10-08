package com.decker.astra.launcher.can

import android.hardware.usb.UsbDevice
import android.hardware.usb.UsbDeviceConnection
import android.hardware.usb.UsbManager
import android.util.Log
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class VehicleData(
    val instantConsumption: Double = 0.0,
    val avgConsumption: Double = 0.0,
    val range: Int = 0,
    val coolantTemp: Int = 0,
    val ambientTemp: Int = 0,
    val speed: Int = 0,
    val rpm: Int = 0,
    val fuelLevel: Int = 0,
    val isConnected: Boolean = false
)

class CanBusManager(
    private val usbManager: UsbManager
) {

    private var usbConnection: UsbDeviceConnection? = null
    private var connectedDevice: UsbDevice? = null

    private val _vehicleData = MutableStateFlow(VehicleData())
    val vehicleData: StateFlow<VehicleData> = _vehicleData

    companion object {
        private const val TAG = "CanBusManager"
        private const val ELM327_VENDOR_ID = 0x067B // Prolific
        private const val ELM327_PRODUCT_ID = 0x2303
    }

    fun scanForDevices(): List<UsbDevice> {
        return usbManager.deviceList.values.filter {
            it.vendorId == ELM327_VENDOR_ID || it.vendorId == 0x10C4 // Silicon Labs
        }.also {
            Log.d(TAG, "Found ${it.size} ELM327-compatible devices")
        }
    }

    fun connectToDevice(device: UsbDevice): Boolean {
        return try {
            usbConnection = usbManager.openDevice(device)
            connectedDevice = device
            Log.d(TAG, "Connected to ${device.deviceName}")
            initializeElm327()
            true
        } catch (e: Exception) {
            Log.e(TAG, "Failed to connect: ${e.message}")
            false
        }
    }

    private fun initializeElm327() {
        // Send AT Z (reset)
        sendCommand("ATZ\r")
        // Set protocol to CAN 11-bit 500K
        sendCommand("ATSP6\r")
        // Get device info
        sendCommand("ATI\r")
        updateConnectionStatus(true)
    }

    fun requestPidData(pid: String): String? {
        return try {
            val response = sendCommand("01$pid\r")
            Log.d(TAG, "PID $pid response: $response")
            response
        } catch (e: Exception) {
            Log.e(TAG, "Failed to request PID $pid: ${e.message}")
            null
        }
    }

    private fun sendCommand(command: String): String? {
        return try {
            val conn = usbConnection ?: return null
            // Placeholder for actual USB serial communication
            Log.d(TAG, "Sending: $command")
            ""
        } catch (e: Exception) {
            Log.e(TAG, "Error sending command: ${e.message}")
            null
        }
    }

    fun updateVehicleData(
        instantConsumption: Double = _vehicleData.value.instantConsumption,
        avgConsumption: Double = _vehicleData.value.avgConsumption,
        range: Int = _vehicleData.value.range,
        coolantTemp: Int = _vehicleData.value.coolantTemp,
        ambientTemp: Int = _vehicleData.value.ambientTemp,
        speed: Int = _vehicleData.value.speed,
        rpm: Int = _vehicleData.value.rpm,
        fuelLevel: Int = _vehicleData.value.fuelLevel
    ) {
        _vehicleData.value = VehicleData(
            instantConsumption = instantConsumption,
            avgConsumption = avgConsumption,
            range = range,
            coolantTemp = coolantTemp,
            ambientTemp = ambientTemp,
            speed = speed,
            rpm = rpm,
            fuelLevel = fuelLevel,
            isConnected = _vehicleData.value.isConnected
        )
    }

    private fun updateConnectionStatus(connected: Boolean) {
        _vehicleData.value = _vehicleData.value.copy(isConnected = connected)
    }

    fun disconnect() {
        try {
            usbConnection?.close()
            usbConnection = null
            connectedDevice = null
            updateConnectionStatus(false)
            Log.d(TAG, "Disconnected")
        } catch (e: Exception) {
            Log.e(TAG, "Error disconnecting: ${e.message}")
        }
    }

    fun isConnected(): Boolean = usbConnection != null && _vehicleData.value.isConnected
}
