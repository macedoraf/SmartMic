package br.com.rafael.smartmic.utill

import android.content.Context
import br.com.rafael.smartmic.data.DataSystemInfo
import br.com.rafael.smartmic.data.WebSocketRepository
import br.com.rafael.smartmic.domain.ConnectToHost
import br.com.rafael.smartmic.domain.GetWifiIpAdress
import br.com.rafael.smartmic.presentation.SmartMicApplication
import br.com.rafael.smartmic.presentation.connected.Connected
import br.com.rafael.smartmic.presentation.connected.ConnectedPresenter
import br.com.rafael.smartmic.presentation.home.GuestHome
import br.com.rafael.smartmic.presentation.home.GuestHomePresenter
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor


/*
    Project SmartMic
    Created by Rafael in 11/10/2019
*/

object Injector {

    private val provideOkHttpClient: OkHttpClient by lazy(LazyThreadSafetyMode.PUBLICATION) {
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()
    }

    private fun provideContext(): Context {
        return SmartMicApplication.getInstance()
    }

    private fun provideDataSystemInfo(): DataSystemInfo = DataSystemInfo(provideContext())




    class HomeProviderComponent {
        fun provideGuestHomePresenter(): GuestHome.Presenter =
            GuestHomePresenter(provideGetWifiIpAdress())
        private fun provideGetWifiIpAdress(): GetWifiIpAdress =
            GetWifiIpAdress(provideDataSystemInfo())
    }

    class ConnectedProviderComponent {

        fun provideConnectedPresenter(): Connected.Presenter {
            return ConnectedPresenter(ConnectToHost(provideWebSokectRepository()))
        }

        fun provideWebSokectRepository(): WebSocketRepository {
            return WebSocketRepository(GsonBuilder().create(), provideOkHttpClient, provideDataSystemInfo())
        }

    }
}
