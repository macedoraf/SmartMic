package br.com.rafael.smartmic.di

import android.content.Context
import br.com.rafael.smartmic.data.DataSystemInfo
import br.com.rafael.smartmic.presentation.home.GuestHome
import br.com.rafael.smartmic.presentation.home.GuestHomeInteractor
import br.com.rafael.smartmic.presentation.home.GuestHomePresenter

/*
    Project SmartMic
    Created by Rafael in 11/10/2019
*/

class HomeProviderComponent(private val context: Context) {];

    fun provideGuestHomePresenter(): GuestHome.Presenter =
        GuestHomePresenter(provideGuestHomeInteractor())

    private fun provideGuestHomeInteractor(): GuestHome.Interactor =
        GuestHomeInteractor(provideDataSystemInfo())


    private fun provideDataSystemInfo(): DataSystemInfo =
        DataSystemInfo(provideContext())

    private fun provideContext(): Context {
        return context
    }


}