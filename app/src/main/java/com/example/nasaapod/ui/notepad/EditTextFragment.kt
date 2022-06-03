package com.example.nasaapod.ui.notepad

import android.os.Bundle
import android.service.autofill.Validators.and
import android.service.autofill.Validators.or
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
import java.util.*

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

        var id = -1
        var newList = mutableListOf<AdapterItem>()

        notebookViewModel.id.observe(viewLifecycleOwner) { idposition ->
            if (idposition>-1) id = idposition
        }

        notebookViewModel.currentdData.observe(viewLifecycleOwner) { curList ->
            if (id>-1) {
                newList = curList
                binding.inputLayout.editText?.setText((newList[id] as TextItem).text)
            }
        }




        binding.editToolbar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.edit_menu_save -> {
                    if ((id>-1) and (newList.isNotEmpty())) {
                        (newList[id] as TextItem).text = binding.inputLayout.editText?.text.toString()
                        notebookViewModel.setData(newList)
                        notebookViewModel.clearId()


                    }
                    requireActivity().supportFragmentManager.popBackStack()
                    true
                }
                R.id.edit_menu_cancel -> {
                  //  notebookViewModel.clearBuffer()
                    requireActivity().onBackPressed()
                    true
                }
                else -> false
            }

        }


    }


}