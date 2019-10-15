package br.com.rafael.smartmic.data

import br.com.rafael.smartmic.data.subscribe.Subscribe
import com.tinder.scarlet.WebSocket

/*
    Project SmartMic
    Created by Rafael in 15/10/2019
*/

class WebSocketRepository(private val service: SmartMicService) {
    init {
        service.observeWebSocketEvent()
            .filter { it is WebSocket.Event.OnConnectionOpened<*> }
            .subscribe {
                Subscribe.RequestConnection("","","")

            }
    }


}