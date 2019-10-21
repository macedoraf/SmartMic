package br.com.rafael.smartmic.data.http

/*
    Project SmartMic
    Created by Rafael in 21/10/2019
*/

sealed class ResponseType {

    object Ok : ResponseType()
    object Error : ResponseType()
}