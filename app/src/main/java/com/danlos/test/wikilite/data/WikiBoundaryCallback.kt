package com.danlos.test.wikilite.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import com.danlos.test.wikilite.api.WikiService
import com.danlos.test.wikilite.api.searchWiki
import com.danlos.test.wikilite.db.WikiLocalCache
import com.danlos.test.wikilite.model.Wiki

class WikiBoundaryCallback(
    private val query: String,
    private val service: WikiService,
    private val cache: WikiLocalCache
    ):PagedList.BoundaryCallback<Wiki>() {

    private var lastOffset = 0
    private var _networkErrors = MutableLiveData<String>()
    val networkErrors: LiveData<String>
        get() = _networkErrors

    private var isRequestInProgress = false

    override fun onZeroItemsLoaded() {
        requestAndSave(query)
    }

    override fun onItemAtEndLoaded(itemAtEnd: Wiki) {
        requestAndSave(query)
    }

    fun requestAndSave(query: String){
        if (isRequestInProgress) return

        isRequestInProgress = true
        searchWiki(service, query, NETWORK_PAGE_SIZE, lastOffset,
            {wikis, continuation ->
                cache.insert(wikis){
                    lastOffset = continuation.offset
                    isRequestInProgress = false
                }
            },
            {error ->
                _networkErrors.postValue(error)
                isRequestInProgress = false
            })
    }

    companion object {
        private const val NETWORK_PAGE_SIZE = 50
    }
}
