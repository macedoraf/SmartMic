package br.com.rafael.smartmic.presentation.home

import br.com.rafael.smartmic.domain.GetWifiIpAdress
import br.com.rafael.smartmic.utill.Failure
import br.com.rafael.smartmic.utill.Interactor
import io.reactivex.Observable

/*
    Project SmartMic
    Created by Rafael in 11/10/2019
*/

class GuestHomePresenter(
    private val getWifiIpAdress: GetWifiIpAdress,
    private var view: GuestHome.View? = null
) : GuestHome.Presenter {


    override fun attachView(view: GuestHome.View) {
        this.view = view
    }

    override fun onStart() {
        getWifiIpAdress.invoke(Interactor.None()) { it.either(::onFailure, ::onWifiConnected) }
    }

    private fun onFailure(failure: Failure) {
        when (failure) {
            is Failure.OnWifiDisconnected -> {
                view?.showAlertDialog("Alert", "Please connect to wifi")
            }
        }

    }

    private fun onWifiConnected(ipAdress: String) {
        val split = ipAdress.split(":")

        view?.fetchIpAdress(split[0],split[1])
    }

    private fun onHostConnect(observable: Observable<String>) {
        observable
            .subscribe {
                view?.showToast(it)
            }.dispose()

    }


}