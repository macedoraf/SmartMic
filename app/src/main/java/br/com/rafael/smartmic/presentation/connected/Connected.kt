package br.com.rafael.smartmic.presentation.connected

/**
 * Created by Santander on 14/10/2019
 */
interface Connected {

    interface Presenter {
        fun attackView(view: View)
        fun start(ip: String, port: String)

    }

    interface View {
        fun showLoading()
        fun hideLoading()
    }

}