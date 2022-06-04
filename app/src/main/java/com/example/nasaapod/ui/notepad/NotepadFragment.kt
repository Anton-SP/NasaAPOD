package com.example.nasaapod.ui.notepad

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.nasaapod.R
import com.example.nasaapod.databinding.FragmentNotepadBinding
import com.example.nasaapod.ui.notepad.TouchHelper.ItemTouchHelperCallBack
import java.util.*
import kotlin.collections.ArrayList

class NotepadFragment : Fragment(R.layout.fragment_notepad) {

    private lateinit var binding: FragmentNotepadBinding

    private lateinit var adapter: NotebookAdapter

    private val notebookViewModel: NotebookViewModel by activityViewModels {
        NotebookViewModel.NotebookViewModelFactory()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNotepadBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (savedInstanceState == null) {
            notebookViewModel.setData()
            Log.d("HAPPY", "new list")
        }

        val list: RecyclerView = binding.recycle

        adapter = NotebookAdapter(
            { textItemRemove ->
                val copy = ArrayList(adapter.currentList)
                copy.removeAt(textItemRemove)
                adapter.submitList(copy)
            },
            { textItemEdit ->
                requireActivity().supportFragmentManager
                    .beginTransaction()
                    .add(R.id.main_container, EditTextFragment(textItemEdit), "EDIT")
                    .addToBackStack(null)
                    .commit()
            }
        )


        binding.notepadToolbar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.add_text_item -> {
                    requireActivity().supportFragmentManager
                        .beginTransaction()
                        .add(R.id.main_container, AddNoteFragment(), "ADD")
                        .addToBackStack(null)
                        .commit()
                    true
                }
                R.id.add_image_item -> {
                    val copy = ArrayList(adapter.currentList)
                    copy.add(ImageItem(copy.size.toString(), R.drawable.test_image))
                    notebookViewModel.setData(copy)
                    true
                }
                else -> false
            }

        }


        notebookViewModel.currentdData.observe(viewLifecycleOwner) { list ->
            adapter.submitList(list)
        }

        /**
         * устал воевать с areContentsTheSame
         * идея была такая -> во вью модель заносим изменения а фрагмент подписывется на эти изменения
         * и при получении новго листа diff utils вежливо отображает изменения
         * но почему то проверка не работает (либо я её не правльно понял)
         * причем тач листенер работает и при перемещении можем любезно лицезреть
         * логи сравнения элементов
         * а вот при изменении текста что то нет. что весьма озадачило и я убил на это кучу времени
         * дальше тянуть не мог 3 дз не сданы курс закончен а я плетусь в хвосте
         * из этого (еще и адапетр делегейт не дописан факт который весьма меня удручает идея мне очень нравистся
         * поэтому вот такой костыль с notifyItemChanged ->
         * смысл прост в фрагмент с редактированием записи передаем позицию
         * её сохраняем во воью модель и фрагмент с рейсклером смотрит, если значение больше -1
         * то значит этот элемент менялся надо его обновитьб задно сброив значение индекса на -1
         * костыль? - еще какой.
         * но как и любой костыль он работает, но не красиво.
         */
        notebookViewModel.id.observe(viewLifecycleOwner) { position ->
            if (position > -1) {
                adapter.notifyItemChanged(position)
                notebookViewModel.clearId()
            }
        }


        list.adapter = adapter


        ItemTouchHelper(ItemTouchHelperCallBack({ position ->

            val copy = ArrayList(adapter.currentList)
            copy.removeAt(position)
            notebookViewModel.setData(copy)

        }, { from: Int, to: Int ->

            val copy = ArrayList(adapter.currentList)
            Collections.swap(copy, from, to)
            notebookViewModel.setData(copy)


        })).attachToRecyclerView(list)
    }


}