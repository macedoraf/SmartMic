package br.com.rafael.smartmic.data.http

import io.reactivex.Observable
import retrofit2.http.POST

interface HostAPI {

    @POST
    fun connectToHost(param: Message.Connect): Observable<*>
}