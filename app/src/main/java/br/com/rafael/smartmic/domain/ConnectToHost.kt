package br.com.rafael.smartmic.domain

import br.com.rafael.smartmic.data.DataSystemInfo
import br.com.rafael.smartmic.data.http.HostRepository
import br.com.rafael.smartmic.data.http.ResponseType
import br.com.rafael.smartmic.data.httpserver.ServerSocketTask
import br.com.rafael.smartmic.data.httpserver.ServerStatus
import br.com.rafael.smartmic.presentation.connected.Connected
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import rx.Subscription

class ConnectToHost(
    private val systemInfo: DataSystemInfo,
    private val hostRepository: HostRepository,
    private val randomPort: Int = systemInfo.getFreeRandomPort(ragePortStart, ragePortEnd),
    private var serverSocketTask: ServerSocketTask? = null

) {
    lateinit var presenter: Connected.Presenter
    private var serverSubscription: Subscription? = null
    private var hostSubscription: Disposable? = null
    private var pingSubscription: Disposable? = null

    companion object {
        const val ragePortStart = 1000
        const val ragePortEnd = 65000
    }


    fun initConnection() {
        startLocalHttpServer()
    }

    fun pingHost() {
        pingSubscription = hostRepository.sendPing()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .filter { it is ResponseType.Error }
            .subscribe(
                { presenter.onHostDisconnect() },
                { presenter.onHostDisconnect() })
    }

    fun closeHost() {
        serverSocketTask?.shutdownServer()
        serverSubscription?.unsubscribe()
        hostSubscription?.dispose()
        pingSubscription?.dispose()
    }

    private fun startLocalHttpServer() {
        serverSocketTask = ServerSocketTask()
        serverSocketTask?.startServer(randomPort)
            ?.subscribe {
                when (it) {
                    is ServerStatus.Started -> {
                        requestConnectionToHost(
                            systemInfo.getIp(),
                            randomPort,
                            systemInfo.getDeviceId()
                        )
                    }
                    is ServerStatus.QueuePosition -> {
                        it.message?.let { message ->
                            presenter.updateQueuePosition(message.toInt())
                        }

                    }
                }
            }
    }

    private fun requestConnectionToHost(
        ipAddress: String,
        freeRandomPort: Int,
        deviceId: String
    ) {
        hostSubscription =
            hostRepository.sendRequestConnectionToHost(ipAddress, freeRandomPort, deviceId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .filter { it is ResponseType.Ok }
                .subscribe(
                    {
                        presenter.onConnectSuccess()
                    },
                    {
                        presenter.onHostNotFound()
                    }
                )


    }

    fun sendDisconnectRequest() {
        hostSubscription = hostRepository.sendRequestDisconnect(
            systemInfo.getIp(),
            randomPort,
            systemInfo.getDeviceId()
        ).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { presenter.onHostDisconnect() },
                { }//Tratar Erro
            )
    }

    fun sendMessage(message: String) {
        hostSubscription = hostRepository.sendMessage(
            message,
            systemInfo.getIp(),
            randomPort,
            systemInfo.getDeviceId()
        ).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {},
                {}
            )
    }
}