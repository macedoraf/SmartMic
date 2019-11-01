package br.com.rafael.smartmic.presentation.connected

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import br.com.rafael.smartmic.R
import br.com.rafael.smartmic.presentation.MainActivity
import br.com.rafael.smartmic.presentation.SmartMicApplication
import br.com.rafael.smartmic.utill.RandomSeconds
import br.com.rafael.smartmic.utill.hideActivityLoading
import br.com.rafael.smartmic.utill.showActivityLoading
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.connected_fragment.*
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
        return inflater.inflate(R.layout.connected_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()
    }

    private fun setupListeners() {
        imgDisconnect.setOnClickListener {
            presenter.requestDisconnect()

        }
        lblSend.setOnClickListener {
            presenter.sendMessage(edtSendField.text.toString())
        }

        btnTurnOffMic.setOnClickListener {
            presenter.requestCloseMic()
        }
    }

    override fun onStart() {
        super.onStart()
        (activity as MainActivity).requestMicrophonePermission(object :
            MainActivity.OnRequestPermissionCallback {
            override fun onSuccess() {
                presenter.start(ip, port)
            }

            override fun onFailure() {
                returnToHomeScreen()
            }

        })

    }

    override fun onDestroy() {
        presenter.onDestroy()
        super.onDestroy()
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

    //TODO : Depois remover daqui
    override fun startPingTimer() {
        timer.schedule(object : TimerTask() {
            override fun run() {
                presenter.pingHost()
            }

        }, 0, RandomSeconds.getSeconds(20, 60))
    }

    //TODO : Depois remover daqui
    override fun cancelPingTimer() {
        timer.cancel()
    }

    override fun showInfoDialog(@StringRes errorMessage: Int) {
        MaterialAlertDialogBuilder(context)
            .setTitle(R.string.information_dialog_title)
            .setMessage(errorMessage)
            .setCancelable(false)
            .setNeutralButton(R.string.dialog_button_ok, null)
            .create()
            .show()
    }


    override fun showErrorDialog(@StringRes errorMessage: Int) {
        MaterialAlertDialogBuilder(context)
            .setTitle(R.string.error_dialog_title)
            .setMessage(errorMessage)
            .setCancelable(false)
            .setNeutralButton(R.string.dialog_button_ok) { _, _ ->
                returnToHomeScreen()
            }
            .create()
            .show()
    }

    override fun returnToHomeScreen() {
        (activity as MainActivity).goToHomeScreen()
    }

    override fun resetMessageField() {
        edtSendField.text?.clear()
    }

    override fun setUpdatingQueuePosition() {
        lblQueuePosition.setText(R.string.connected_screen_updating_queue_position)
    }

    override fun showMicPanel() {
        micPanel.visibility = View.VISIBLE
        imgMicStatus.visibility = View.VISIBLE
    }

    override fun changeToMutedMic() {
        btnMuteUmute.setImageDrawable(this.resources.getDrawable(R.drawable.ic_orange_mic_muted))
    }

    override fun changeToUnmutedMic() {
        btnMuteUmute.setImageDrawable(this.resources.getDrawable(R.drawable.ic_orange_umuted_mic))
    }

    override fun hideMicPanel() {
        micPanel.visibility = View.GONE
        imgMicStatus.visibility = View.GONE
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