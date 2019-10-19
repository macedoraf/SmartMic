package br.com.rafael.smartmic.presentation.connected

import br.com.rafael.smartmic.domain.ConnectToHost

/**
 * Created by Santander on 14/10/2019
 */
class ConnectedPresenter(
    val connectToHost: ConnectToHost,
    var view: Connected.View? = null
) : Connected.Presenter {


    override fun attackView(view: Connected.View) {
        this.view = view
    }

    override fun start(ip: String, port: String) {
        view?.showLoading()
        connectToHost.initConnection()
    }
}