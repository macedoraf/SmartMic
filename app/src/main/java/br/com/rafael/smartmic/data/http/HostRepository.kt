package br.com.rafael.smartmic.data.http


import com.google.gson.Gson
import io.reactivex.Observable
import okhttp3.*
import java.io.IOException

/*
    Project SmartMic
    Created by Rafael in 19/10/2019
*/

class HostRepository(
    private val parser: Gson,
    private val okHttpClient: OkHttpClient,
    private val requestBuilder: Request.Builder
) : Callback {

    private fun createRequestString(requestType: RequestType): String {
        return "{\"message\" = ${parser.toJson(requestType)}\"}"
    }

    private fun createRequestBody(requestType: RequestType) =
        RequestBody.create(MediaType.parse("text/plain"), createRequestString(requestType))

    fun sendRequestConnectionToHost(
        ipAddress: String,
        port: Int,
        deviceId: String
    ): Observable<ResponseType> {
        return Observable.defer {
            val requestType = RequestType.Connect(ipAddress, port.toString(), deviceId)
            val httpRequest = requestBuilder.put(createRequestBody(requestType)).build()
            Observable.just(okHttpClient.newCall(httpRequest).execute())
                .map<ResponseType> {
                    if (it.isSuccessful) {
                        ResponseType.Ok
                    } else {
                        ResponseType.Error
                    }
                }
        }


    }

    fun startSendPing(): Observable<ResponseType> {
        val requestType = RequestType.Ping()
        val httpRequest = requestBuilder.put(createRequestBody(requestType)).build()
        return Observable.defer {
            Observable.just(okHttpClient.newCall(httpRequest).execute())
                .map<ResponseType> {
                    if (it.isSuccessful) {
                        ResponseType.Ok
                    } else {
                        ResponseType.Error
                    }
                }
        }
    }


    override fun onFailure(call: Call, e: IOException) {
    }

    override fun onResponse(call: Call, response: Response) {

    }
}