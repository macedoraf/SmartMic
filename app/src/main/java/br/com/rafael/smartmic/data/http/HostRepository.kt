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

    private fun createRequestString(requestType: RequestType): String =
        "{\"message\" = ${parser.toJson(requestType)}\"}"

    private fun createRequestBody(requestType: RequestType) =
        RequestBody.create(MediaType.parse("text/plain"), createRequestString(requestType))

    fun sendRequestConnectionToHost(
        ipAddress: String, port: Int, deviceId: String
    ): Observable<ResponseType> =
        makeRequest(RequestType.Connect(ipAddress, port.toString(), deviceId))

    fun sendRequestDisconnect(ipAddress: String, port: Int, deviceId: String):
            Observable<ResponseType> =
        makeRequest(RequestType.Disconnect(ipAddress, port.toString(), deviceId))

    fun sendPing(): Observable<ResponseType> = makeRequest(RequestType.Ping())

    fun sendMessage(message: String, ipAddress: String, port: Int, deviceId: String) =
        makeRequest(RequestType.Message(message, ipAddress, port.toString(), deviceId))


    private fun makeRequest(requestType: RequestType): Observable<ResponseType> {
        return Observable.fromCallable { executeRequest(requestType) }
            .map { handleResponse(it) }
    }

    private fun executeRequest(requestType: RequestType): Response? {
        return okHttpClient.newCall(
            requestBuilder.put(
                createRequestBody(requestType)
            ).build()
        ).execute()
    }

    private fun handleResponse(it: Response): ResponseType {
        return if (it.isSuccessful) {
            ResponseType.Ok
        } else {
            ResponseType.Error
        }
    }
}
