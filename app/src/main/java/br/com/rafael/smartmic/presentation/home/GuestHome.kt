package br.com.rafael.smartmic.presentation.home

/*
    Project SmartMic
    Created by Rafael in 11/10/2019
*/

interface GuestHome {

    interface View {
        fun fetchIpAdress(ip: String, port: String)
        fun showAlertDialog(title: String, message: String)
        fun showToast(string: String) //To Test

    }

    interface Presenter {
        fun onStart()

        fun attachView(view: View)

    }


}