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
import com.example.nasaapod.R
import com.example.nasaapod.databinding.FragmentEditTextBinding
import com.example.nasaapod.databinding.MarsFragmentBinding

class EditTextFragment : Fragment(R.layout.fragment_edit_text) {

    private lateinit var binding: FragmentEditTextBinding

    private val notebookViewModel: NotebookViewModel by activityViewModels {
        NotebookViewModel.NotebookViewModelFactory()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditTextBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewLifecycleOwner.lifecycle.coroutineScope.launchWhenStarted {
            notebookViewModel.currentTextBuffer.collect { buffer ->
                if (buffer != -1) {
                    notebookViewModel.currentData.collect() { list ->
                        binding.inputLayout.editText?.setText((list[buffer] as TextItem).text)
                    }
                }
                Log.d("HAPPY", "in edit scope = " + buffer)

            }
        }


        binding.editToolbar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.edit_menu_save -> {
                    var newText = binding.inputLayout.editText?.text.toString()
                    var id = -1
                    var copy :MutableList<AdapterItem> = mutableListOf()
                    viewLifecycleOwner.lifecycle.coroutineScope.launchWhenStarted{
                        notebookViewModel.currentData.collect(){ curList ->
                           copy = curList
                        }
                    }

                    viewLifecycleOwner.lifecycle.coroutineScope.launchWhenStarted {
                        notebookViewModel.currentTextBuffer.collect(){ buffer->
                            if (buffer !=-1) {
                                if (copy.count()>0) {
                                    (copy[buffer] as TextItem).text = newText
                                    notebookViewModel.serData(copy)
                                }
                            }
                        }
                    }

                    //  notebookViewModel.setBuffer(binding.editedText.text.toString())
                    requireActivity().supportFragmentManager.popBackStack()
                    true
                }
                R.id.edit_menu_cancel -> {
                    notebookViewModel.clearBuffer()
                    requireActivity().onBackPressed()
                    true
                }
                else -> false
            }

        }


    }


}