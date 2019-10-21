package br.com.rafael.smartmic.data.httpserver

/*
    Project SmartMic
    Created by Rafael in 19/10/2019
*/

sealed class ServerStatus(val message: String? = null) {

    class Ok(message: String) : ServerStatus(message)

    object Started : ServerStatus()

    class Error(message: String) : ServerStatus(message)

    class Close : ServerStatus()
}