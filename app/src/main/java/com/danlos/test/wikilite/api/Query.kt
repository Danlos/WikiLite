package com.danlos.test.wikilite.api

import com.danlos.test.wikilite.model.Wiki
import com.google.gson.annotations.SerializedName

data class Query(
    @SerializedName("search") val searchResults: List<Wiki> = emptyList()
)