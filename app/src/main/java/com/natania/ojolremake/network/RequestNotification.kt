package com.natania.ojolremake.network

import com.google.gson.annotations.SerializedName
import com.natania.ojolremake.model.Booking

class RequestNotification{

    @SerializedName("to")
    var token: String? = null

    @SerializedName("data")
    var sendNotificationModel: Booking? = null
}