package com.example.nasaapod.ui.notepad

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.nasaapod.R
import com.example.nasaapod.databinding.NotepadBinding
import com.example.nasaapod.ui.notepad.TouchHelper.ItemTouchHelperCallBack
import com.example.nasaapod.ui.notepad.dalagates.AdapterDelegates
import com.example.nasaapod.ui.notepad.dalagates.ImageAdapterDelegate
import com.example.nasaapod.ui.notepad.dalagates.NotebookAdapterDelegates
import com.example.nasaapod.ui.notepad.dalagates.TextAdapterDelegate
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.flow.collect
import java.util.*
import kotlin.collections.ArrayList

class NotepadFragment : Fragment(R.layout.notepad) {

    private lateinit var binding: NotepadBinding

    private lateinit var adapter: NotebookAdapterDelegates


    private val notebookViewModel: NotebookViewModel by activityViewModels {
        NotebookViewModel.NotebookViewModelFactory()
    }


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

        if (savedInstanceState == null) {
            notebookViewModel.setData()
            Log.d("HAPPY", "new list")
        }

        val list: RecyclerView = binding.recycle


        adapter = NotebookAdapterDelegates(listOf(
            TextAdapterDelegate(),
            ImageAdapterDelegate()
        ) as List<AdapterDelegates<AdapterItem>>)
        /*adapter = NotebookAdapter(
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
            },
            { textItemEdit ->

              //  notebookViewModel.setId(textItemEdit)
                requireActivity().supportFragmentManager
                    .beginTransaction()
                    .add(R.id.main_container, EditTextFragment(textItemEdit), "EDIT")
                    .addToBackStack(null)
                    .commit()


            }
        )*/

        notebookViewModel.currentdData.observe(viewLifecycleOwner) { list ->
            adapter.submitList(list)
        /*if (adapter.currentList != list) {

                Log.d("HAPPY", "update list")
            }*/

        }

        list.adapter = adapter

        ItemTouchHelper(ItemTouchHelperCallBack({ position ->
            with(adapter) {
                val copy = ArrayList(adapter.currentList)
                copy.removeAt(position)
            //    adapter.submitList(copy)
                notebookViewModel.setData(copy)
            }


        }, { from: Int, to: Int ->

                val copy = ArrayList(adapter.currentList)
                Collections.swap(copy, from, to)
             //   adapter.submitList(copy)
               notebookViewModel.setData(copy)


        })).attachToRecyclerView(list)
    }





}