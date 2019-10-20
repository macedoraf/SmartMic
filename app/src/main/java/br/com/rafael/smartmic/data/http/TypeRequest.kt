package br.com.rafael.smartmic.data.http

import com.google.gson.annotations.SerializedName

sealed class TypeRequest(
    private val type: String,
    @SerializedName("param_1")
    private val param1: String? = null,
    @SerializedName("param_2")
    private val param2: String? = null,
    @SerializedName("param_3")
    private val param3: String? = null,
    @SerializedName("param_4")
    private val param4: String? = null,
    @SerializedName("param_5")
    private val param5: String? = null,
    @SerializedName("param_6")
    private val param6: String? = null
) {

    class Connect(ipAddress: String, ipPort: String, deviceId: String) :
        TypeRequest("message", "NAME", ipAddress, ipPort, deviceId)

    class Disconnect(ipAddress: String, ipPort: String, deviceId: String) :
        TypeRequest("command", "GUEST_DISCONECTED", ipAddress, ipPort, deviceId)

    class Message(
        message: String, ipAddress: String, ipPort: String,
        deviceId: String
    ) :
        TypeRequest("command", "MESSAGE_FROM_GUEST", ipAddress, ipPort, deviceId, message)

    class MuteMic(ipAddress: String, ipPort: String, deviceId: String) :
        TypeRequest("command", "MIC_MUTED_BY_GUEST", ipAddress, ipPort, deviceId)

    class UnmuteMic(ipAddress: String, ipPort: String, deviceId: String) :
        TypeRequest("command", "MIC_UNMUTED_BY_GUEST", ipAddress, ipPort, deviceId)

    class DeclinePool(ipAddress: String, ipPort: String, deviceId: String) :
        TypeRequest("response", "DECLINED_POLL", ipAddress, ipPort, deviceId)

    class StartPoolAck(ipAddress: String, ipPort: String, deviceId: String) :
        TypeRequest("response", "POLL_START_ACK", ipAddress, ipPort, deviceId)

    class PollAwnser(
        um: String, dois: String, ipAddress: String, ipPort: String,
        val deviceId: String
    ) :
        TypeRequest("response", "POLL_ANSWER", ipAddress, ipPort, deviceId, um, dois)

    class Ping : TypeRequest("message", "PING")

    
}