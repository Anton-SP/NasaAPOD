package com.example.nasaapod.ui.notepad

import android.util.Log
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
    private val textItemUp: ((position: Int) -> Unit)? = null,
    private val textItemDown: ((position: Int) -> Unit)? = null,
    private val textItemRemoved: ((position: Int) -> Unit)? = null,
    private val textItemEdit: ((position: Int) -> Unit)? = null
) : ListAdapter<AdapterItem, RecyclerView.ViewHolder>(
    object :
        DiffUtil.ItemCallback<AdapterItem>() {
        override fun areItemsTheSame(oldItem: AdapterItem, newItem: AdapterItem): Boolean =

        when
        {
            oldItem.key == newItem.key ->{
            Log.d("####", "same item true")
            true
        } else -> {
            Log.d("####", "same item false")
            false
        }
        }


        override fun areContentsTheSame(oldItem: AdapterItem, newItem: AdapterItem): Boolean =


            when {
                oldItem is TextItem && newItem is TextItem && oldItem.text == newItem.text -> {
                    Log.d("####", "old item = " + oldItem.text + "new item = " + newItem.text)
                    true
                }
                else -> {
                    Log.d("####", "False")
                    false
                }
            }


    }
) {
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
                    Log.d("HAPPY", "bind, position = " + position);
                    txt.text = item.text

                    /*  up.visibleIf { position != 0 }
                      down.visibleIf { position != currentList.size - 1 }*/

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

        /*   val up: ImageView = itemView.findViewById(R.id.move_item_up)
           val down: ImageView = itemView.findViewById(R.id.move_item_down)*/

        init {
            itemView.setOnClickListener {
                (currentList[adapterPosition] as? TextItem)?.let {

                }
            }

            /**
             * add here code for actions!
             */

            itemView.findViewById<ImageView>(R.id.delete_item).setOnClickListener {
                textItemRemoved?.invoke(adapterPosition)
            }

            itemView.findViewById<ImageView>(R.id.edit_text_item).setOnClickListener {
                textItemEdit?.invoke(adapterPosition)
            }

            /* up.setOnClickListener {
                 textItemUp?.invoke(adapterPosition)
             }

             down.setOnClickListener {
                 val from: TextItem = currentList[adapterPosition] as TextItem
                 val to: TextItem = currentList[adapterPosition+1] as TextItem
                 from.update = true
                 to.update = true
                 textItemDown?.invoke(adapterPosition)
             }*/
        }
    }

    inner class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val img: ImageView = itemView.findViewById(R.id.note_image)

        init {
            img.setOnLongClickListener {
                (currentList[adapterPosition] as? ImageItem)?.let {
                    //imageLongClicked?.invoke(it)
                }
                true
            }
        }

    }

    override fun getItemCount(): Int = currentList.size

    /*   fun itemsMoved(from: Int, to: Int) {
           Collections.swap(currentList, from, to)
       }*/


}


