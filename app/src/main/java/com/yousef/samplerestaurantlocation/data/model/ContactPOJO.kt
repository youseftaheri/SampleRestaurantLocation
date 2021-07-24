package com.yousef.samplerestaurantlocation.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ContactPOJO {
    constructor()
    constructor(statusCode: Int, messageInput: String){
        meta = Meta(statusCode,messageInput)
        response = Response(messageInput)
    }

    @JvmField
    @Expose
    @SerializedName("meta")
    var meta: Meta? = null

    @JvmField
    @Expose
    @SerializedName("response")
    var response: Response? = null


    inner class Meta {
        constructor()
        constructor(statusCode: Int, error: String) {
            code = statusCode
            errorDetail = error
        }

        @JvmField
        @Expose
        @SerializedName("code")
        var code: Int? = null

        @JvmField
        @Expose
        @SerializedName("errorDetail")
        var errorDetail: String? = null
    }

    inner class Response {
        constructor()
        constructor(data: String){
            venue = Venue(data)
        }
        @JvmField
        @Expose
        @SerializedName("venue")
        var venue: Venue? = null
    }

    inner class Venue {
        constructor()
        constructor(data: String){
            contact = Contact(data)
        }
        @JvmField
        @Expose
        @SerializedName("contact")
        var contact: Contact? = null
    }

    inner class Contact {
        constructor()
        constructor(data: String){
            formattedPhone = data
            twitter = data
            facebook = data
        }
        @JvmField
        @Expose
        @SerializedName("formattedPhone")
        var formattedPhone: String? = null

        @JvmField
        @Expose
        @SerializedName("twitter")
        var twitter: String? = null

        @JvmField
        @Expose
        @SerializedName("facebook")
        var facebook: String? = null
    }
}