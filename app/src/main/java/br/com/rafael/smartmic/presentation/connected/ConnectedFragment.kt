package br.com.rafael.smartmic.presentation.connected

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import br.com.rafael.smartmic.R
import br.com.rafael.smartmic.presentation.SmartMicApplication
import br.com.rafael.smartmic.utill.RandomSeconds
import br.com.rafael.smartmic.utill.hideActivityLoading
import br.com.rafael.smartmic.utill.showActivityLoading
import kotlinx.android.synthetic.main.connected.*
import kotlinx.android.synthetic.main.guest_home_fragment.*
import java.util.*

/*
    Project SmartMic
    Created by Rafael in 13/10/2019
*/

class ConnectedFragment : Fragment(), Connected.View {

    lateinit var presenter: Connected.Presenter
    lateinit var ip: String
    lateinit var port: String
    private val timer by lazy { Timer() }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.connected, container, false)
    }

    override fun onStart() {
        super.onStart()
        presenter.start(ip, port)

    }

    override fun hideLoading() {
        hideActivityLoading()
    }

    override fun showLoading() {
        showActivityLoading()
    }

    override fun setQueuePosition(position: String) {
        lblQueuePosition.text = String.format(
            SmartMicApplication.getInstance().resources.getString(R.string.connected_screen_queue_position),
            position
        )
    }

    override fun startPingTimer() {
        timer.schedule(object :TimerTask(){
           override fun run() {
               presenter.pingHost()
           }

       },0,RandomSeconds.getSeconds(20,60))
    }

    override fun cancelPingTimer(){
        timer.cancel()
    }

    override fun showInfoDialog() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showErrorDialog() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun returnToHomeScreen() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    companion object {

        fun newInstance(
            ip: String,
            port: String,
            presenter: Connected.Presenter
        ): ConnectedFragment {
            val fragment = ConnectedFragment()
            fragment.presenter = presenter
            presenter.attackView(fragment)
            fragment.ip = ip
            fragment.port = port
            return fragment
        }

    }
}