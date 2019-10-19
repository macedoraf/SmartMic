package br.com.rafael.smartmic.data

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.net.wifi.WifiManager
import android.provider.Settings
import android.text.format.Formatter
import java.lang.Exception
import java.net.ServerSocket
import java.util.*
import java.util.concurrent.ThreadLocalRandom

/*
    Project SmartMic
    Created by Rafael in 11/10/2019
*/

class DataSystemInfo(private val applicationContext: Context) {

    fun isWifiConnected(): Boolean {
        val cm =
            applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
        return activeNetwork?.isConnected == true && activeNetwork.type == ConnectivityManager.TYPE_WIFI
    }

    fun getIp(): String {
        val wifiManager =
            applicationContext.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
        return Formatter.formatIpAddress(wifiManager.connectionInfo.ipAddress)
    }

    private fun getPort(localPort: Int): Int? {
        return try {
            val serverSocket = ServerSocket(localPort)
            serverSocket.localPort
        } catch (err: Exception) {
            null
        }

    }

    @SuppressLint("HardwareIds")
    fun getDeviceId(): String {
        return Settings.Secure.getString(
            applicationContext.contentResolver,
            Settings.Secure.ANDROID_ID
        )
    }

    fun getListOfNumbers(from: Int, to: Int): MutableList<Int> {
        val list = mutableListOf<Int>()
        var current = from
        while (current <= to) {
            list.add(current)
            current++
        }

        return list
    }


    fun getFreeRandomPort(from: Int, to: Int): Int {
        val rand = Random()
        val listOfNumbers = getListOfNumbers(from, to)
        while (listOfNumbers.isNotEmpty()) {
            val randomElement = listOfNumbers[rand.nextInt(listOfNumbers.size)]
            val port = getPort(randomElement)
            if (port == null) {
                listOfNumbers.remove(randomElement)
            } else {
                return port
            }
        }
        return 0
    }
}