package com.eray.horoscopeapp.util

import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions
import com.eray.horoscopeapp.R

fun NavController.navigateWithPushAnimation(directions: NavDirections) {
    val navOptions = NavOptions
        .Builder()
        .setEnterAnim(R.anim.slide_in_right)
        .setExitAnim(R.anim.slide_out_left)
        .setPopEnterAnim(R.anim.slide_in_left)
        .setPopExitAnim(R.anim.slide_out_right)
        .build()
    navigate(directions, navOptions)
}