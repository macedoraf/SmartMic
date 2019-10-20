package br.com.rafael.smartmic.data.httpserver

import android.util.Log
import fi.iki.elonen.NanoHTTPD
import rx.subjects.PublishSubject
import java.io.InputStream
import java.util.*

class HttpServer(port: Int) : NanoHTTPD(port) {

    companion object {
        const val TAG = "HTTP-SERVER"
    }

    private val observableResponse: PublishSubject<ServerStatus> by lazy { PublishSubject.create<ServerStatus>() }

    fun subscribe(): PublishSubject<ServerStatus> {
        return observableResponse
    }

    fun iziStart() {
        this.start(SOCKET_READ_TIMEOUT, false)
    }

    override fun start(timeout: Int, daemon: Boolean) {
        super.start(timeout, daemon)
        observableResponse.onNext(ServerStatus.Start)
        Log.i(TAG, "Stating on ip:$hostname:$listeningPort")
    }

    override fun serve(session: IHTTPSession?): Response {
        val method = session?.method
        val uri = session?.uri
        val parameters = session?.parameters
        val headers = session?.headers
        val remoteIpAddress = session?.remoteIpAddress
        val inputStream = session?.inputStream
        val bodyString = getBodyContentFromStream(inputStream)

        observableResponse.onNext(ServerStatus.OpenMic)

        //Logging
        Log.i(TAG, "{ORIGIN $remoteIpAddress} Method: $method - URI:$uri ")
        parameters?.forEach {
            Log.i(
                TAG,
                "{ORIGIN $remoteIpAddress} PARAM-KEY: ${it.key} - PARAM-VALUE:${it.value} "
            )
        }
        headers?.forEach {
            Log.i(
                TAG,
                "{ORIGIN $remoteIpAddress} HEADER-KEY: ${it.key} - HEADER-VALUE:${it.value} "
            )
        }
        Log.i(TAG, "{ORIGIN $remoteIpAddress} BODY-CONTENT: $bodyString")

        return newChunkedResponse(Response.Status.OK, MIME_TYPES["application/x-www-form-urlencoded"], null)
    }

    private fun getBodyContentFromStream(inputStream: InputStream?): StringBuilder {
        val sbBuilder = StringBuilder()
        val scan = Scanner(inputStream)
        while (scan.hasNextLine()) {
            sbBuilder.append(scan.nextLine())
        }
        scan.close()
        return sbBuilder
    }
}