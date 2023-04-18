package com.example.chatappwithnotification.model

import com.example.chatappwithnotification.NotificationAPI
import com.example.chatappwithnotification.constants.Companion.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitIntence {

    companion object {

        private val retrofit by lazy {
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        val api by lazy {
             retrofit.create(NotificationAPI::class.java)
        }


    }
}