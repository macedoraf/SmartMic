package br.com.rafael.smartmic.utill

import android.content.Context
import br.com.rafael.smartmic.data.DataSystemInfo
import br.com.rafael.smartmic.data.http.HostRepository
import br.com.rafael.smartmic.domain.ConnectToHost
import br.com.rafael.smartmic.domain.GetWifiIpAdress
import br.com.rafael.smartmic.presentation.SmartMicApplication
import br.com.rafael.smartmic.presentation.connected.Connected
import br.com.rafael.smartmic.presentation.connected.ConnectedPresenter
import br.com.rafael.smartmic.presentation.home.GuestHome
import br.com.rafael.smartmic.presentation.home.GuestHomePresenter
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.Request
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

    private fun retrofit2(hostAddress: String): Retrofit =
        Retrofit.Builder()
            .baseUrl(hostAddress)
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

    private fun context(): Context = SmartMicApplication.getInstance()


    class HomeProviderComponent {
        fun provideGuestHomePresenter(): GuestHome.Presenter =
            GuestHomePresenter(provideGetWifiIpAddress())

        private fun provideGetWifiIpAddress(): GetWifiIpAdress =
            GetWifiIpAdress(dataSystemInfo)
    }

    class ConnectedProviderComponent {
        fun provideConnectedPresenter(ipHostAddress: String): Connected.Presenter.Input {
            return ConnectedPresenter(provideConnectToHost(ipHostAddress))
        }

        private fun provideConnectToHost(ipHostAddress: String): ConnectToHost {
            return ConnectToHost(dataSystemInfo, provideHostRepository(ipHostAddress))
        }

        private fun provideHostRepository(ipHostAddress: String): HostRepository {
            return HostRepository(gson, okHttpClient,provideRequestBuilder(ipHostAddress))
        }

        private fun provideRequestBuilder(ipHostAddress: String): Request.Builder {
            return Request.Builder().url("http://$ipHostAddress")
                .header("accept", "application/json")
                .method("post",null)

        }

    }
}
