package br.com.rafael.smartmic.domain

import br.com.rafael.smartmic.data.WebsocketRepository
import br.com.rafael.smartmic.utill.Either
import br.com.rafael.smartmic.utill.Failure
import br.com.rafael.smartmic.utill.Interactor
import io.reactivex.Observable

/*
    Project SmartMic
    Created by Rafael in 13/10/2019
*/

class ConnectToHost(private val repository: WebsocketRepository) :
    Interactor<Observable<String>, ConnectToHost.ConnectToHostParam>() {

    override suspend fun run(params: ConnectToHostParam): Either<Failure, Observable<String>> {
        return repository.initConnection("${params.ip}:${params.port}")
    }

    data class ConnectToHostParam(val ip: String, val port: String)
}