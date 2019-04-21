package com.danlos.test.wikilite.ui

import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

class WikiViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    val viewBinding = DataBindingUtil.bind<ViewDataBinding>(itemView)
}