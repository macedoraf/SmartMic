package br.com.rafael.smartmic.presentation

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.transition.Transition
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction.TRANSIT_FRAGMENT_OPEN
import androidx.transition.FragmentTransitionSupport
import br.com.rafael.smartmic.R
import br.com.rafael.smartmic.presentation.connected.ConnectedFragment
import br.com.rafael.smartmic.presentation.home.GuestHomeFragment
import br.com.rafael.smartmic.utill.Injector
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        const val MY_PERMISSIONS_REQUEST_RECORD_AUDIO: Int = 1000
    }

    lateinit var callback: OnRequestPermissionCallback

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

    fun requestMicrophonePermission(callback: OnRequestPermissionCallback) {
        this.callback = callback
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this, arrayOf(Manifest.permission.RECORD_AUDIO),
                MY_PERMISSIONS_REQUEST_RECORD_AUDIO
            )


        } else {
            callback.onSuccess()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            MY_PERMISSIONS_REQUEST_RECORD_AUDIO -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    callback.onSuccess()
                } else {
                    callback.onFailure()
                }
            }
        }
    }

    interface OnRequestPermissionCallback {
        fun onSuccess()
        fun onFailure()
    }


}
