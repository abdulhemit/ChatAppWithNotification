package com.example.chatappwithnotification



import com.example.chatappwithnotification.constants.Companion.CONTENT_TYPE
import com.example.chatappwithnotification.constants.Companion.SERVER_KEY
import com.example.chatappwithnotification.model.PushNotification
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface NotificationAPI {



    @Headers("Authorization: key=$SERVER_KEY", "Content-Type:$CONTENT_TYPE")
    @POST("fcm/send")
    suspend fun postNotification(
        @Body notification: PushNotification

    ): Response<ResponseBody>




}