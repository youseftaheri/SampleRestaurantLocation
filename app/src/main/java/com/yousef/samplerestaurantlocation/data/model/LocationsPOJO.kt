package com.yousef.samplerestaurantlocation.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class LocationsPOJO {
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
            venues = listOf(Venue(data))
        }
        @JvmField
        @Expose
        @SerializedName("venues")
        var venues: List<Venue>? = null
    }

    inner class Venue {
        constructor()
        constructor(data: String){
            id = data
            name = data
            location = Location(data)
        }
        @JvmField
        @Expose
        @SerializedName("id")
        var id: String? = null

        @JvmField
        @Expose
        @SerializedName("name")
        var name: String? = null

        @JvmField
        @Expose
        @SerializedName("location")
        var location: Location? = null
    }

    inner class Location {
        constructor()
        constructor(data: String){
            address = data
            crossStreet = data
            lat = 0.0
            lng = 0.0
            distance = 0.0
            postalCode = data
            country = data
            state = data
            city = data
        }
        @JvmField
        @Expose
        @SerializedName("address")
        var address: String? = null

        @JvmField
        @Expose
        @SerializedName("crossStreet")
        var crossStreet: String? = null

        @JvmField
        @Expose
        @SerializedName("lat")
        var lat: Double? = null

        @JvmField
        @Expose
        @SerializedName("lng")
        var lng: Double? = null

        @JvmField
        @Expose
        @SerializedName("distance")
        var distance: Double? = null

        @JvmField
        @Expose
        @SerializedName("postalCode")
        var postalCode: String? = null

        @JvmField
        @Expose
        @SerializedName("country")
        var country: String? = null

        @JvmField
        @Expose
        @SerializedName("state")
        var state: String? = null

        @JvmField
        @Expose
        @SerializedName("city")
        var city: String? = null
    }
}