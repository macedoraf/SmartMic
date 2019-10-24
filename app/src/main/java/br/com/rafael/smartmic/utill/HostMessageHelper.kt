package br.com.rafael.smartmic.utill

/*
    Project SmartMic
    Created by Rafael in 21/10/2019
*/

object HostMessageHelper {

    fun parseToJsonString(oldMessage: String): String {
        val beginToRemove = oldMessage.substring(0, 12)
        val newString = oldMessage.replace(beginToRemove, "").replace("\\", "")
        return newString.substring(0, newString.length - 2)

    }
}