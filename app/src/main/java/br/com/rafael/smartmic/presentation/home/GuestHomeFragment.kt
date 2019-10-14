package br.com.rafael.smartmic.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import br.com.rafael.smartmic.R
import br.com.rafael.smartmic.utill.Injector
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.guest_home_fragment.*


class GuestHomeFragment : Fragment(), GuestHome.View {

    lateinit var mPresenter: GuestHome.Presenter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.guest_home_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpListeners()
    }

    override fun onStart() {
        super.onStart()
        mPresenter.onStart()
    }

    override fun showAlertDialog(title: String, message: String) {
        MaterialAlertDialogBuilder(context)
            .setTitle(title)
            .setCancelable(false)
            .setMessage(message)
            .setNeutralButton("OK", null)
            .create()
            .show()
    }

    override fun fetchIpAdress(ip: String, port: String) {
        edtIP.setText(ip)
        edtPort.setText(port)
    }

    private fun setUpListeners() {

        imgConnect.setOnClickListener {

        }

        imgMoreInfo.setOnClickListener {
            //Show Dialog
        }

        imgQrCode.setOnClickListener {
            //Open Camera to READ QR code
        }
    }

    override fun showToast(string: String) {
        Toast.makeText(this.context, string, Toast.LENGTH_LONG).show()
    }



    companion object {
        fun newInstance(component: Injector.HomeProviderComponent): GuestHomeFragment {
            val guestHomeFragment = GuestHomeFragment()
            guestHomeFragment.mPresenter = component.provideGuestHomePresenter()
            guestHomeFragment.mPresenter.attachView(guestHomeFragment)
            return guestHomeFragment
        }
    }
}