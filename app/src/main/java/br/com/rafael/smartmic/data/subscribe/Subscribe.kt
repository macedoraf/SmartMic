package br.com.rafael.smartmic.data.subscribe

import com.google.gson.annotations.SerializedName

/**
 * Created by Santander on 15/10/2019
 */
sealed class Subscribe(
    @SerializedName("type") private val type: Type,
    @SerializedName("param_1") private val param1: String,
    @SerializedName("param_2") private val param2: String,
    @SerializedName("param_3") private val param3: String,
    @SerializedName("param_4") private val param4: String? = null
) {


    class RequestConnection(ipAdress: String, guestWebServerPort: String, deviceId: String) :
        Subscribe(Type.NAME, ipAdress, guestWebServerPort, deviceId)


    private enum class Type {
        NAME,
        MENSSAGE,
        COMMAND
    }
}
