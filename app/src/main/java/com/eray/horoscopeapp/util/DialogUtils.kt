package com.eray.horoscopeapp.util

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.InsetDrawable
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import com.eray.horoscopeapp.R
import com.eray.horoscopeapp.databinding.DialogResultBinding

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

    @SuppressLint("MissingInflatedId")
    fun showResultDialog(
        activity: Activity?,
        title: String?,
        message: String?,
        buttonText: String?,
        buttonClick: (() -> Unit)?
    ) {
        if (activity != null && !activity.isFinishing) {
            activity.runOnUiThread(Runnable {
                val dialogBuilder: AlertDialog.Builder = AlertDialog.Builder(activity)
                val dialogView: View = activity.layoutInflater.inflate(R.layout.dialog_result, null)
                val binding = DialogResultBinding.bind(dialogView)
                dialogBuilder.setView(dialogView)
                with(binding) {
                    if (title.isNullOrEmpty()) tvResultTitle.visibility = View.GONE
                    else tvResultTitle.text = title
                    tvResult.text = message
                    if (buttonText.isNullOrEmpty()) btnShowDetail.visibility = View.GONE
                    else btnShowDetail.text = buttonText
                    val dialog = dialogBuilder.create()
                    dialog.window!!.setBackgroundDrawable(
                        InsetDrawable(
                            ColorDrawable(Color.TRANSPARENT),
                            50
                        )
                    )
                    dialog.show()
                    btnShowDetail.setOnClickListener {
                        dialog.dismiss()
                        buttonClick?.invoke()
                    }
                }
            })
        }
    }
}