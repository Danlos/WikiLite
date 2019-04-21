package com.danlos.test.wikilite.ui

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.danlos.test.wikilite.BR
import com.danlos.test.wikilite.R
import com.danlos.test.wikilite.model.Wiki

class WikisAdapter: PagedListAdapter<Wiki, WikiViewHolder>(WIKI_COMPARATOR){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WikiViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView = inflater.inflate(R.layout.wiki_item, parent, false)
        return WikiViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: WikiViewHolder, position: Int) {
        val wiki = getItem(position)
        if (wiki != null) {
            holder.viewBinding?.setVariable(BR.Wiki, wiki )
            holder.itemView.setOnClickListener{
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://en.wikipedia.org/wiki/${wiki.title.replace(' ', '_')}"))
                holder.itemView.context.startActivity(browserIntent)
            }
        }
    }

    companion object {
        private val WIKI_COMPARATOR = object: DiffUtil.ItemCallback<Wiki>(){
            override fun areContentsTheSame(oldItem: Wiki, newItem: Wiki): Boolean =
                oldItem.pageId == newItem.pageId

            override fun areItemsTheSame(oldItem: Wiki, newItem: Wiki): Boolean =
                oldItem == newItem
        }
    }
}