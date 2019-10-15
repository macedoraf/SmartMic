package br.com.rafael.smartmic.data

/*
    Project SmartMic
    Created by Rafael in 15/10/2019
*/

class WebSocketRepository(private val service: SmartMicService) {


    suspend fun connect(): String {
        return service.socketEventAsync()
            .await()
    }

}