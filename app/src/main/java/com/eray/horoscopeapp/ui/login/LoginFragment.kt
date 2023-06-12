package com.eray.horoscopeapp.ui.login

import android.graphics.LinearGradient
import android.graphics.Shader
import android.os.Bundle
import android.text.TextPaint
import android.text.style.CharacterStyle
import android.text.style.UpdateAppearance
import android.view.View
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import androidx.core.text.set
import androidx.core.text.toSpannable
import androidx.navigation.fragment.findNavController
import com.eray.horoscopeapp.R
import com.eray.horoscopeapp.databinding.FragmentLoginBinding
import com.eray.horoscopeapp.ui.base.BaseFragment


class LoginFragment : BaseFragment<FragmentLoginBinding>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        goToHome()
        initViews()
    }

    private fun goToHome() {
        binding.goToHome.setOnClickListener {
            findNavController().navigate(LoginFragmentDirections.toUserPersonalDetailFragment(personalDetail = null))
        }
    }

    private fun initViews() = with(binding) {

        // Find the TextView in the layout

        // Set the text and apply linear gradient using SpannableStringBuilder
        val text = "Hello, this is your personal astrologer AstroScope"
        val otherText = "AstroScope"
        val startColor = ContextCompat.getColor(requireContext(), R.color.start_header)
        val centerColor = ContextCompat.getColor(requireContext(), R.color.center_header)
        val endColor = ContextCompat.getColor(requireContext(), R.color.end_header)
        val spannable = text.toSpannable()
        spannable[text.length - otherText.length..text.length] =
            LinearGradientSpan(text, otherText, startColor, centerColor, endColor)
        title.text = spannable
    }

    override fun getFragmentView(): Int = R.layout.fragment_login

    class LinearGradientSpan(
        private val containingText: String,
        private val textToStyle: String,
        @ColorInt private val startColorInt: Int,
        @ColorInt private val centerColorInt: Int,
        @ColorInt private val endColorInt: Int
    ) : CharacterStyle(), UpdateAppearance {


        override fun updateDrawState(tp: TextPaint?) {
            tp ?: return
            var leadingWidth = 0f
            val indexOfTextToStyle = containingText.indexOf(textToStyle)
            if (!containingText.startsWith(textToStyle) && containingText != textToStyle) {
                leadingWidth = tp.measureText(containingText, 0, indexOfTextToStyle)
            }
            val gradientWidth = tp.measureText(
                containingText,
                indexOfTextToStyle,
                indexOfTextToStyle + textToStyle.length
            )

            tp.shader = LinearGradient(
                leadingWidth,
                0f,
                leadingWidth + gradientWidth,
                0f,
                intArrayOf(
                    startColorInt,
                    centerColorInt,
                    endColorInt
                ),
                null,
                Shader.TileMode.MIRROR
            )
        }
    }
}