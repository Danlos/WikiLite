package com.danlos.test.wikilite.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.paging.PagedList
import com.danlos.test.wikilite.R
import com.danlos.test.wikilite.api.WikiService
import com.danlos.test.wikilite.data.WikiRepository
import com.danlos.test.wikilite.db.WikiDatabase
import com.danlos.test.wikilite.db.WikiLocalCache
import com.danlos.test.wikilite.model.Wiki
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class SearchWikisActivity : AppCompatActivity() {

    private lateinit var  viewModel: SearchWikisViewModel
    private var adapter = WikisAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Make this with DI
        val database = WikiDatabase.getInstance(this)
        val cache = WikiLocalCache(database.wikisDao(), Executors.newSingleThreadExecutor())
        val repository = WikiRepository(WikiService.create(), cache)
        val viewModelFactory = ViewModelFactory(repository)

        //Create the ViewModel
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(SearchWikisViewModel::class.java)

        initAdapter()
        val query = savedInstanceState?.getString(LAST_SEARCH_QUERY) ?: DEFAULT_QUERY
        viewModel.searchWiki(query)
        initSearch(query)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(LAST_SEARCH_QUERY, viewModel.lastQueryValue())
    }

    private fun initAdapter(){
        wiki_list.adapter = adapter
        viewModel.wikis.observe(this, Observer<PagedList<Wiki>> {
            showEmptyList(it?.size == 0)
            adapter.submitList(it)
        })
        viewModel.networkErrors.observe(this, Observer<String>{
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
        })
    }

    private fun showEmptyList(show: Boolean) {
        if (show) {
            emptyList.visibility = View.VISIBLE
            wiki_list.visibility = View.GONE
        } else {
            emptyList.visibility = View.GONE
            wiki_list.visibility = View.VISIBLE
        }
    }

    private fun initSearch(query: String) {
        search_wiki.setText(query)

        search_wiki.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_GO) {
                updateWikiListFromInput()
                true
            } else {
                false
            }
        }
        search_wiki.setOnKeyListener { _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                updateWikiListFromInput()
                true
            } else {
                false
            }
        }
    }

    private fun updateWikiListFromInput() {
        search_wiki.text.trim().let {
            if (it.isNotEmpty()) {
                wiki_list.scrollToPosition(0)
                viewModel.searchWiki(it.toString())
                adapter.submitList(null)
            }
        }
    }

    companion object {
        private const val LAST_SEARCH_QUERY: String = "last_search_query"
        private const val DEFAULT_QUERY = "Nelson Mandela"
    }
}
