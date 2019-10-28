package br.com.rafael.smartmic.data.httpserver

/*
    Project SmartMic
    Created by Rafael in 19/10/2019
*/

sealed class ServerStatus(val message: String? = null) {

    open class Ok(message: String) : ServerStatus(message)

    class QueuePosition(position: String) : Ok(position)

    object Started : ServerStatus()

    object OpenMic : ServerStatus()

    object CloseMic : ServerStatus()

    object MuteMic : ServerStatus()

    object UnMuteMic : ServerStatus()

    class Error(message: String) : ServerStatus(message)

    class Close : ServerStatus()
}