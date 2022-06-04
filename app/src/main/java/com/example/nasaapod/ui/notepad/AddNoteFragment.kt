package com.example.nasaapod.ui.notepad

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.nasaapod.R
import com.example.nasaapod.databinding.FragmentAddNoteBinding


class AddNoteFragment : Fragment(R.layout.fragment_add_note) {

    private lateinit var binding: FragmentAddNoteBinding

    private val notebookViewModel: NotebookViewModel by activityViewModels {
        NotebookViewModel.NotebookViewModelFactory()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var newList = mutableListOf<AdapterItem>()

        notebookViewModel.currentdData.observe(viewLifecycleOwner) { curlist ->
            newList = curlist
        }


        binding.addToolbar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.add_menu_save -> {
                    val text = binding.inputLayout.editText?.text.toString()
                    if (text != null) {
                        newList.add(TextItem(newList.size.toString(), text))
                        notebookViewModel.setData(newList)
                        requireActivity().supportFragmentManager.popBackStack()
                    } else Toast.makeText(
                        requireContext(),
                        getString(R.string.add_note_error),
                        Toast.LENGTH_SHORT
                    ).show()

                    true
                }
                R.id.add_menu_cancel -> {
                    requireActivity().onBackPressed()
                    true
                }
                else -> false
            }

        }


    }

}