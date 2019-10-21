package br.com.rafael.smartmic.presentation.connected

import androidx.annotation.IdRes

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

    }

    interface View {
        fun showLoading()
        fun hideLoading()
        fun setQueuePosition(position: String)
    }

}