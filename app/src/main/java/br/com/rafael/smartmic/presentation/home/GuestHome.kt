package br.com.rafael.smartmic.presentation.home

import br.com.rafael.smartmic.utill.Either

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

    }


}