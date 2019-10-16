package br.com.rafael.smartmic.data

import okhttp3.WebSocketListener
import java.net.URI

/*
    Project SmartMic
    Created by Rafael in 15/10/2019
*/

class WebSocketRepository(
    systemInfo: DataSystemInfo
) : WebSocketListener(), GuestServer.GuestServerListener {

    private var ipHost = ""
    private var portHost = 0

    private val guestServer by lazy { GuestServer(systemInfo, this) }

    private val guestClient by lazy { GuestClient(URI.create("ws://$ipHost:$portHost")) }

//        private val guestClient by lazy { GuestClient(URI.create("ws://${guestServer.address}:${guestServer.port} ")) }


    fun initServer(ipHost: String, portHost: Int) {
        this.ipHost = ipHost
        this.portHost = portHost

        guestServer.start()
    }

    override fun onServerUp() {
        guestClient.connect()
    }


}