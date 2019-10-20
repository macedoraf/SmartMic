package br.com.rafael.smartmic.data.httpserver

/*
    Project SmartMic
    Created by Rafael in 19/10/2019
*/

sealed class ServerStatus {

    object CloseMic : ServerStatus()

    object OpenMic : ServerStatus()

    object Start : ServerStatus()
}