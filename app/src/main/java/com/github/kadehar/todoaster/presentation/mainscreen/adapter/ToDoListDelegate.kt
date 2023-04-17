package com.github.kadehar.todoaster.presentation.mainscreen.adapter

import com.github.kadehar.todoaster.R
import com.github.kadehar.todoaster.base.ext.backgroundColor
import com.github.kadehar.todoaster.base.priority.Priority
import com.github.kadehar.todoaster.databinding.TodoItemBinding
import com.github.kadehar.todoaster.domain.model.ToDo
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding

fun todoListDelegate(onToDoClicked: (ToDo) -> Unit = {}) =
    adapterDelegateViewBinding<ToDo, ToDo, TodoItemBinding>(
        { layoutInflater, root ->
            TodoItemBinding.inflate(layoutInflater, root, false)
        }
    ) {
        bind {
            with(binding) {
                root.setOnClickListener {
                    onToDoClicked(item)
                }

                tvPriority.text = item.priority.name
                when (item.priority) {
                    Priority.High -> {
                        tvPriority.backgroundColor(R.color.red)
                    }
                    Priority.Medium -> {
                        tvPriority.backgroundColor(R.color.yellow)
                    }
                    Priority.Low -> {
                        tvPriority.backgroundColor(R.color.green)
                    }
                }
                tvTitle.text = item.title
                tvDescription.text = item.description
            }
        }
    }