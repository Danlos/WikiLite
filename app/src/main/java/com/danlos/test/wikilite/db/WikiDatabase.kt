package com.danlos.test.wikilite.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.danlos.test.wikilite.model.Wiki

@Database(
    entities = [Wiki::class],
    version = 1,
    exportSchema = false
)
abstract class WikiDatabase: RoomDatabase (){
    abstract fun wikisDao(): WikiDao
    companion object {
        @Volatile
        private var INSTANCE: WikiDatabase? = null

        fun getInstance(context: Context): WikiDatabase =
            INSTANCE ?: synchronized(this){
                INSTANCE ?: buildDatabase(context).also{ INSTANCE = it}
            }

        private fun buildDatabase(context: Context) =
                Room.databaseBuilder(context.applicationContext,
                    WikiDatabase::class.java, "Wiki.db")
                    .build()
    }
}