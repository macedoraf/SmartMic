package br.com.rafael.smartmic.domain

import br.com.rafael.smartmic.data.DataSystemInfo
import br.com.rafael.smartmic.data.http.HostRepository
import br.com.rafael.smartmic.data.httpserver.HttpServer
import br.com.rafael.smartmic.data.httpserver.ServerStatus
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import rx.Subscription

class ConnectToHost(
    private val systemInfo: DataSystemInfo,
    private val hostRepository: HostRepository,
    private val randomPort: Int = systemInfo.getFreeRandomPort(ragePortStart, ragePortEnd),
    private val httpServer: HttpServer = HttpServer(randomPort)

) {
    private var serverSubscription: Subscription? = null
    private var hostSubscription: Disposable? = null

    companion object {
        const val ragePortStart = 1000
        const val ragePortEnd = 65000
    }


    fun initConnection() {
        startLocalHttpServer()
    }

    fun closeHost() {
        httpServer.stop()
        serverSubscription?.unsubscribe()
        hostSubscription?.dispose()
    }

    private fun startLocalHttpServer() {
        serverSubscription = httpServer.subscribe()
            .subscribe ({
                when (it) {
                    is ServerStatus.Start -> requestConnectionToHost(
                        systemInfo.getIp(),
                        randomPort,
                        systemInfo.getDeviceId()
                    )
                    is ServerStatus.CloseMic -> {
                    } //TODO
                    is ServerStatus.OpenMic -> {
                    } //TODO
                }
            },{
                //TODO: Tratar a exceção e esconder o loadig
            })

        httpServer.iziStart()


    }

    private fun requestConnectionToHost(
        ipAddress: String,
        freeRandomPort: Int,
        deviceId: String
    ) {
        hostSubscription = hostRepository.sendRequestConnectionToHost(ipAddress, freeRandomPort, deviceId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe ({
                when(it){
                    is Any ->{} //TODO: Hide Loading
                    is Exception -> {}//TODO: Hide Loading
                }
            },{
                //TODO: Tratar a exceção e esconder o loading
            })
    }

}