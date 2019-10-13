package br.com.rafael.smartmic.utill

/*
    Project SmartMic
    Created by Rafael in 13/10/2019
*/

/**
 * Base Class for handling errors/failures/exceptions.
 * Every feature specific failure should extend [FeatureFailure] class.
 */
sealed class Failure {
    object NetworkConnection : Failure()
    object ServerError : Failure()

    /** * Extend this class for feature specific failures.*/
    abstract class FeatureFailure : Failure()

    object OnWifiDisconnected : FeatureFailure()
}