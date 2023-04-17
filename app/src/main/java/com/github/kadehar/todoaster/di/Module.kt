package com.github.kadehar.todoaster.di

import androidx.room.Room
import com.github.kadehar.todoaster.ToDoDatabase
import com.github.kadehar.todoaster.base.Mapper
import com.github.kadehar.todoaster.data.ToDoRepository
import com.github.kadehar.todoaster.data.ToDoRepositoryImpl
import com.github.kadehar.todoaster.data.local.ToDoDAO
import com.github.kadehar.todoaster.domain.ToDoInteractor
import com.github.kadehar.todoaster.domain.ToDoInteractorImpl
import com.github.kadehar.todoaster.presentation.mainscreen.MainScreenViewModel
import com.github.kadehar.todoaster.presentation.vm.SharedViewModel
import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val appModule = module {
    single<ToDoDatabase> {
        Room.databaseBuilder(
            androidContext(),
            ToDoDatabase::class.java,
            ToDoDatabase.DB_NAME
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    single<ToDoDAO> {
        get<ToDoDatabase>().dao()
    }

    single<ToDoRepository> {
        ToDoRepositoryImpl(dao = get())
    }

    single<ToDoInteractor> {
        ToDoInteractorImpl(
            repository = get(),
            mapper = Mapper.Base()
        )
    }

    single<MainScreenViewModel> {
        MainScreenViewModel(
            interactor = get()
        )
    }

    single<SharedViewModel> {
        SharedViewModel(interactor = get())
    }
}

val navModule = module {
    single<Cicerone<Router>> {
        Cicerone.create(Router())
    }

    single<NavigatorHolder> {
        get<Cicerone<Router>>().getNavigatorHolder()
    }

    single<Router> {
        get<Cicerone<Router>>().router
    }
}