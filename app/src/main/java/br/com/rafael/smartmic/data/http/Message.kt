package br.com.rafael.smartmic.data.http

import com.google.gson.annotations.SerializedName

sealed class Message(
    private val type: Type,
    @SerializedName("param_1")
    private val param1: String,
    @SerializedName("param_2")
    private val param2: String,
    @SerializedName("param_3")
    private val param3: String,
    @SerializedName("param_4")
    private val param4: String,
    @SerializedName("param_5")
    private val param5: String? = null,
    @SerializedName("param_6")
    private val param6: String? = null
) {

    data class Connect(val ipAdress: String, val ipPort: String, val deviceId: String) :
        Message(
            Type.RESPONSE,
            "NAME",
            ipAdress, ipPort, deviceId
        )


    private enum class Type(val value: String) {
        RESPONSE("response"), COMMAND("command")

    }
}