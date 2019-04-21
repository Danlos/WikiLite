package com.danlos.test.wikilite.model

import androidx.lifecycle.LiveData
import androidx.paging.PagedList

data class WikiSearchResult(
    val data: LiveData<PagedList<Wiki>>,
    val networkErrors: LiveData<String>
)