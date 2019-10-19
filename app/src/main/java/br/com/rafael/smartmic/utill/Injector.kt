package br.com.rafael.smartmic.utill

import android.content.Context
import br.com.rafael.smartmic.data.DataSystemInfo
import br.com.rafael.smartmic.data.http.HostAPI
import br.com.rafael.smartmic.data.httpserver.HttpServer
import br.com.rafael.smartmic.domain.ConnectToHost
import br.com.rafael.smartmic.domain.GetWifiIpAdress
import br.com.rafael.smartmic.presentation.SmartMicApplication
import br.com.rafael.smartmic.presentation.connected.Connected
import br.com.rafael.smartmic.presentation.connected.ConnectedPresenter
import br.com.rafael.smartmic.presentation.home.GuestHome
import br.com.rafael.smartmic.presentation.home.GuestHomePresenter
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


/*
    Project SmartMic
    Created by Rafael in 11/10/2019
*/

object Injector {

    private val okHttpClient by lazy(LazyThreadSafetyMode.PUBLICATION) {
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()
    }

    private val gson by lazy(LazyThreadSafetyMode.PUBLICATION) { GsonBuilder().create() }

    private val dataSystemInfo by lazy { DataSystemInfo(context()) }

    private val httpServer by lazy { HttpServer() }

    private fun retrofit2(hostAddress: String): Retrofit =
        Retrofit.Builder()
            .baseUrl(hostAddress)
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

    private fun provideHostApi(hostIpAdress: String) =
        retrofit2(hostIpAdress).create<HostAPI>(HostAPI::class.java)


    private fun context(): Context = SmartMicApplication.getInstance()


    class HomeProviderComponent {
        fun provideGuestHomePresenter(): GuestHome.Presenter =
            GuestHomePresenter(provideGetWifiIpAdress())

        private fun provideGetWifiIpAdress(): GetWifiIpAdress =
            GetWifiIpAdress(dataSystemInfo)
    }

    class ConnectedProviderComponent {
        fun provideConnectedPresenter(ipHostAdress: String): Connected.Presenter {
            return ConnectedPresenter(provideConnectToHost(ipHostAdress))
        }

        fun provideConnectToHost(ipHostAdress: String): ConnectToHost {
            return ConnectToHost(dataSystemInfo, httpServer, provideHostApi("http://$ipHostAdress"))
        }

    }
}
