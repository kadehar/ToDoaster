package com.github.kadehar.todoaster

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.kadehar.todoaster.base.navigation.BackButtonListener
import com.github.kadehar.todoaster.base.navigation.MainActivityNavigator
import com.github.kadehar.todoaster.base.navigation.Screens
import com.github.terrakok.cicerone.Navigator
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {
    private val navHolder by inject<NavigatorHolder>()
    private val navigator: Navigator = MainActivityNavigator(this, R.id.fragmentContainerView)
    private val router by inject<Router>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navHolder.setNavigator(navigator)
        router.newRootScreen(Screens.mainScreen())
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        navHolder.setNavigator(navigator)
    }

    override fun onPause() {
        super.onPause()
        navHolder.removeNavigator()
    }

    override fun onBackPressed() {
        val fragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView)
        if (fragment != null && fragment is BackButtonListener
            && (fragment as BackButtonListener).onBackPressed()
        ) {
            return
        } else {
            super.onBackPressed()
        }
    }
}