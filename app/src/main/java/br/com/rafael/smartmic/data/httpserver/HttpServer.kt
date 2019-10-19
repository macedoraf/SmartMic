package br.com.rafael.smartmic.data.httpserver

import android.util.Log
import com.koushikdutta.async.http.server.AsyncHttpServer

class HttpServer : AsyncHttpServer() {

    fun closeServer() {
        this.stop()
    }


    fun startServer(port: Int) {
        listen(port)
        this["/", { request, response ->

            Log.i("Http-Server-Request", request.path)
            Log.i("Http-Server-Request", request.body.contentType)
            Log.i("Http-Server-Request", request.method)

            Log.i("Http-Server-Response", "")


        }]

    }


}