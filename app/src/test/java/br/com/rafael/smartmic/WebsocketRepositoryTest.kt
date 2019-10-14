package br.com.rafael.smartmic

import br.com.rafael.smartmic.data.WebsocketRepository
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import org.junit.Test

/*
    Project SmartMic
    Created by Rafael in 13/10/2019
*/

class WebsocketRepositoryTest {

    @Test
    fun `should connect and close to server`() {
        val listener = object : WebSocketListener() {
            override fun onOpen(webSocket: WebSocket, response: Response) {
                super.onOpen(webSocket, response)
                webSocket.close(1000,"Fechando")
            }

            override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
                super.onClosing(webSocket, code, reason)
            }

            override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
                super.onClosed(webSocket, code, reason)
            }

        }
        WebsocketRepository.initConnection("", listener)

    }
}