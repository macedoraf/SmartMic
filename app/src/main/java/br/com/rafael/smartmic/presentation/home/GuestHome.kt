package br.com.rafael.smartmic.presentation.home

/*
    Project SmartMic
    Created by Rafael in 11/10/2019
*/

interface GuestHome {

    interface View {
        fun fetchIpAdress(ip: String)
        fun showAlertDialog(title: String, message: String)

    }

    interface Presenter {
        fun onStart()

        fun attachView(view: View)

        fun onWifiOn(ip: String)

        fun onWifiOff()

    }

    interface Interactor {
        fun whenLoadGuestHomeScreen()
    }

}