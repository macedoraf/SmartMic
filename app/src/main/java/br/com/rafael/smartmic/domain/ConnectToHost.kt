package br.com.rafael.smartmic.domain

import br.com.rafael.smartmic.data.DataSystemInfo
import br.com.rafael.smartmic.data.http.HostAPI
import br.com.rafael.smartmic.data.httpserver.HttpServer

class ConnectToHost(
    private val systemInfo: DataSystemInfo,
    private val httpServer: HttpServer,
    val hostAPI: HostAPI
) {

    companion object {
        const val ragePortStart = 1000
        const val ragePortEnd = 65000
    }


    fun initConnection() {
        startLocalHttpServer()
    }

    private fun startLocalHttpServer() {
        val freeRandomPort = systemInfo.getFreeRandomPort(ragePortStart, ragePortEnd)
        val deviceId = systemInfo.getDeviceId()
        httpServer.startServer(freeRandomPort)
    }

}