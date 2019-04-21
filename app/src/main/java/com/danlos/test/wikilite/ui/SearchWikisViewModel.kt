package com.danlos.test.wikilite.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.danlos.test.wikilite.data.WikiRepository
import com.danlos.test.wikilite.model.Wiki
import com.danlos.test.wikilite.model.WikiSearchResult

class SearchWikisViewModel(private val repository: WikiRepository): ViewModel() {
    private val queryLiveData = MutableLiveData<String>()
    private val wikiResult: LiveData<WikiSearchResult> = Transformations.map(queryLiveData){
        repository.search(it)
    }

    val wikis: LiveData<PagedList<Wiki>> = Transformations.switchMap(wikiResult){it.data}
    val networkErrors: LiveData<String> = Transformations.switchMap(wikiResult){it.networkErrors}

    fun searchWiki(queryString: String){
        queryLiveData.postValue(queryString)
    }

    fun lastQueryValue():String? = queryLiveData.value
}