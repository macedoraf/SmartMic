package br.com.rafael.smartmic.presentation

import android.os.Bundle
import android.transition.Transition
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction.TRANSIT_FRAGMENT_OPEN
import androidx.transition.FragmentTransitionSupport
import br.com.rafael.smartmic.R
import br.com.rafael.smartmic.presentation.connected.ConnectedFragment
import br.com.rafael.smartmic.presentation.home.GuestHomeFragment
import br.com.rafael.smartmic.utill.Injector
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()

        goToHomeScreen()
    }

     fun goToHomeScreen() {
        replaceFragment(
            GuestHomeFragment.newInstance(
                Injector.HomeProviderComponent().provideGuestHomePresenter()
            )
        )
    }

    fun goToConnectedScreen(ip: String, port: String) {
        replaceFragment(
            ConnectedFragment.newInstance(
                ip,
                port,
                Injector
                    .ConnectedProviderComponent()
                    .provideConnectedPresenter("$ip:$port")
            )
        )
    }


    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(contentFrame.id, fragment)
            .setTransition(TRANSIT_FRAGMENT_OPEN)
            .commit()
    }

    fun showLoading() {
        contentFrame.visibility = View.GONE
        progressBar.visibility = View.VISIBLE
    }

    fun hideLoading() {
        contentFrame.visibility = View.VISIBLE
        progressBar.visibility = View.GONE
    }


}
