package br.com.rafael.smartmic.data.httpserver

import android.os.AsyncTask
import rx.subjects.PublishSubject
import java.io.BufferedOutputStream
import java.io.BufferedWriter
import java.io.OutputStreamWriter
import java.net.ServerSocket
import java.util.*

/*
    Project SmartMic
    Created by Rafael in 21/10/2019
*/

class ServerSocketTask :
    AsyncTask<Int, String, Unit>() {

    private var isServerOn = true
    private var serverSocket: ServerSocket? = null

    private val observableResponse: PublishSubject<ServerStatus> by lazy { PublishSubject.create<ServerStatus>() }

    companion object {
        const val OUTPUT_HEADER = "\"HTTP/1.1 200 OK\\r\\n"
        const val DEFLATE = "deflate"
        const val CONTENT_TYPE = "Content-Type"
        const val HOST = "Host"
    }

    fun shutdownServer() {
        isServerOn = false
    }

    fun startServer(port: Int): PublishSubject<ServerStatus> {
        this.execute(port)
        return observableResponse
    }


    override fun doInBackground(vararg params: Int?) {
        try {
            serverSocket = ServerSocket(params[0]!!)
            observableResponse.onNext(ServerStatus.Started)
            while (isServerOn) {

                val socket = serverSocket?.let {
                    it.accept()
                }
                val sb = StringBuilder()

                val bufferedWriter = BufferedWriter(
                    OutputStreamWriter(
                        BufferedOutputStream(socket?.getOutputStream()),
                        "UTF-8"
                    )
                )
                bufferedWriter.write(OUTPUT_HEADER)
                bufferedWriter.flush()

                val scanner = Scanner(socket?.getInputStream())
                while (scanner.hasNextLine()) {
                    sb.append(scanner.nextLine())
                }
                val method = sb.substring(0, sb.indexOf("/", 0, true)).trim()
                val host =
                    sb.substring(sb.indexOf(HOST) + HOST.length, sb.indexOf(CONTENT_TYPE, 0, true))
                        .trim()
                val body = sb.substring(sb.indexOf(DEFLATE, 0, true) + DEFLATE.length, sb.length)
                publishProgress(method, body, host)
                sb.clear()


            }
        } catch (err: Exception) {
            observableResponse.onError(err)
        }finally {
            isServerOn = false
            serverSocket?.close()
        }
    }

    override fun onProgressUpdate(vararg values: String?) {
        values[1]?.let {
            observableResponse.onNext(ServerStatus.Ok(it))
        }
    }

    override fun onPostExecute(result: Unit?) {
        serverSocket?.let {
            it.close()
            observableResponse.onNext(ServerStatus.Close())
        }

    }

}