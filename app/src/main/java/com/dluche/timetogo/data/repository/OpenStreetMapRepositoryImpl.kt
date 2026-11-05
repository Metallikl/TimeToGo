package com.dluche.timetogo.data.repository

import com.dluche.timetogo.data.datasource.OpenStreetMapDatasource
import com.dluche.timetogo.data.response.AddressSearchResponse
import com.dluche.timetogo.domain.repository.OpenStreetMapRepository
import com.dluche.timetogo.util.dispatcher.DispatcherProvider
import kotlinx.coroutines.withContext
import org.koin.core.annotation.Factory

@Factory
class OpenStreetMapRepositoryImpl(
    private val datasource: OpenStreetMapDatasource,
    private val dispatchers: DispatcherProvider
) : OpenStreetMapRepository {
    override suspend fun searchAddress(
        searchQuery: String,
        limit: Int
    ): Result<List<AddressSearchResponse>> = withContext(dispatchers.io) {
        runCatching {
            datasource.searchAddress(searchQuery, limit).orEmpty()
        }
    }
}
