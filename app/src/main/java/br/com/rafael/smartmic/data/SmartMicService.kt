package br.com.rafael.smartmic.data

import com.tinder.scarlet.ws.Receive
import kotlinx.coroutines.Deferred

/*
    Project SmartMic
    Created by Rafael in 15/10/2019
*/

interface SmartMicService {

    @Receive
    fun socketEventAsync(): Deferred<String>

}