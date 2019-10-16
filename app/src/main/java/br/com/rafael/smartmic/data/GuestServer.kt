package br.com.rafael.smartmic.data

import org.java_websocket.WebSocket
import org.java_websocket.handshake.ClientHandshake
import org.java_websocket.server.WebSocketServer
import java.net.InetSocketAddress

class GuestServer(systemInfo: DataSystemInfo, val listener: GuestServerListener) :
    WebSocketServer(InetSocketAddress(systemInfo.getIp(), systemInfo.getFreeRamdomPort())) {

    interface GuestServerListener {
        fun onServerUp()
    }

    override fun onOpen(conn: WebSocket?, handshake: ClientHandshake?) {

    }

    override fun onClose(conn: WebSocket?, code: Int, reason: String?, remote: Boolean) {

    }

    override fun onMessage(conn: WebSocket?, message: String?) {

    }

    override fun onStart() {
        listener.onServerUp()

    }

    override fun onError(conn: WebSocket?, ex: Exception?) {

    }

}