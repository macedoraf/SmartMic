package br.com.rafael.smartmic.data

import org.java_websocket.client.WebSocketClient
import org.java_websocket.handshake.ServerHandshake
import java.net.URI

class GuestClient(uri: URI) : WebSocketClient(uri) {

    override fun onOpen(handshakedata: ServerHandshake?) {

    }

    override fun onClose(code: Int, reason: String?, remote: Boolean) {

    }

    override fun onMessage(message: String?) {

    }

    override fun onError(ex: Exception?) {

    }
}
