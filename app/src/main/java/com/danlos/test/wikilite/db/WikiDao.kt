package com.danlos.test.wikilite.db

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.danlos.test.wikilite.model.Wiki

@Dao
interface WikiDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(wikis: List<Wiki>)

    @Query("SELECT * FROM wikis WHERE (title LIKE :queryString) ORDER BY title ASC")
    fun wikisByTitle(queryString: String): DataSource.Factory<Int, Wiki>

    @Query("SELECT * FROM wikis WHERE (title LIKE :queryString) ORDER BY id")
    fun wikisAsIs(queryString: String): DataSource.Factory<Int, Wiki>
}