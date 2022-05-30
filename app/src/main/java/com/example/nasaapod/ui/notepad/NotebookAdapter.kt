package com.example.nasaapod.ui.notepad

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class NotebookAdapter(
    private val textItemClecked: ((item: String) -> Unit)? = null,
    private val imageItemClecked: ((item: ImageItem) -> Unit)? = null,
    ) :
    ListAdapter<AdapterItem, RecyclerView.ViewHolder>(
        object :
            DiffUtil.ItemCallback<AdapterItem>() {
            override fun areItemsTheSame(oldItem: AdapterItem, newItem: AdapterItem): Boolean =
                oldItem.key == newItem.key

            override fun areContentsTheSame(oldItem: AdapterItem, newItem: AdapterItem): Boolean =
                oldItem == newItem
        }){
    companion object {
        private const val TYPE_TEXT = 1
        private const val TYPE_IMG = 2

    }

    override fun getItemViewType(position: Int): Int = when (currentList[position]){
        is TextItem -> TYPE_TEXT
        is  ImageItem -> TYPE_IMG
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }


}

