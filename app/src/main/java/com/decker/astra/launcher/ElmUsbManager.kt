package com.decker.astra.launcher

import android.hardware.usb.UsbDevice
import android.hardware.usb.UsbDeviceConnection
import android.hardware.usb.UsbManager

class ElmUsbManager(
    private val usbManager: UsbManager
) {

    fun hasConnectedDevice(): Boolean = usbManager.deviceList.isNotEmpty()

    fun connect(): Boolean = true

    fun disconnect(): Boolean = true

    fun getAvailableDevice(): UsbDevice? = usbManager.deviceList.values.firstOrNull()

    fun openConnection(device: UsbDevice): UsbDeviceConnection? = null
}
