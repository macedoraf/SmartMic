package br.com.rafael.smartmic.utill

import android.app.Application
import android.content.Context
import br.com.rafael.smartmic.data.DataSystemInfo
import br.com.rafael.smartmic.data.SmartMicService
import br.com.rafael.smartmic.domain.GetWifiIpAdress
import br.com.rafael.smartmic.presentation.connected.Connected
import br.com.rafael.smartmic.presentation.connected.ConnectedPresenter
import br.com.rafael.smartmic.presentation.home.GuestHome
import br.com.rafael.smartmic.presentation.home.GuestHomePresenter
import com.google.gson.GsonBuilder
import com.tinder.scarlet.Lifecycle
import com.tinder.scarlet.Scarlet
import com.tinder.scarlet.lifecycle.android.AndroidLifecycle
import com.tinder.scarlet.messageadapter.gson.GsonMessageAdapter
import com.tinder.scarlet.websocket.okhttp.newWebSocketFactory
import com.tinder.streamadapter.coroutines.CoroutinesStreamAdapterFactory
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

    class HomeProviderComponent(private val context: Context) {

        fun provideGuestHomePresenter(): GuestHome.Presenter =
            GuestHomePresenter(provideGetWifiIpAdress())

        private fun provideGetWifiIpAdress(): GetWifiIpAdress =
            GetWifiIpAdress(provideDataSystemInfo())

        private fun provideDataSystemInfo(): DataSystemInfo = DataSystemInfo(provideContext())

        private fun provideContext(): Context {
            return context
        }

    }

    class ConnectedProviderComponent {

        fun provideConnectedPresenter(): Connected.Presenter {
            return ConnectedPresenter()
        }

        private fun provideLifecycle(application: Application): Lifecycle =
            AndroidLifecycle.ofApplicationForeground(application)


        fun provideSmartMicService(ipPort: String, application: Application): SmartMicService {
            val scarlet = Scarlet.Builder()
                .lifecycle(provideLifecycle(application))
                .webSocketFactory(provideOkHttpClient.newWebSocketFactory("ws://$ipPort"))
                .addMessageAdapterFactory(GsonMessageAdapter.Factory())
                .addStreamAdapterFactory(CoroutinesStreamAdapterFactory())
                .build()

            return scarlet.create()
        }


    }
}
