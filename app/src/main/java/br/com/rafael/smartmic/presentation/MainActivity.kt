package br.com.rafael.smartmic.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.com.rafael.smartmic.R
import br.com.rafael.smartmic.di.HomeProviderComponent
import br.com.rafael.smartmic.presentation.home.GuestHomeFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction()
            .replace(
                contentFrame.id,
                GuestHomeFragment.newInstance(HomeProviderComponent(this))
            )
            .commit()
    }


}
