package br.com.rafael.smartmic.presentation.home

import br.com.rafael.smartmic.data.DataSystemInfo

/*
    Project SmartMic
    Created by Rafael in 11/10/2019
*/

class GuestHomeInteractor(
    private val dataSystemInfo: DataSystemInfo
) : GuestHome.Interactor {

    override fun whenLoadGuestHomeScreen() {
        if (dataSystemInfo.isWifiConnected()) {
//            presenter.onWifiOn(dataSystemInfo.getIp())
        } else {
//            presenter.onWifiOff()
        }
    }
}