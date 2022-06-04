package com.example.nasaapod.ui.notepad.delegates

import com.example.nasaapod.ui.notepad.AdapterItem

interface AdapterDelegates<T: AdapterItem> {
    val layoutId: Int

    fun canHandleType(item: AdapterItem): Boolean

    fun bind(item: T)

    fun created(holder: DelegatesViewHolder)
}