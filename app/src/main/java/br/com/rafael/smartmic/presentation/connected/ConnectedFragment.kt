package br.com.rafael.smartmic.presentation.connected

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import br.com.rafael.smartmic.R
import br.com.rafael.smartmic.utill.hideActivityLoading
import br.com.rafael.smartmic.utill.showActivityLoading

/*
    Project SmartMic
    Created by Rafael in 13/10/2019
*/

class ConnectedFragment : Fragment(), Connected.View {

    lateinit var presenter: Connected.Presenter

    lateinit var ip: String

    lateinit var port: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (arguments == null)
            throw RuntimeException("Arguments cannot be null")

        if (arguments!!.getString(IP_ARG) == null)
            throw RuntimeException("IP cannot be null")
        else
            ip = arguments!!.getString(IP_ARG)!!

        if (arguments!!.getString(PORT_ARG) == null)
            throw RuntimeException("Port cannot be null")
        else
            port = arguments!!.getString(PORT_ARG)!!


    }

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

    companion object {
        const val IP_ARG = "ip_start"
        const val PORT_ARG = "port_start"

        fun newInstance(
            ip: String,
            port: String,
            presenter: Connected.Presenter
        ): ConnectedFragment {
            val fragment = ConnectedFragment()
            fragment.presenter = presenter
            presenter.attackView(fragment)
            setArgs(ip, port, fragment)
            return fragment
        }

        private fun setArgs(
            ip: String,
            port: String,
            fragment: ConnectedFragment
        ) {
            val args = Bundle()
            args.putString(IP_ARG, ip)
            args.putString(PORT_ARG, port)
            fragment.arguments = args
        }
    }
}