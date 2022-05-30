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
   // private val imageItemClecked: ((item: ImageItem) -> Unit)? = null,
    private val imageLongClicked: ((item: ImageItem) -> Unit)? = null,
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when (viewType){
            TYPE_TEXT -> TextViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.note_item_text,parent,false)
            )
            TYPE_IMG -> ImageViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.note_item_image,parent,false)
            )
            else -> throw  IllegalStateException("WRONG!!!!")
        }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
       when (holder) {
           is TextViewHolder -> {
               val item = currentList[position] as TextItem

               with (holder){
                   txt.text = item.text
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

   inner class  TextViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val txt:TextView = itemView.findViewById(R.id.note_text)

        init {
            itemView.setOnClickListener {
                (currentList[adapterPosition] as? TextItem)?.let {
                    textItemClecked?.invoke(it.text)
                }
            }
            /**
             * add here code for actions!
             */

        }
    }

    inner class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        val img : ImageView = itemView.findViewById(R.id.note_image)

        init {
            img.setOnLongClickListener{
                (currentList[adapterPosition] as? ImageItem)?.let {
                    imageLongClicked?.invoke(it)
                }
                true
            }
        }

    }




}


