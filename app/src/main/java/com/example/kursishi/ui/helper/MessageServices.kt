package com.example.kursishi.ui.helper

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MessageServices : FirebaseMessagingService(){
    override fun onMessageReceived(p0: RemoteMessage) {
        val s = p0.data["welcome"]
    }
}