package br.com.rafael.smartmic.presentation.connected

import androidx.annotation.StringRes

/**
 * Created by Santander on 14/10/2019
 */
interface Connected {


    interface Presenter {


        interface Input : Presenter {
            fun attackView(view: View)
            fun start(ip: String, port: String)
            fun onDestroy()
            fun requestDisconnect()
            fun requestCloseMic()
            fun sendMessage(message: String)
            fun pingHost()
            fun muteUnmuteMic()
        }

        interface Output : Presenter {
            fun onError(it: Throwable?)
            fun onHostNotFound()
            fun onConnectSuccess()
            fun onHostDisconnect()
            fun onDisconnectedByGuest()
            fun onMessageRecived()
            fun onOpenMicPanel()
            fun onMuteMic()
            fun onUnmuteMic()

            fun onCloseMic()
            fun updateQueuePosition(position: Int)
            fun onStartRecording()
            fun onStopRecording()
            fun onResumeRecording()
        }
    }

    interface View {
        fun showLoading()
        fun hideLoading()
        fun showInfoDialog(@StringRes errorMessage: Int)
        fun showErrorDialog(@StringRes errorMessage: Int)
        fun returnToHomeScreen()
        fun setQueuePosition(position: String)
        fun startPingTimer()
        fun cancelPingTimer()
        fun resetMessageField()
        fun setUpdatingQueuePosition()
        fun showMicPanel()
        fun changeToMutedMic()
        fun changeToUnmutedMic()
        fun hideMicPanel()
    }

}