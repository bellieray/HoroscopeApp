package com.eray.horoscopeapp.ui.language

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.eray.horoscopeapp.MainActivity
import com.eray.horoscopeapp.R
import com.eray.horoscopeapp.data.pref.Prefs
import com.eray.horoscopeapp.databinding.FragmentLanguageBinding
import com.eray.horoscopeapp.ui.SessionViewModel
import com.eray.horoscopeapp.ui.base.BaseFragment
import com.eray.horoscopeapp.util.Constants.IS_LANGUAGE_ENGLISH
import com.eray.horoscopeapp.util.DialogUtils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class LanguageFragment : BaseFragment<FragmentLanguageBinding>() {
    @Inject
    lateinit var prefs: Prefs
    private val sessionViewModel by activityViewModels<SessionViewModel>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.clTurkishFlag.setOnClickListener {
            binding.clTurkishFlag.isSelected = true
            binding.clUsaFlag.isSelected = binding.clTurkishFlag.isSelected.not()
        }

        binding.clUsaFlag.setOnClickListener {
            binding.clUsaFlag.isSelected = true
            binding.clTurkishFlag.isSelected = binding.clUsaFlag.isSelected.not()
        }

        binding.btnLanguage.setOnClickListener {
            checkIsItemClicked()
        }

    }

    private fun checkIsItemClicked() = with(binding) {
        if (clTurkishFlag.isSelected.not() && clUsaFlag.isSelected.not()) {
            DialogUtils.showCustomAlert(
                requireActivity(),
                textRes = R.string.please_fill_the_all_blanks
            )
        } else {
            lifecycleScope.launch {
                when {
                    binding.clTurkishFlag.isSelected -> {
                        prefs.setSharedBoolean(
                            IS_LANGUAGE_ENGLISH,
                            false
                        )
                        updateResources("tr")
                        sessionViewModel.setIsLanguageSelected()
                    }

                    else -> {
                        prefs.setSharedBoolean(
                            IS_LANGUAGE_ENGLISH,
                            true
                        )
                        updateResources("en")
                        sessionViewModel.setIsLanguageSelected()
                    }
                }
            }

        }
    }

    private fun updateResources(language: String) {
        val appLocale: LocaleListCompat = LocaleListCompat.forLanguageTags(language)
        AppCompatDelegate.setApplicationLocales(appLocale)
        recreateActivity()
    }

    private fun recreateActivity() {
        val intent = Intent(requireActivity(), MainActivity::class.java)
        requireActivity().finish()
        requireActivity().startActivity(intent)
    }

    override fun getFragmentView() = R.layout.fragment_language
}