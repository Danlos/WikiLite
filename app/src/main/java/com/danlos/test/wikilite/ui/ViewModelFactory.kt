package com.danlos.test.wikilite.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.danlos.test.wikilite.data.WikiRepository
import java.lang.IllegalArgumentException

class ViewModelFactory(private val repository: WikiRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchWikisViewModel::class.java)){
            return SearchWikisViewModel (repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}