package br.com.rafael.smartmic.data

import android.util.Log
import br.com.rafael.smartmic.data.subscribe.Subscribe
import com.google.gson.Gson
import okhttp3.*

/*
    Project SmartMic
    Created by Rafael in 15/10/2019
*/

class WebSocketRepository(
    private val gson: Gson,
    private val okHttpClient: OkHttpClient,
    private val systemInfo: DataSystemInfo
) : WebSocketListener(), GuestServer.GuestServerListener {

    companion object {
        const val TAG = "WebSocketRepository"
    }

    lateinit var clientWebSocket: WebSocket

    lateinit var serverWebSocket: GuestServer


    fun startWebsocket(ipWithPort: String) {
        serverWebSocket = GuestServer(systemInfo.getIp(), systemInfo.getFreeRamdomPort(), this)
        serverWebSocket.start()

        val request = Request.Builder().url(ipWithPort).build()
        clientWebSocket = okHttpClient.newWebSocket(request, this)

    }

    //Client Listeners
    override fun onOpen(webSocket: WebSocket, response: Response) {
        //Start Server

    }

    override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
        serverWebSocket.stop()
    }

    //Client Listeners


    // Server Listeners
    override fun onSuccessServerStart(ip: String, port: Int) {
        clientWebSocket.send(
            gson.toJson(
                Subscribe.RequestConnection(
                    ip,
                    port.toString(),
                    systemInfo.getDeviceId()
                )
            )
        )
    }

    override fun onMessageServerRecive(message: String?) {
        Log.d(TAG, "OnMessage Recivce -> $message")
    }

    override fun onServerDisconect() {
        Log.d(TAG, "Websocked server Disconnect")
        clientWebSocket.close(1000, "")
    }


// Server Listeners


}