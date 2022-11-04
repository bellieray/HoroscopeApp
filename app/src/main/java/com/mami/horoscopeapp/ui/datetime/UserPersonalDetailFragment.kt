package com.mami.horoscopeapp.ui.datetime

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.mami.horoscopeapp.R
import com.mami.horoscopeapp.databinding.FragmentUserPersonalDetailBinding
import com.mami.horoscopeapp.ui.base.BaseFragment
import com.mami.horoscopeapp.util.DateUtils
import com.mami.horoscopeapp.util.DeviceUtils
import com.mami.horoscopeapp.util.StringUtils
import kotlinx.coroutines.launch
import java.util.*


private const val DATE_PATTERN = "dd / MM / yyyy"

class DateTimeFragment : BaseFragment<FragmentUserPersonalDetailBinding>() {
    private var datePickerDialog: DatePickerDialog? = null
    private val viewModel by viewModels<UserPersonalDetailViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initObservers()
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
            etBirthdateUserBirthday.setOnClickListener {
                showBirthDateSelector()
            }
            btnPersonalDetail.setOnClickListener {
                if (StringUtils.checkInformation(
                        binding.etBirthdateUserBirthday.text.toString(),
                        binding.etBirthTimeUserBirthday.text.toString(),
                        binding.etEmailUserBirthday.text.toString(),
                        binding.etNameUserBirthday.text.toString(),
                        binding.etGenderUserBirthday.text.toString(),
                        binding.etPlaceOfBirthUserBirthday.text.toString(),
                    )
                ) {
                    findNavController().navigate(DateTimeFragmentDirections.actionDateTimeFragmentToLoginFragment())
                    DeviceUtils.closeKeyboard(requireActivity(), binding.root)
                } else {
                    showCustomAlert()
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
            getText(com.mami.horoscopeapp.R.string.ok)
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
            resources.getText(com.mami.horoscopeapp.R.string.cancel)
        ) { _, _ ->
            datePickerDialog?.dismiss()
            datePickerDialog = null
        }

        datePickerDialog?.show()
    }

    private fun showCustomAlert() {
        val inflater = layoutInflater
        val layout: View = inflater.inflate(
            R.layout.layout_error_toast,
            requireActivity().findViewById(R.id.toast_layout_root)
        )

        val toast = Toast(context) // context should be view's Parent

        toast.setGravity(Gravity.TOP or Gravity.FILL_HORIZONTAL, 0, 0)
        toast.duration = Toast.LENGTH_SHORT
        toast.view = layout
        toast.show()
    }

    override fun getFragmentView() = R.layout.fragment_user_personal_detail
}