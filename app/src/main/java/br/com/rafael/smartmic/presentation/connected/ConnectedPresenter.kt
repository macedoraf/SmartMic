package br.com.rafael.smartmic.presentation.connected

import br.com.rafael.smartmic.R
import br.com.rafael.smartmic.domain.ConnectToHost

/**
 * Created by Santander on 14/10/2019
 */
class ConnectedPresenter(
    private val connectToHost: ConnectToHost,
    private var view: Connected.View? = null
) : Connected.Presenter {

    override fun requestDisconnect() {
        connectToHost.sendDisconnectRequest()
    }

    override fun onDestroy() {
        connectToHost.closeHost()
    }

    override fun pingHost() {
        connectToHost.pingHost()
    }

    override fun onConnectSuccess() {
        view?.hideLoading()
        view?.startPingTimer()
    }

    init {
        connectToHost.presenter = this
    }

    override fun onDisconnectedByGuest() {
        view?.returnToHomeScreen()
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
        view?.showErrorDialog(R.string.dialog_message_from_termineted_by_host)
    }

    override fun onHostNotFound() {
        view?.showErrorDialog(R.string.dialog_message_host_not_found)
    }

    override fun sendMessage(message:String) {
        if(message.isNotBlank()){
            view?.resetMessageField()
            connectToHost.sendMessage(message)
        }
    }

    override fun onMessageRecived() {
        view?.showInfoDialog(R.string.dialog_message_recived)
    }
}