package com.danlos.test.wikilite.api

import android.util.Log
import com.danlos.test.wikilite.model.Wiki
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
private const val TAG = "DNLS"
fun searchWiki(service: WikiService,
               searchStr: String,
               itemsPerPage: Int,
               offset: Int,
               onSuccess: (wikis: List<Wiki>, continuation: Continue ) -> Unit, //Callback for success
               onError: (error: String) -> Unit //Callback for fail
) {
    service.searchWiki(searchStr,itemsPerPage,offset).enqueue(
        object: Callback<WikiSearchResponse> {
            override fun onFailure(call: Call<WikiSearchResponse>, t: Throwable) {
                Log.d(TAG, "failed to get Data" )
                onError(t.message ?: "unknown error")
            }

            override fun onResponse(call: Call<WikiSearchResponse>, response: Response<WikiSearchResponse>) {
                Log.d(TAG, "response: $response")
                if(response.isSuccessful){
                    val wikis = response.body()?.query?.searchResults ?: emptyList()
                    val cont = response.body()?.continuation ?: Continue(0,"")
                    onSuccess(wikis, cont)
                }
                else{
                    onError(response.errorBody()?.string() ?: "Unknown error")
                }
            }
        }
    )
}

/*
* Wikipedia API w/Retrofit
* */

interface WikiService{
    // Wikipedia search api
    @GET("/w/api.php?action=query&format=json&list=search&utf8=1")
    fun searchWiki(@Query("srsearch") search: String,
                   @Query("srlimit") pageLimit: Int,
                   @Query("sroffset") pageOffset: Int): Call<WikiSearchResponse>

    companion object {
        private const val BASE_URL = "https://en.wikipedia.org/"
        fun create():WikiService{
            val logger = HttpLoggingInterceptor()
            logger.level = HttpLoggingInterceptor.Level.BASIC
            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(WikiService::class.java)
        }
    }
}