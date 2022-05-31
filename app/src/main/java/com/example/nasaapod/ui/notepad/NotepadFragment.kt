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
            TextItem("id1", "One"),
            TextItem("id2", "two"),
            ImageItem("id3", R.drawable.test_image),
            TextItem("id4", "four"),
            TextItem("id5", "five"),
            ImageItem("id6", R.drawable.test_image),
        )



        adapter = NotebookAdapter({
            Snackbar.make(list, it, Snackbar.LENGTH_SHORT).show()
        }, {
            Snackbar.make(list, it.toString(), Snackbar.LENGTH_SHORT).show()
        }).apply {
            setData(data)
            notifyDataSetChanged()
        }


        list.adapter = adapter

        ItemTouchHelper(ItemTouchHelperCallBack({ position ->
            with (adapter) {
                itemRemoved(position)
                notifyItemRemoved(position)
                Snackbar.make(list, position.toString(), Snackbar.LENGTH_SHORT).show()
            }



        }, { from: Int, to: Int ->
            with (adapter){
                itemsMoved(from, to)
                notifyItemMoved(from,to)
                notifyItemChanged(from)
                notifyItemChanged(to)
            }

        })).attachToRecyclerView(list)
    }
}