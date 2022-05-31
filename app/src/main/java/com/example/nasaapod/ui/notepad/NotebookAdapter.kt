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
import java.util.*


class NotebookAdapter(
    private val textItemClecked: ((item: String) -> Unit)? = null,
    private val imageLongClicked: ((item: ImageItem) -> Unit)? = null,
    private val itemRemoved: ((position: Int) -> Unit)? = null
) : RecyclerView.Adapter<RecyclerView.ViewHolder>()
/* ListAdapter<AdapterItem, RecyclerView.ViewHolder>(
     object :
         DiffUtil.ItemCallback<AdapterItem>() {
         override fun areItemsTheSame(oldItem: AdapterItem, newItem: AdapterItem): Boolean =
             oldItem.key == newItem.key

         override fun areContentsTheSame(oldItem: AdapterItem, newItem: AdapterItem): Boolean =
             oldItem == newItem
     }
 ) */ {


    companion object {
        private const val TYPE_TEXT = 1
        private const val TYPE_IMG = 2

    }

    private val data = mutableListOf<AdapterItem>()

    fun setData(list: Collection<AdapterItem>) {
        data.apply {
            clear()
            addAll(list)
        }
    }

    override fun getItemViewType(position: Int): Int = when (data[position]) {
        is TextItem -> TYPE_TEXT
        is ImageItem -> TYPE_IMG
    }

    fun itemRemoved(position: Int) {
        data.removeAt(position)
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
                val item = data[position] as TextItem

                with(holder) {
                    txt.text = item.text
                    up.visibleIf { position != 0 }
                    down.visibleIf { position != data.size - 1 }

                }
            }

            is ImageViewHolder -> {
                val item = data[position] as ImageItem
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
                (data[adapterPosition] as? TextItem)?.let {
                    // textItemClecked?.invoke(it.text)
                }
            }

            /**
             * add here code for actions!
             */

            itemView.findViewById<ImageView>(R.id.delete_item).setOnClickListener {
                data.removeAt(adapterPosition)

                down.visibleIf { adapterPosition + 1 != data.size - 1 }
                up.visibleIf { adapterPosition - 1 != 0 }
                notifyItemRemoved(adapterPosition)
                notifyItemChanged(adapterPosition)
            }

            up.setOnClickListener {
                Collections.swap(data, adapterPosition, adapterPosition - 1)
                up.visibleIf { adapterPosition - 1 != 0 }
                notifyItemMoved(adapterPosition, adapterPosition - 1)
                notifyItemChanged(adapterPosition)
            }

            down.setOnClickListener {
                Collections.swap(data, adapterPosition, adapterPosition + 1)
                down.visibleIf { adapterPosition + 1 != data.size - 1 }
                notifyItemMoved(adapterPosition, adapterPosition + 1)
                notifyItemChanged(adapterPosition)
            }
        }
    }

    inner class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val img: ImageView = itemView.findViewById(R.id.note_image)

        init {
            img.setOnLongClickListener {
                (data[adapterPosition] as? ImageItem)?.let {
                    //imageLongClicked?.invoke(it)
                }
                true
            }
        }

    }

    override fun getItemCount(): Int = data.size

    fun itemsMoved(from: Int, to: Int) {
        Collections.swap(data,from,to)
    }


}


