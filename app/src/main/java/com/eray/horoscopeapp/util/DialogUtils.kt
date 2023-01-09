package com.eray.horoscopeapp.util

import android.app.Activity
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import com.eray.horoscopeapp.R

object DialogUtils {
    fun showCustomAlert(layoutInflater: LayoutInflater, activity: Activity) {
        val layout: View = layoutInflater.inflate(
            R.layout.layout_error_toast,
            activity.findViewById(R.id.toast_layout_root)
        )

        val toast = Toast(activity) // context should be view's Parent

        toast.setGravity(Gravity.TOP or Gravity.FILL_HORIZONTAL, 0, 0)
        toast.duration = Toast.LENGTH_SHORT
        toast.view = layout
        toast.show()
    }
}