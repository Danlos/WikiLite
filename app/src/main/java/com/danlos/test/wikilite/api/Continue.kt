package com.danlos.test.wikilite.api

import com.google.gson.annotations.SerializedName

data class Continue(
    @SerializedName("srOffset") val offset: Int,
    @SerializedName("continue") val continueVal: String)