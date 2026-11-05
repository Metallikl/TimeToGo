package com.dluche.timetogo.domain.repository

import com.dluche.timetogo.data.response.AddressSearchResponse

interface OpenStreetMapRepository {
    suspend fun searchAddress(searchQuery: String, limit: Int = 10): Result<List<AddressSearchResponse>>
}