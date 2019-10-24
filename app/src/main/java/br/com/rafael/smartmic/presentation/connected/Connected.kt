package br.com.rafael.smartmic.presentation.connected

/**
 * Created by Santander on 14/10/2019
 */
interface Connected {

    interface Presenter {
        fun attackView(view: View)
        fun start(ip: String, port: String)

        fun updateQueuePosition(position: Int)

        fun hideLoading()
        fun showLoading()
        fun onConnectSuccess()
        fun onHostDisconnect()
        fun pingHost()

    }

    interface View {
        fun showLoading()
        fun hideLoading()
        fun showInfoDialog()
        fun showErrorDialog()
        fun returnToHomeScreen()
        fun setQueuePosition(position: String)
        fun startPingTimer()
        fun cancelPingTimer()
    }

}