package br.com.rafael.smartmic.presentation.connected

/**
 * Created by Santander on 14/10/2019
 */
class ConnectedPresenter(var view: Connected.View? = null) : Connected.Presenter {


    override fun attackView(view: Connected.View) {
        this.view = view
    }

    override fun start(string: String, string1: String) {
        view?.showLoading()
    }
}