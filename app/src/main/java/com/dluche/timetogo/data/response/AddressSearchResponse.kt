package com.dluche.timetogo.data.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AddressSearchResponse(
    @SerialName("place_id") val placeId: Long,
    @SerialName("osm_type") val osmType: String,
    @SerialName("osm_id" )val osmId: Long,
    @SerialName("lat") val lat: String,
    @SerialName("lon") val lon: String,
    @SerialName("display_name") val displayName: String
)