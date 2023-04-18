package com.example.chatappwithnotification

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.chatappwithnotification.databinding.ActivityMainBinding
import com.example.chatappwithnotification.model.NotificationData
import com.example.chatappwithnotification.model.PushNotification
import com.example.chatappwithnotification.model.RetrofitIntence
import com.google.firebase.iid.FirebaseInstanceIdReceiver
import com.google.firebase.iid.internal.FirebaseInstanceIdInternal
import com.google.firebase.messaging.FirebaseMessaging
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit

const val TOPIC = "/topics/myTopic"

class MainActivity : AppCompatActivity() {
    val TAG = "MainActivity"

    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        FirebaseServec.sharedPreferences = getSharedPreferences("sharedPreferences", Context.MODE_PRIVATE)
        FirebaseMessaging.getInstance().token.addOnCompleteListener {
            if (it.isSuccessful){
                FirebaseServec.token = it.result
                binding.etToken.setText(it.result.toString())
            }
        }

        FirebaseMessaging.getInstance().subscribeToTopic(TOPIC)

        binding.btnSend.setOnClickListener {
            var title = binding.etTitle.text.toString()
            var message = binding.etMessage.text.toString()
            var recipiendToken = binding.etToken.text.toString()
            if (title.isNotEmpty() && message.isNotEmpty()){
                PushNotification(
                    NotificationData(title,message)
                    , recipiendToken
                ).also {
                    senNotification(it)
                }
            }

        }


    }

    private fun senNotification(notivication: PushNotification) = CoroutineScope(Dispatchers.IO).launch {
        try {
            val response = RetrofitIntence.api.postNotification(notivication)
            if (response.isSuccessful){
                Log.e(TAG,"Response ${Gson().toJson(response)}")
            }else{
                Log.e(TAG,response.errorBody().toString())
            }

        }catch (e:Exception){
            Log.e(TAG,e.message.toString())
        }

    }
}