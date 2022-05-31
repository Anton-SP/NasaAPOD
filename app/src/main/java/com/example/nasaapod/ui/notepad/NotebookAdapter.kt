package com.example.nasaapod.ui.notepad

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.nasaapod.R


class NotebookAdapter(
    private val textItemClecked: ((item: String) -> Unit)? = null,
    private val imageLongClicked: ((item: ImageItem) -> Unit)? = null,
    private val itemRemoved: ((position: Int) -> Unit)? = null
) :
    ListAdapter<AdapterItem, RecyclerView.ViewHolder>(
        object :
            DiffUtil.ItemCallback<AdapterItem>() {
            override fun areItemsTheSame(oldItem: AdapterItem, newItem: AdapterItem): Boolean =
                oldItem.key == newItem.key

            override fun areContentsTheSame(oldItem: AdapterItem, newItem: AdapterItem): Boolean =
                oldItem == newItem
        }) {
    companion object {
        private const val TYPE_TEXT = 1
        private const val TYPE_IMG = 2

    }

    override fun getItemViewType(position: Int): Int = when (currentList[position]) {
        is TextItem -> TYPE_TEXT
        is ImageItem -> TYPE_IMG
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when (viewType) {
            TYPE_TEXT -> TextViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.note_item_text, parent, false)
            )
            TYPE_IMG -> ImageViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.note_item_image, parent, false)
            )
            else -> throw  IllegalStateException("WRONG!!!!")
        }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is TextViewHolder -> {
                val item = currentList[position] as TextItem

                with(holder) {
                    txt.text = item.text
                    up.visibleIf { position != 0 }
                    down.visibleIf { position != currentList.size - 1 }

                }
            }

            is ImageViewHolder -> {
                val item = currentList[position] as ImageItem
                holder.img.setImageResource(item.img)
            }
        }
    }

    /**
     * структура
     * класс холдера типа элемента списка
     * в инит описываем необходимые методы
     * клик,перемещине удаление и тд
     */

    inner class TextViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val txt: TextView = itemView.findViewById(R.id.note_text)

        val up: ImageView = itemView.findViewById(R.id.move_item_up)
        val down: ImageView = itemView.findViewById(R.id.move_item_down)
        val delete: ImageView = itemView.findViewById(R.id.delete_item)

        init {
            itemView.setOnClickListener {
                (currentList[adapterPosition] as? TextItem)?.let {
                    textItemClecked?.invoke(it.text)
                }
            }

            /**
             * add here code for actions!
             */

            itemView.findViewById<ImageView>(R.id.delete_item).setOnClickListener {
                itemRemoved?.invoke(adapterPosition)
            }

            up.setOnClickListener {
                var temp : MutableList<AdapterItem> = currentList
                temp.removeAt(adapterPosition).apply {
                    temp.add(adapterPosition - 1, this)
                }
                onCurrentListChanged(currentList,temp)
              /*   currentList.removeAt(adapterPosition).apply {
                     currentList.add(adapterPosition - 1, this)
                 }*/
                //Collections.swap(currentList, adapterPosition, adapterPosition - 1)
                up.visibleIf { adapterPosition - 1 != 0 }
                notifyItemMoved(adapterPosition, adapterPosition - 1)
                notifyItemChanged(adapterPosition)
            }

            down.setOnClickListener {
               // Collections.swap(currentList,adapterPosition, adapterPosition+1)
                down.visibleIf { adapterPosition + 1 != currentList.size - 1 }
                notifyItemMoved(adapterPosition,adapterPosition+1)
                notifyItemChanged(adapterPosition)
            }
        }
    }

    inner class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val img: ImageView = itemView.findViewById(R.id.note_image)

        init {
            img.setOnLongClickListener {
                (currentList[adapterPosition] as? ImageItem)?.let {
                    imageLongClicked?.invoke(it)
                }
                true
            }
        }

    }


}


