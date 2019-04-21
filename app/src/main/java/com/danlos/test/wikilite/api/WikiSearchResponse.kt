package com.danlos.test.wikilite.api

import com.google.gson.annotations.SerializedName
import org.json.JSONObject

data class WikiSearchResponse (
    @SerializedName("query") val query: Query,
    @SerializedName("continue") val continuation: Continue
    )