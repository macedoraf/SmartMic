package br.com.rafael.smartmic.utill

/*
    Project SmartMic
    Created by Rafael in 21/10/2019
*/

object HostMessageHelper {

    fun parseToJsonString(oldMessage: String): String {
        val beginToRemove = oldMessage.substring(0, 12)
        val endToRemove = oldMessage.substring(oldMessage.length - 3, oldMessage.length)
        return oldMessage.replace(beginToRemove, "")
            .replace(endToRemove, "")

    }
}