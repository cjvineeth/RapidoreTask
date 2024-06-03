package com.app.rapidore.common

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.NavGraph
import androidx.navigation.NavOptions
import androidx.navigation.Navigator


object NavigationExtensions {

    private fun NavController.safetyCheck(@IdRes resId: Int, safe: () -> Unit) {
        val currentDestination = currentDestination
        if (currentDestination != null) {
            val navAction = currentDestination.getAction(resId)
            if (navAction != null) {
                val destinationId = orEmpty(navAction.destinationId)
                val currentNode: NavGraph? = if (currentDestination is NavGraph) currentDestination else currentDestination.parent
                if (destinationId != 0 && currentNode != null && currentNode.findNode(destinationId) != null) {
                    safe.invoke()
                }
            }
        }
    }

    /**
     * This function will check navigation safety before starting navigation using direction
     *
     * @param navController NavController instance
     * @param direction     navigation operation
     */
    fun NavController.navigateSafe(direction: NavDirections, navOptions: NavOptions? = null) {
        safetyCheck(direction.actionId) {
            navigate(direction, navOptions)
        }
    }

    fun NavController.navigateSafe(direction: NavDirections, extras: Navigator.Extras) {
        safetyCheck(direction.actionId) {
            navigate(direction, extras)
        }
    }

    /**
     * This function will check navigation safety before starting navigation using resId and args bundle
     *
     * @param navController NavController instance
     * @param resId         destination resource id
     * @param args          bundle args
     */
    fun NavController.navigateSafe(@IdRes resId: Int, args: Bundle? = null) {
        safetyCheck(resId) {
            navigate(resId, args)
        }
    }

    private fun orEmpty(value: Int?): Int {
        return value ?: 0
    }
}