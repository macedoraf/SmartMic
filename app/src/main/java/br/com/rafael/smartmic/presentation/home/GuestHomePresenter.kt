package br.com.rafael.smartmic.presentation.home

/*
    Project SmartMic
    Created by Rafael in 11/10/2019
*/

class GuestHomePresenter(
    private val interactor: GuestHome.Interactor,
    private var view: GuestHome.View? = null
) : GuestHome.Presenter {


    override fun attachView(view: GuestHome.View) {
        this.view = view
    }

    override fun onStart() {
        interactor.whenLoadGuestHomeScreen()
    }

    override fun onWifiOn(ip: String) {
        view?.fetchIpAdress(ip)
    }

    override fun onWifiOff() {
        view?.showAlertDialog("Infomation", "Connect to wifi first")
    }

}