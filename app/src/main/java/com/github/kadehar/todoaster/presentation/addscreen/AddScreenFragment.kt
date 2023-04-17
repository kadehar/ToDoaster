package com.github.kadehar.todoaster.presentation.addscreen

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.github.kadehar.todoaster.R
import com.github.kadehar.todoaster.base.navigation.Screens
import com.github.kadehar.todoaster.base.priority.Priority
import com.github.kadehar.todoaster.databinding.FragmentAddscreenBinding
import com.github.kadehar.todoaster.domain.model.ToDo
import com.github.kadehar.todoaster.presentation.vm.SharedViewModel
import com.github.terrakok.cicerone.Router
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class AddScreenFragment : Fragment(R.layout.fragment_addscreen) {
    companion object {
        fun newInstance() = AddScreenFragment()
    }

    private val binding: FragmentAddscreenBinding by viewBinding(FragmentAddscreenBinding::bind)
    private val viewModel: SharedViewModel by sharedViewModel()
    private val router by inject<Router>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnAdd.setOnClickListener {
            createToDo()
        }
    }

    private fun createToDo() {
        with(binding) {
            val title = etTitle.text.toString()
            val priority = priority.selectedItem.toString()
            val description = etDescription.text.toString()

            val isValid = viewModel.verify(title, description)
            if (isValid) {
                val todo = ToDo(
                    id = 0,
                    title = title,
                    priority = Priority.valueOf(priority),
                    description = description
                )

                viewModel.create(todo)
                Toast.makeText(
                    requireContext(),
                    getString(R.string.success_message),
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
}