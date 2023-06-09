package com.eray.horoscopeapp.util

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.InsetDrawable
import android.view.Gravity
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import com.eray.horoscopeapp.R
import com.eray.horoscopeapp.databinding.DialogResultBinding
import com.eray.horoscopeapp.databinding.LayoutErrorToastBinding
import com.eray.horoscopeapp.databinding.ViewMatchingDescriptionDetailBinding

object DialogUtils {
    fun showCustomAlert(activity: Activity, textRes: Int? = null, errorText: String? = null) {
        val layoutInflater = LayoutInflater.from(activity)
        val dialogView = layoutInflater.inflate(R.layout.layout_error_toast, null)
        val view = LayoutErrorToastBinding.bind(dialogView)
        val toast = Toast(activity) // context should be view's Parent

        toast.setGravity(Gravity.TOP or Gravity.FILL_HORIZONTAL, 0, 0)
        toast.duration = Toast.LENGTH_SHORT

        // Create a View.OnTouchListener object and implement the onTouch() method
        toast.view = view.root
        view.tvText.text = textRes?.let { activity.getString(it) } ?: errorText
        toast.show()
    }

    @SuppressLint("MissingInflatedId")
    fun showResultDialog(
        activity: Activity?,
        iconUrl: String?,
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
                    if(iconUrl.isNullOrEmpty()) ivResult.visibility = View.GONE
                    else imageUrl = iconUrl
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

    fun showMatchingDescriptionTextDetailDialog(context: Context, text: String) {
        // Create a layout inflater.
        val inflater = LayoutInflater.from(context)

        // Inflate the custom view.
        val dialogView = inflater.inflate(R.layout.view_matching_description_detail, null)
        val view = ViewMatchingDescriptionDetailBinding.bind(dialogView)

        // Create an AlertDialog builder.
        val builder = AlertDialog.Builder(context)

        // Set the custom view as the dialog's content.
        builder.setView(dialogView)
        view.tvDescriptionDetail.text = text


        // Create the dialog.
        val dialog = builder.create()
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCanceledOnTouchOutside(true)

        // Show the dialog.
        dialog.show()
    }
}