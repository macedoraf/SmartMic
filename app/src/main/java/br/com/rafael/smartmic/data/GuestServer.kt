package br.com.rafael.smartmic.data

import android.util.Log
import org.java_websocket.WebSocket
import org.java_websocket.handshake.ClientHandshake
import org.java_websocket.server.WebSocketServer
import java.net.InetSocketAddress

class GuestServer(private val hostname: String, private val Adressport: Int, private val listener: GuestServerListener) :
    WebSocketServer(InetSocketAddress(hostname, Adressport)) {

    interface GuestServerListener {

        fun onSuccessServerStart(ip: String, port: Int)

        fun onMessageServerRecive(message: String?)

        fun onServerDisconect()

    }

    companion object {
        const val TAG = "GUEST_SERVER"
    }

    override fun onOpen(conn: WebSocket?, handshake: ClientHandshake?) {
        Log.d(TAG, "Server Open")

    }

    override fun onClose(conn: WebSocket?, code: Int, reason: String?, remote: Boolean) {
        Log.d(TAG, "Server Closed")
        listener.onServerDisconect()
    }

    override fun onMessage(conn: WebSocket?, message: String?) {
        Log.d(TAG, message)

        listener.onMessageServerRecive(message)
    }

    override fun onStart() {
        Log.d(TAG, "Server Stated")
        listener.onSuccessServerStart(hostname, Adressport)
    }

    override fun onError(conn: WebSocket?, ex: Exception?) {
        Log.e(TAG, ex?.message)
    }
}