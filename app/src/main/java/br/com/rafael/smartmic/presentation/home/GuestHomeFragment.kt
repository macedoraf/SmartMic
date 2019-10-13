package br.com.rafael.smartmic.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import br.com.rafael.smartmic.R
import br.com.rafael.smartmic.utill.HomeProviderComponent
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

    override fun fetchIpAdress(ip: String) {
        edtIP.setText(ip)
    }

    private fun setUpListeners() {

        imgConnect.setOnClickListener {
        mPresenter
        }

        imgMoreInfo.setOnClickListener {
            //Show Dialog
        }
    }


    companion object {
        fun newInstance(component: HomeProviderComponent): GuestHomeFragment {
            val guestHomeFragment = GuestHomeFragment()
            guestHomeFragment.mPresenter = component.provideGuestHomePresenter()
            return guestHomeFragment
        }
    }
}