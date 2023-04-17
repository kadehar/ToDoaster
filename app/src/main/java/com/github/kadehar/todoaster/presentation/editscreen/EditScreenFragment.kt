package com.github.kadehar.todoaster.presentation.editscreen

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.github.kadehar.todoaster.R
import com.github.kadehar.todoaster.base.navigation.Screens
import com.github.kadehar.todoaster.base.priority.Priority
import com.github.kadehar.todoaster.base.priority.Priority.*
import com.github.kadehar.todoaster.databinding.FragmentEditscreenBinding
import com.github.kadehar.todoaster.domain.model.ToDo
import com.github.kadehar.todoaster.presentation.vm.SharedViewModel
import com.github.terrakok.cicerone.Router
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class EditScreenFragment : Fragment(R.layout.fragment_editscreen) {
    companion object {
        private const val TODO_ITEM_KEY = "todo"
        fun newInstance(toDo: ToDo?) =
            EditScreenFragment().apply {
                arguments = bundleOf(TODO_ITEM_KEY to toDo)
            }
    }

    private val toDoItem: ToDo? by lazy { requireArguments().getParcelable(TODO_ITEM_KEY) }
    private val binding: FragmentEditscreenBinding by viewBinding(FragmentEditscreenBinding::bind)
    private val viewModel: SharedViewModel by sharedViewModel()
    private val router by inject<Router>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            btnAdd.setOnClickListener {
                updateToDo()
            }

            toDoItem?.let {
                etTitle.setText(it.title)
                etDescription.setText(it.description)
                priority.setSelection(position(it.priority))
            }
        }
    }

    private fun updateToDo() {
        with(binding) {
            val title = etTitle.text.toString()
            val priority = priority.selectedItem.toString()
            val description = etDescription.text.toString()

            val isValid = viewModel.verify(title, description)
            if (isValid) {
                val todo = ToDo(
                    id = toDoItem!!.id,
                    title = title,
                    priority = Priority.valueOf(priority),
                    description = description
                )

                viewModel.update(todo)
                Toast.makeText(
                    requireContext(),
                    getString(R.string.success_upd_message),
                    Toast.LENGTH_SHORT
                ).show()
                router.backTo(Screens.mainScreen())
            } else {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.error_msg),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun position(priority: Priority) = when (priority) {
        High -> 0
        Medium -> 1
        Low -> 2
    }
}