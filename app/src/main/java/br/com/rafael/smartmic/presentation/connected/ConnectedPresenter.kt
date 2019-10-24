package br.com.rafael.smartmic.presentation.connected

import br.com.rafael.smartmic.domain.ConnectToHost

/**
 * Created by Santander on 14/10/2019
 */
class ConnectedPresenter(
    private val connectToHost: ConnectToHost,
    private var view: Connected.View? = null
) : Connected.Presenter {

    override fun pingHost() {
        connectToHost.pingHost()
    }

    override fun onConnectSuccess() {
        view?.startPingTimer()
    }

    init {
        connectToHost.presenter = this
    }

    override fun hideLoading() {
        view?.hideLoading()
    }

    override fun showLoading() {
        view?.showLoading()
    }


    override fun attackView(view: Connected.View) {
        this.view = view
    }

    override fun start(ip: String, port: String) {
        view?.showLoading()
        connectToHost.initConnection()
    }

    override fun updateQueuePosition(position: Int) {
        view?.setQueuePosition(position.toString())
    }

    override fun onHostDisconnect() {
        view?.showErrorDialog()

    }
}