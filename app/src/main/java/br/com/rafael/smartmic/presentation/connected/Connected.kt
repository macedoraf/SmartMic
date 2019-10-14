package br.com.rafael.smartmic.presentation.connected

/**
 * Created by Santander on 14/10/2019
 */
interface Connected {

    interface Presenter {
        fun attackView(view: Connected.View)
        fun start(string: String, string1: String)

    }

    interface View {
        fun showLoading()
        fun hideLoading()
    }

}