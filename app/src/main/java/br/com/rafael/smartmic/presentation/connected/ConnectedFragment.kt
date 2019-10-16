package br.com.rafael.smartmic.presentation.connected

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import br.com.rafael.smartmic.R
import br.com.rafael.smartmic.utill.hideActivityLoading
import br.com.rafael.smartmic.utill.showActivityLoading
import kotlinx.android.synthetic.main.guest_home_fragment.*

/*
    Project SmartMic
    Created by Rafael in 13/10/2019
*/

class ConnectedFragment : Fragment(), Connected.View {

    lateinit var presenter: Connected.Presenter
    lateinit var ip: String
    lateinit var port: String


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