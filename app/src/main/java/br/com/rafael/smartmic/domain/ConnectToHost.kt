package br.com.rafael.smartmic.domain

import br.com.rafael.smartmic.data.WebSocketRepository

/*
    Project SmartMic
    Created by Rafael in 13/10/2019
*/

class ConnectToHost(private val repository: WebSocketRepository) {

    fun startConnectionToHost(ip: String, port: String) {
        repository.startWebsocket("ws://$ip:$port")
    }


}