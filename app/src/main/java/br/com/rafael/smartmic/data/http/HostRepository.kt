package br.com.rafael.smartmic.data.http


import com.google.gson.Gson
import io.reactivex.Observable
import okhttp3.*

/*
    Project SmartMic
    Created by Rafael in 19/10/2019
*/

class HostRepository(
    private val parser: Gson,
    private val okHttpClient: OkHttpClient,
    private val requestBuilder: Request.Builder
) {

    fun sendRequestConnectionToHost(
        ipAddress: String,
        port: Int,
        deviceId: String) =
        makeRequest(RequestType.Connect(ipAddress, port.toString(), deviceId))


    fun sendRequestDisconnect(
        ipAddress: String,
        port: Int,
        deviceId: String
    ): Observable<ResponseType> =
        makeRequest(RequestType.Disconnect(ipAddress, port.toString(), deviceId))

    fun sendPing(): Observable<ResponseType> =
        makeRequest(RequestType.Ping())

    fun sendMessage(
        message: String,
        ipAddress: String,
        port: Int,
        deviceId: String
    ): Observable<ResponseType> =
        makeRequest(
            RequestType.Message(
                message,
                ipAddress,
                port.toString(),
                deviceId))

    fun sendRequestUnmuteMic(
        ip: String,
        randomPort: Int,
        deviceId: String
    ): Observable<ResponseType> =
        makeRequest(RequestType.UnmuteMic(ip, randomPort.toString(), deviceId))

    fun sendRequestMuteMic(
        ip: String,
        randomPort: Int,
        deviceId: String
    ): Observable<ResponseType> =
        makeRequest(RequestType.MuteMic(ip, randomPort.toString(), deviceId))

    fun sendRequestCloseMic(
        ip: String,
        randomPort: Int,
        deviceId: String
    ): Observable<ResponseType> =
        makeRequest(RequestType.MuteMic(ip, randomPort.toString(), deviceId))


    private fun makeRequest(requestType: RequestType) =
        Observable.create<Response> {
            okHttpClient.newCall(createRequest(requestType)).execute()
        }.map {
            if (it.isSuccessful) {
                ResponseType.Ok
            } else {
                ResponseType.Error
            }
        }

    private fun createRequest(requestType: RequestType) =
        requestBuilder.put(createRequestBody(requestType)).build()

    private fun createRequestString(requestType: RequestType): String {
        return "{\"message\" = ${parser.toJson(requestType)}\"}"
    }

    private fun createRequestBody(requestType: RequestType) =
        RequestBody.create(MediaType.parse("text/plain"), createRequestString(requestType))


}