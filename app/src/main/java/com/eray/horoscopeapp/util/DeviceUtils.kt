package com.eray.horoscopeapp.util

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

object DeviceUtils {
    fun closeKeyboard(activity: Activity?, view: View?) {
        activity?.let {
            val inputMethodManager =
                activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            val windowToken = view?.windowToken ?: activity.window.decorView.windowToken
            inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
        }
    }

    fun openKeyboard(activity: Activity?, view: View?, softInputMode: Int?) {
        activity?.let { safeActivity ->
            val imm = safeActivity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
            softInputMode?.let { safeSoftInputMode ->
                safeActivity.window.setSoftInputMode(safeSoftInputMode)
            }
        }
    }
}