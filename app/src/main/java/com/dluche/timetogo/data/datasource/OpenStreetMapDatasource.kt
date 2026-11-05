package com.dluche.timetogo.data.datasource

import com.dluche.timetogo.data.response.AddressSearchResponse

interface OpenStreetMapDatasource {
    suspend fun searchAddress(searchQuery: String, limit: Int = 10): List<AddressSearchResponse>?
}