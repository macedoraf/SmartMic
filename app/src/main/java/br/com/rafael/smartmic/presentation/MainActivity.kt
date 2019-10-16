package br.com.rafael.smartmic.presentation

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
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

    private fun goToHomeScreen() {
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
                    .provideConnectedPresenter()
            )
        )
    }


    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(contentFrame.id, fragment)
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
