package com.example.nasaapod.ui.notepad.delegates

import android.widget.ImageView
import com.example.nasaapod.R
import com.example.nasaapod.ui.notepad.AdapterItem
import com.example.nasaapod.ui.notepad.ImageItem

class ImageAdapterDelegate: AdapterDelegates<ImageItem> {

    private lateinit var  img:ImageView

    override val layoutId: Int = R.layout.note_item_image


    override fun canHandleType(item: AdapterItem): Boolean = item is ImageItem

    override fun bind(item: ImageItem) {
      img.setImageResource(item.img)
    }

    override fun created(holder: DelegatesViewHolder) {
       img = holder.itemView.findViewById(R.id.note_image)

    }
}
