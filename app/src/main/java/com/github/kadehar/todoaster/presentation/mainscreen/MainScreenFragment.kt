package com.github.kadehar.todoaster.presentation.mainscreen

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.github.kadehar.todoaster.R
import com.github.kadehar.todoaster.base.SwipeToDelete
import com.github.kadehar.todoaster.base.ext.setData
import com.github.kadehar.todoaster.base.hideKeyboard
import com.github.kadehar.todoaster.base.navigation.Screens
import com.github.kadehar.todoaster.databinding.FragmentMainscreenBinding
import com.github.kadehar.todoaster.domain.model.ToDo
import com.github.kadehar.todoaster.presentation.mainscreen.adapter.todoListDelegate
import com.github.terrakok.cicerone.Router
import com.google.android.material.snackbar.Snackbar
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainScreenFragment :
    Fragment(R.layout.fragment_mainscreen),
    MenuProvider,
    SearchView.OnQueryTextListener {

    companion object {
        fun newInstance() = MainScreenFragment()
    }

    private val binding: FragmentMainscreenBinding by viewBinding(FragmentMainscreenBinding::bind)
    private val viewModel: MainScreenViewModel by viewModel()
    private val router by inject<Router>()
    private val adapter = ListDelegationAdapter(
        todoListDelegate(
            onToDoClicked = {
                router.navigateTo(Screens.editScreen(it))
            }
        )
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        viewModel.getAll().observe(viewLifecycleOwner, ::render)
        with(binding) {
            btnAdd.setOnClickListener {
                router.navigateTo(Screens.addScreen())
            }
        }

        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)

        hideKeyboard(requireActivity())
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.list_fragment_menu, menu)

        val search = menu.findItem(R.id.search)
        val searchView = search.actionView as? SearchView
        searchView?.isSubmitButtonEnabled = true
        searchView?.setOnQueryTextListener(this)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {
            R.id.deleteAll -> {
                deleteAll()
                true
            }
            R.id.highPriority -> {
                viewModel.sortByHighPriority().observe(this, ::render)
                true
            }
            R.id.lowPriority -> {
                viewModel.sortByLowPriority().observe(this, ::render)
                true
            }
            else -> false
        }
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query != null) searchBy(query)
        return true
    }

    override fun onQueryTextChange(query: String?): Boolean {
        if (query != null) searchBy(query)
        return true
    }

    private fun initAdapter() {
        with(binding) {
            rvContent.adapter = adapter
            rvContent.layoutManager = StaggeredGridLayoutManager(
                2,
                StaggeredGridLayoutManager.VERTICAL
            )
            rvContent.itemAnimator = SlideInUpAnimator().apply {
                addDuration = 300
            }
        }
        swipeToDelete()
    }

    private fun render(data: List<ToDo>) {
        adapter.setData(data)
    }

    private fun swipeToDelete() {
        val callback = object : SwipeToDelete() {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val deletedItem = adapter.items[viewHolder.absoluteAdapterPosition]
                viewModel.delete(deletedItem)
                adapter.notifyItemRemoved(viewHolder.absoluteAdapterPosition)
                restoreData(viewHolder.itemView, deletedItem)
            }
        }
        val itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(binding.rvContent)
    }

    private fun restoreData(
        view: View,
        item: ToDo
    ) {
        val snackBar = Snackbar.make(
            view,
            "Deleted \"${item.title}\"",
            Snackbar.LENGTH_LONG
        )
        snackBar.setAction("Undo") {
            viewModel.create(item)
        }
        snackBar.show()
    }

    private fun searchBy(query: String) {
        viewModel.search("%$query%").observe(this) { list ->
            list?.let {
                adapter.setData(it)
            }
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
}