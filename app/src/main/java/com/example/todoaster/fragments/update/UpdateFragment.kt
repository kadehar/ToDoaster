package com.example.todoaster.fragments.update

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.todoaster.R
import com.example.todoaster.data.models.ToDoData
import com.example.todoaster.data.viewmodel.ToDoViewModel
import com.example.todoaster.databinding.FragmentUpdateBinding
import com.example.todoaster.fragments.SharedViewModel

class UpdateFragment : Fragment(), MenuProvider {

    private var _binding: FragmentUpdateBinding? = null
    private val binding get() = _binding!!

    private val args by navArgs<UpdateFragmentArgs>()
    private val sharedViewModel: SharedViewModel by viewModels()
    private val toDoViewModel: ToDoViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUpdateBinding.inflate(inflater, container, false)
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)

        with(binding) {
            etTitle.setText(args.currentItem.title)
            priority.setSelection(sharedViewModel.positionOf(args.currentItem.priority))
            priority.onItemSelectedListener = sharedViewModel.listener
            etDescription.setText(args.currentItem.description)
        }
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.update_fragment_menu, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {
            R.id.save -> {
                updateItem()
                findNavController().navigate(R.id.action_back_to_list_from_edit)
                true
            }
            R.id.delete -> {
                deleteItem()
                true
            }
            else -> false
        }
    }

    private fun updateItem() {
        with(binding) {
            val title = etTitle.text.toString()
            val priority = priority.selectedItem.toString()
            val description = etDescription.text.toString()

            val isValid = sharedViewModel.verify(title, description)
            if (isValid) {
                val updatedToDo = ToDoData(
                    id = args.currentItem.id,
                    title = title,
                    priority = sharedViewModel.toPriority(priority),
                    description = description
                )
                toDoViewModel.update(updatedToDo)
                Toast.makeText(
                    requireContext(),
                    getString(R.string.success_upd_message),
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.error_msg),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun deleteItem() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes") { _, _ ->
            toDoViewModel.delete(args.currentItem)
            Toast.makeText(
                requireContext(),
                getString(R.string.success_delete_message, args.currentItem.title),
                Toast.LENGTH_SHORT
            ).show()
            findNavController().navigate(R.id.action_back_to_list_from_edit)
        }
        builder.setNegativeButton("No") { _, _ -> }
        builder.setTitle("Remove \"${args.currentItem.title}\"")
        builder.setMessage("Are you sure you want to remove \"${args.currentItem.title}\"?")
        builder.create().show()
    }
}