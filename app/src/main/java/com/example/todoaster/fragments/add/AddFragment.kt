package com.example.todoaster.fragments.add

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import com.example.todoaster.R
import com.example.todoaster.data.models.ToDoData
import com.example.todoaster.data.viewmodel.ToDoViewModel
import com.example.todoaster.databinding.FragmentAddBinding
import com.example.todoaster.fragments.SharedViewModel

class AddFragment : Fragment(R.layout.fragment_add), MenuProvider {

    private var _binding: FragmentAddBinding? = null
    private val binding get() = _binding!!

    private val toDoViewModel: ToDoViewModel by viewModels()
    private val sharedViewModel: SharedViewModel by  viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddBinding.inflate(inflater, container, false)
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)

        binding.priority.onItemSelectedListener = sharedViewModel.listener
        return binding.root
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.add_fragment_menu, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when(menuItem.itemId) {
            R.id.save -> {
                addDataToDb()
                true
            }
            else -> false
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun addDataToDb() {
        with(binding) {
            val title = etTitle.text.toString()
            val priority = priority.selectedItem.toString()
            val description = etDescription.text.toString()

            val isValid = sharedViewModel.verify(title, description)
            if (isValid) {
                val todo = ToDoData(
                    id = 0,
                    title = title,
                    priority = sharedViewModel.toPriority(priority),
                    description = description
                )

                toDoViewModel.addNew(todo)
                Toast.makeText(
                    requireContext(),
                    getString(R.string.success_message),
                    Toast.LENGTH_SHORT
                ).show()
                findNavController().navigate(R.id.action_back_to_list_from_add)
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