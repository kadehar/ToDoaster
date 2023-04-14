package com.example.todoaster.fragments.list

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.todoaster.R
import com.example.todoaster.data.models.ToDoData
import com.example.todoaster.data.viewmodel.ToDoViewModel
import com.example.todoaster.databinding.FragmentListBinding
import com.example.todoaster.fragments.list.adapter.ListAdapter
import com.example.todoaster.utils.hideKeyboard
import com.google.android.material.snackbar.Snackbar
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator

class ListFragment : Fragment(R.layout.fragment_list), MenuProvider, SearchView.OnQueryTextListener {

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!

    private val adapter: ListAdapter by lazy {
        ListAdapter(
            onItemClick = {
                val action = ListFragmentDirections.actionUpdateItem(it)
                findNavController().navigate(action)
            }
        )
    }
    private val viewModel: ToDoViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentListBinding.inflate(inflater, container, false)

        binding.btnAdd.setOnClickListener {
            findNavController().navigate(R.id.action_add_item)
        }

        initRecyclerView()

        viewModel.fetchAllData.observe(viewLifecycleOwner) { data ->
            if (data.isEmpty()) {
                binding.noData.visibility = View.VISIBLE
            }
            adapter.setData(data)
        }


        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)

        hideKeyboard(requireActivity())

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.list_fragment_menu, menu)

        val search = menu.findItem(R.id.search)
        val searchView = search.actionView as? SearchView
        searchView?.isSubmitButtonEnabled = true
        searchView?.setOnQueryTextListener(this)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when(menuItem.itemId) {
            R.id.deleteAll -> {
                deleteAll()
                true
            }
            R.id.search -> {
                true
            }
            R.id.highPriority -> {
                viewModel.sortByHighPriority.observe(this) {
                    adapter.setData(it)
                }
                true
            }
            R.id.lowPriority -> {
                viewModel.sortByLowPriority.observe(this) {
                    adapter.setData(it)
                }
                true
            }
            else -> false
        }
    }

    private fun deleteAll() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes") { _, _ ->
            viewModel.deleteAll()
            Toast.makeText(
                requireContext(),
                getString(R.string.success_delete_all_message),
                Toast.LENGTH_SHORT
            ).show()
        }
        builder.setNegativeButton("No") { _, _ -> }
        builder.setTitle("Clear list")
        builder.setMessage("Are you sure you want to remove all data?")
        builder.create().show()
    }

    private fun initRecyclerView() {
        with(binding) {
            rvContent.adapter = adapter
            rvContent.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            rvContent.itemAnimator = SlideInUpAnimator().apply {
                addDuration = 300
            }
        }

        swipeToDelete()
    }

    private fun swipeToDelete() {
        val callback = object: SwipeToDelete() {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val deletedItem = adapter.dataList[viewHolder.adapterPosition]
                viewModel.delete(deletedItem)
                adapter.notifyItemRemoved(viewHolder.adapterPosition)
                restoreData(viewHolder.itemView, deletedItem)
            }
        }
        val itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(binding.rvContent)
    }

    private fun restoreData(
        view: View,
        item: ToDoData
    ) {
        val snackbar = Snackbar.make(
            view,
            "Deleted \"${item.title}\"",
            Snackbar.LENGTH_LONG
        )
        snackbar.setAction("Undo") {
            viewModel.addNew(item)
        }
        snackbar.show()
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query != null) searchBy(query)
        return true
    }

    override fun onQueryTextChange(query: String?): Boolean {
        if (query != null) searchBy(query)
        return true
    }

    private fun searchBy(query: String) {
        viewModel.search("%$query%").observe(this) { list ->
            list?.let {
                adapter.setData(it)
            }
        }
    }
}