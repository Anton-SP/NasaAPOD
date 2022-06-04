package com.example.nasaapod.ui.notepad.delegates

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.nasaapod.ui.notepad.AdapterItem

class NotebookAdapterDelegates(private val delegates: List<AdapterDelegates<AdapterItem>>) :
    ListAdapter<AdapterItem, DelegatesViewHolder>(object :
        DiffUtil.ItemCallback<AdapterItem>() {

        override fun areItemsTheSame(oldItem: AdapterItem, newItem: AdapterItem): Boolean =
            oldItem.key == newItem.key


        override fun areContentsTheSame(oldItem: AdapterItem, newItem: AdapterItem): Boolean =
            oldItem == newItem

        override fun getChangePayload(oldItem: AdapterItem, newItem: AdapterItem): Any? {
            return super.getChangePayload(oldItem, newItem)
        }

    }) {
    override fun getItemViewType(position: Int): Int {
        val item = currentList[position]

        val viewType = delegates.find {
            it.canHandleType(item)
        }
            ?.layoutId ?: throw  IllegalStateException("Cannot handle this type")

        return viewType
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DelegatesViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(viewType, parent, false)

        val holder = DelegatesViewHolder(itemView)


        delegates.find {
            it.layoutId == viewType
        }?.created(holder,)

        return holder
    }

    override fun onBindViewHolder(holder: DelegatesViewHolder, position: Int) {

        val item: AdapterItem = currentList[position]

        delegates.find {
            it.canHandleType(item)
        }
            ?.bind(item)
    }

}

class DelegatesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)