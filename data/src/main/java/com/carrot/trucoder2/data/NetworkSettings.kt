package com.carrot.trucoder2.data

/** An object that contains functions and constants specifically related to network only. */
object NetworkSettings {

    private var isDeveloperMode: Boolean = true

    /** DEVELOPER URL which connects to development server */
    private const val DEVELOPER_URL = "https://trucoder-backend.herokuapp.com"
    /**  PRODUCTION URL which connects to production server */
    private const val PROD_URL = "https://trucoder-backend.herokuapp.com"

    fun getBaseUrl(): String {
        return if(isDeveloperMode) {
            DEVELOPER_URL
        } else {
            PROD_URL
        }
    }
}