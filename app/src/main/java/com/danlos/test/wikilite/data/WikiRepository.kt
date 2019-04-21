package com.danlos.test.wikilite.data

import androidx.paging.LivePagedListBuilder
import com.danlos.test.wikilite.api.WikiService
import com.danlos.test.wikilite.db.WikiLocalCache
import com.danlos.test.wikilite.model.WikiSearchResult

class WikiRepository(
    private val service: WikiService,
    private val cache: WikiLocalCache
) {
    fun search(query: String): WikiSearchResult{

        //Get data from the local cache
        val dataSourceFactory = cache.wikisAsIs(query)//.wikisByTitle(query)

        //Make the boundary callback for the paged lib to work
        val boundaryCallback = WikiBoundaryCallback(query, service, cache)
        val networkErrors = boundaryCallback.networkErrors
        val data = LivePagedListBuilder(dataSourceFactory, DATABASE_PAGE_SIZE)
            .setBoundaryCallback(boundaryCallback)
            .build()

        return WikiSearchResult(data, networkErrors)
    }

    companion object {
        private const val DATABASE_PAGE_SIZE = 20
    }
}