package br.com.rafael.smartmic.data

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.net.wifi.WifiManager
import android.text.format.Formatter

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
}