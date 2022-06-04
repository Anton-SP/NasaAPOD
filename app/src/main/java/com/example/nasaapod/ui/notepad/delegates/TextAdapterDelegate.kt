package com.example.nasaapod.ui.notepad.delegates

import android.widget.ImageView
import android.widget.TextView
import com.example.nasaapod.R
import com.example.nasaapod.ui.notepad.AdapterItem
import com.example.nasaapod.ui.notepad.TextItem

class TextAdapterDelegate (
    private val textItemRemove: (() -> Unit)? = null,
  //  private val textItemRemove: ((position: Int) -> Unit)? = null,
    private val textItemEdit: ((position: Int) -> Unit)? = null
        ) : AdapterDelegates<TextItem> {

    private lateinit var txt: TextView

    private lateinit var edit: ImageView

    private lateinit var delete: ImageView

    override val layoutId: Int = R.layout.note_item_text

    override fun canHandleType(item: AdapterItem): Boolean = item is TextItem


    override fun bind(item: TextItem) {
            txt.text = item.text
    }

    override fun created(holder: DelegatesViewHolder) {
        val itemView = holder.itemView

        txt = itemView.findViewById(R.id.note_text)

        edit = itemView.findViewById(R.id.edit_text_item)

        delete = itemView.findViewById(R.id.delete_item)

        delete.setOnClickListener {
            textItemRemove?.invoke()
            true
        }
    }
}