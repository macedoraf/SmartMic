package br.com.rafael.smartmic.utill

import kotlin.random.Random

/*
    Project SmartMic
    Created by Rafael in 23/10/2019
*/

class RandomSeconds {

    companion object {
        private const val SECONDS = 1000L

        fun getSeconds(begin: Long, end: Long) = Random.nextLong(begin, end) * SECONDS

    }
}