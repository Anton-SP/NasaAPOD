package com.example.nasaapod.ui.notepad

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.nasaapod.R
import com.example.nasaapod.databinding.NotepadBinding
import com.example.nasaapod.ui.notepad.TouchHelper.ItemTouchHelperCallBack
import com.google.android.material.snackbar.Snackbar
import java.util.*
import kotlin.collections.ArrayList

class NotepadFragment : Fragment(R.layout.notepad) {

    private lateinit var binding: NotepadBinding

    private lateinit var adapter: NotebookAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = NotepadBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val list: RecyclerView = binding.recycle

        var data: MutableList<AdapterItem> = mutableListOf(
            TextItem("id1", "One",false),
            TextItem("id2", "two",false),
            TextItem("id3", "three",false),
          //  ImageItem("id3", R.drawable.test_image),
            TextItem("id4", "four",false),
            TextItem("id5", "five",false),
           // ImageItem("id6", R.drawable.test_image),
        )



        adapter = NotebookAdapter(
            { textItemUp ->
                val copy = ArrayList(adapter.currentList)
                Collections.swap(copy, textItemUp, textItemUp - 1)

                adapter.submitList(copy)
            },
            { textItemDown ->
                val copy = ArrayList(adapter.currentList)
                Collections.swap(copy, textItemDown, textItemDown + 1)
                adapter.submitList(copy)
            },
            { textItemRemove ->
                val copy = ArrayList(adapter.currentList)
                copy.removeAt(textItemRemove)
                adapter.submitList(copy)
            }
        )

        adapter.submitList(data)



        list.adapter = adapter

        ItemTouchHelper(ItemTouchHelperCallBack({ position ->
            with(adapter) {
                val copy = ArrayList(adapter.currentList)
                copy.removeAt(position)
                submitList(copy)
            //    itemRemoved(position)

                //Snackbar.make(list, position.toString(), Snackbar.LENGTH_SHORT).show()
            }


        }, { from: Int, to: Int ->
            with(adapter) {
                val copy = ArrayList(adapter.currentList)
                Collections.swap(copy, from, to)
                submitList(copy)

             /*   itemsMoved(from, to)
                notifyItemMoved(from, to)
                notifyItemChanged(from)
                notifyItemChanged(to)*/
            }

        })).attachToRecyclerView(list)
    }
}