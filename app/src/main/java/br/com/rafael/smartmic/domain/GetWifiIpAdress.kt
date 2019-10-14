package br.com.rafael.smartmic.domain

import br.com.rafael.smartmic.data.DataSystemInfo
import br.com.rafael.smartmic.utill.Either
import br.com.rafael.smartmic.utill.Failure
import br.com.rafael.smartmic.utill.Interactor

/*
    Project SmartMic
    Created by Rafael in 13/10/2019
*/

class GetWifiIpAdress(private val systemDataSystemInfo: DataSystemInfo) : Interactor<String, Interactor.None>() {
    override suspend fun run(params: None): Either<Failure, String> {
        return if(systemDataSystemInfo.isWifiConnected()){
            val ipWithPort = systemDataSystemInfo.getIp() + ":8080"
            Either.Right(ipWithPort)
        }else{
            Either.Left(Failure.NetworkConnection)
        }
    }
}