package com.danlos.test.wikilite.db

import android.util.Log
import androidx.paging.DataSource
import com.danlos.test.wikilite.model.Wiki
import java.util.concurrent.Executor

class WikiLocalCache (
    private val wikiDao: WikiDao,
    private val ioExecutor: Executor){
    private val TAG = "WikiLocalCache"

    fun insert(wikis: List<Wiki>,
               insertFinished: () -> Unit //callback for insertion finished
    ){
        ioExecutor.execute{
            Log.d(TAG, "inserting ${wikis.size} wikis")
            wikiDao.insert(wikis)
            insertFinished()
        }
    }

    fun wikisByTitle(title: String): DataSource.Factory<Int, Wiki>{
        //preprocess the title string
        val query = "%${title.replace(' ', '%')}%"
        return wikiDao.wikisByTitle(query)
    }


    fun wikisAsIs(title: String): DataSource.Factory<Int, Wiki>{
        //preprocess the title string
        val query = "%${title.replace(' ', '%')}%"
        return wikiDao.wikisAsIs(query)
    }
}