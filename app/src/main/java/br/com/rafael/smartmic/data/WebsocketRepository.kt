package br.com.rafael.smartmic.data

import br.com.rafael.smartmic.utill.Either
import br.com.rafael.smartmic.utill.Failure
import io.reactivex.Observable
import okhttp3.*

/*
    Project SmartMic
    Created by Rafael in 13/10/2019
*/

class WebsocketRepository(private val client: OkHttpClient) {
    companion object {
        const val DEFAUT_CLOSE_REASON = 1000
    }

    private var webSocketClient: WebSocket? = null

    private var observable = Observable.just("Empty")

    fun initConnection(uri: String): Either<Failure, Observable<String>> {
        val request = Request.Builder()
            .url("ws://$uri")
            .build()
        webSocketClient = client.newWebSocket(request, listener)
        client.dispatcher().executorService().shutdown()
        return Either.Right(observable)
    }

    fun closeConnection() {
        webSocketClient?.close(DEFAUT_CLOSE_REASON, "")
    }

    private val listener = object : WebSocketListener() {

        override fun onOpen(webSocket: WebSocket, response: Response) {
            observable = Observable.just("Connected Successful")
            closeConnection()
        }

        override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
            observable = Observable.just("Close Connection")
        }

        override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response) {
            observable = Observable.just(t.message)
        }

    }

}