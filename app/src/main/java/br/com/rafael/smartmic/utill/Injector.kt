package br.com.rafael.smartmic.utill

import android.content.Context
import br.com.rafael.smartmic.data.DataSystemInfo
import br.com.rafael.smartmic.data.WebsocketRepository
import br.com.rafael.smartmic.domain.ConnectToHost
import br.com.rafael.smartmic.domain.GetWifiIpAdress
import br.com.rafael.smartmic.presentation.connected.Connected
import br.com.rafael.smartmic.presentation.connected.ConnectedPresenter
import br.com.rafael.smartmic.presentation.home.GuestHome
import br.com.rafael.smartmic.presentation.home.GuestHomePresenter
import okhttp3.OkHttpClient

/*
    Project SmartMic
    Created by Rafael in 11/10/2019
*/

sealed class Injector {

    class HomeProviderComponent(private val context: Context) {

        fun provideGuestHomePresenter(): GuestHome.Presenter =
            GuestHomePresenter(provideGetWifiIpAdress())

        private fun provideConnectToHost(): ConnectToHost {
            return ConnectToHost(provideWebSocketRepository())
        }

        private fun provideWebSocketRepository(): WebsocketRepository {
            return WebsocketRepository(provideOkHttpClient())
        }

        private fun provideOkHttpClient(): OkHttpClient {
            return OkHttpClient.Builder().build()
        }

        private fun provideGetWifiIpAdress(): GetWifiIpAdress =
            GetWifiIpAdress(provideDataSystemInfo())


        private fun provideDataSystemInfo(): DataSystemInfo =
            DataSystemInfo(provideContext())

        private fun provideContext(): Context {
            return context
        }

    }

    class ConnectedProviderComponent {

        fun provideConnectedPresenter(): Connected.Presenter{
            return ConnectedPresenter()
        }

    }
}
