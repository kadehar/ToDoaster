package com.github.kadehar.todoaster.base.navigation

import com.github.kadehar.todoaster.domain.model.ToDo
import com.github.kadehar.todoaster.presentation.addscreen.AddScreenFragment
import com.github.kadehar.todoaster.presentation.editscreen.EditScreenFragment
import com.github.kadehar.todoaster.presentation.mainscreen.MainScreenFragment
import com.github.terrakok.cicerone.androidx.FragmentScreen

object Screens {
    fun mainScreen() = FragmentScreen {
        MainScreenFragment.newInstance()
    }

    fun addScreen() = FragmentScreen {
        AddScreenFragment.newInstance()
    }

    fun editScreen(toDo: ToDo) = FragmentScreen {
        EditScreenFragment.newInstance(toDo)
    }
}