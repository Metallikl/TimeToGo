package com.dluche.timetogo.data.datasource

import com.dluche.timetogo.data.client.ClientConsts.OPEN_STREET_FORMAT_PARAM
import com.dluche.timetogo.data.client.ClientConsts.OPEN_STREET_FORMAT_VALUE
import com.dluche.timetogo.data.client.ClientConsts.OPEN_STREET_LIMIT_PARAM
import com.dluche.timetogo.data.client.ClientConsts.OPEN_STREET_QUERY_PARAM
import com.dluche.timetogo.data.client.ClientConsts.OPEN_STREET_SEARCH_ENDPOINT
import com.dluche.timetogo.data.response.AddressSearchResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import org.koin.core.annotation.Single

@Single
class OpenStreetMapDatasourceImpl(
    private val client: HttpClient,
) :OpenStreetMapDatasource {
    override suspend fun searchAddress(
        searchQuery: String,
        limit: Int
    ): List<AddressSearchResponse>? {
        return client.get(OPEN_STREET_SEARCH_ENDPOINT){
            parameter(OPEN_STREET_QUERY_PARAM, searchQuery)
            parameter(OPEN_STREET_FORMAT_PARAM, OPEN_STREET_FORMAT_VALUE)
            parameter(OPEN_STREET_LIMIT_PARAM, limit)
        }.body()
    }
}