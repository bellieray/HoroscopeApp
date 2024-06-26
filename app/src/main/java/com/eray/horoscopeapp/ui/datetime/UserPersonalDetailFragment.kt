package com.eray.horoscopeapp.ui.datetime

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.eray.horoscopeapp.R
import com.eray.horoscopeapp.data.pref.Prefs
import com.eray.horoscopeapp.databinding.FragmentUserPersonalDetailBinding
import com.eray.horoscopeapp.model.PersonalDetail
import com.eray.horoscopeapp.ui.base.BaseFragment
import com.eray.horoscopeapp.util.*
import com.eray.horoscopeapp.util.Constants.LOGIN_STATE_PREF
import com.eray.horoscopeapp.util.Constants.USER_INFOS
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject


private const val DATE_PATTERN = "dd / MM / yyyy"

@AndroidEntryPoint
class UserPersonalDetailFragment : BaseFragment<FragmentUserPersonalDetailBinding>() {
    private var datePickerDialog: DatePickerDialog? = null
    private val viewModel by viewModels<UserPersonalDetailViewModel>()
    private val navArgs: UserPersonalDetailFragmentArgs by navArgs()

    /*@Inject
    lateinit var prefs: Prefs*/

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initObservers()

        val feelings = resources.getStringArray(R.array.feelings)
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.item_dropdown, feelings)
        binding.etGenderUserBirthday.setAdapter(arrayAdapter)
        binding.etGenderUserBirthday.setTextWatcher { arrayAdapter.filter.filter(null) }
    }

    private fun initObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.viewState.collect { viewState ->
                    viewState.userBirthDate?.let { birthDate ->
                        binding.etBirthdateUserBirthday.text =
                            DateUtils.formatDateToString(birthDate, DATE_PATTERN)
                    }
                }
            }
        }
    }

    private fun initViews() {
        with(binding) {
            navArgs.personalDetail?.let {
                personalDetail = it
            }
            ivArrowBack.setOnClickListener { findNavController().popBackStack() }
            etBirthdateUserBirthday.setOnClickListener {
                showBirthDateSelector()
            }
            btnPersonalDetail.setOnClickListener {
                if (StringUtils.checkInformation(
                        binding.etBirthdateUserBirthday.text.toString(),
                        binding.etNameUserBirthday.text.toString(),
                        binding.etGenderUserBirthday.text.toString(),
                    )
                ) {
                    lifecycleScope.launch {
                        viewModel.setUserInfo(
                            binding.etNameUserBirthday.text.toString(),
                            binding.etGenderUserBirthday.text.toString(),
                            binding.etBirthdateUserBirthday.text.toString(),
                        )
                    }

                    if (navArgs.personalDetail == null) {
                        findNavController().navigateWithPushAnimationAndPop(
                            UserPersonalDetailFragmentDirections.toHomeFragment(),
                            R.id.userPersonalDetailFragment,
                            isInclusive = true,
                        )
                    } else findNavController().popBackStack()
                    DeviceUtils.closeKeyboard(requireActivity(), binding.root)
                } else {
                    DialogUtils.showCustomAlert(
                        requireActivity(),
                        textRes = R.string.please_fill_the_all_blanks
                    )
                }

            }
        }

    }

    @SuppressLint("PrivateResource")
    private fun showBirthDateSelector() {
        if (datePickerDialog != null) datePickerDialog?.dismiss()

        val calendar = Calendar.getInstance()
        viewModel.viewState.value.userBirthDate?.let {
            calendar.time = it
        }
        datePickerDialog = DatePickerDialog(
            requireContext(),
            com.google.android.material.R.style.ThemeOverlay_MaterialComponents_MaterialAlertDialog_Picker_Date_Spinner,
            null,
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )

        datePickerDialog?.datePicker?.maxDate = DateUtils.getMaxDateForBirthdaySpinners().time
        datePickerDialog?.datePicker?.minDate = DateUtils.get100YearAgo().time - 10000

        datePickerDialog?.setButton(
            DialogInterface.BUTTON_POSITIVE,
            getText(com.eray.horoscopeapp.R.string.ok)
        ) { _, _ ->
            datePickerDialog?.datePicker?.let { datePicker ->
                calendar.set(Calendar.YEAR, datePicker.year)
                calendar.set(Calendar.MONTH, datePicker.month)
                calendar.set(Calendar.DAY_OF_MONTH, datePicker.dayOfMonth)
            }
            calendar.set(Calendar.MINUTE, 0)
            calendar.set(Calendar.SECOND, 0)
            calendar.set(Calendar.MILLISECOND, 0)

            viewModel.onBirthDateSelected(calendar.time)
            datePickerDialog = null
        }

        datePickerDialog?.setButton(
            DialogInterface.BUTTON_NEGATIVE,
            resources.getText(com.eray.horoscopeapp.R.string.cancel)
        ) { _, _ ->
            datePickerDialog?.dismiss()
            datePickerDialog = null
        }

        datePickerDialog?.show()
    }


    override fun getFragmentView() = R.layout.fragment_user_personal_detail
}