package br.com.rafael.smartmic.data.http


import android.util.Log
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

    private fun createRequestString(typeRequest: TypeRequest): String {
        return "{\"message\" = ${parser.toJson(typeRequest)}\"}"
    }

    private fun createRequestBody(requestType: TypeRequest) =
        RequestBody.create(MediaType.parse("text/plain"), createRequestString(requestType))

    fun sendRequestConnectionToHost(ipAddress: String, port: Int, deviceId: String): Observable<*> {
        val requestType = TypeRequest.Connect(ipAddress, port.toString(), deviceId)
        val httpRequest = requestBuilder.put(createRequestBody(requestType)).build()
        okHttpClient.newCall(httpRequest).enqueue(this)

        return Observable.just("")
    }

    fun sendRequestPinger(): Observable<*> {
        val requestType = TypeRequest.Ping()
        val httpRequest = requestBuilder.put(createRequestBody(requestType)).build()
        okHttpClient.newCall(httpRequest).enqueue(this)
        return Observable.just("")
    }


    override fun onFailure(call: Call, e: IOException) {
        //TODO : Send Failure OK to Domain
    }

    override fun onResponse(call: Call, response: Response) {
        //TODO : Send Status Ok to Domain
    }
}