package com.eray.horoscopeapp.ui.risingsign

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.app.TimePickerDialog.OnTimeSetListener
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.widget.TimePicker
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.eray.horoscopeapp.R
import com.eray.horoscopeapp.databinding.FragmentCalculateRisingSignBinding
import com.eray.horoscopeapp.ui.base.BaseFragment
import com.eray.horoscopeapp.util.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.*

@AndroidEntryPoint
class CalculateRisingSignFragment : BaseFragment<FragmentCalculateRisingSignBinding>(), OnTimeSetListener {

    private val calculateSignViewModel by viewModels<CalculateSignViewModel>()

    private var datePickerDialog: DatePickerDialog? = null
    private var timePickerDialog: TimePickerDialog? = null
    private val calendar: Calendar = Calendar.getInstance()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observe()
        initViews()
    }

    private fun observe() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                calculateSignViewModel.viewState.collect { viewState ->
                    with(binding) {
                        viewState.userBirthDate?.let { birthDate ->
                            etBirthdateUserBirthday.text =
                                DateUtils.formatDateToString(birthDate, DATE_PATTERN)
                            DateUtils.formatDateToString(birthDate, DATE_PATTERN)
                                ?.let { calculateSignViewModel.onBirthDateStringSelected(it) }
                        }
                        viewState.userBirthTime?.let { birthTime ->
                            etUserBirthTime.text =
                                DateUtils.formatLongToString(birthTime, TIME_PATTERN)
                        }
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
            etUserBirthTime.setOnClickListener {
                showTimePickerDialog()
            }
            btnCalculate.setOnClickListener {
                val birthTime = etUserBirthTime.text.toString()
                calculateSignViewModel.convertDateToHoroscope()
                horoscopeList.find {
                    it.id?.toInt() == calculateSignViewModel.viewState.value.userHoroscopeId
                    //it.name == etBirthdateUserBirthday.text.toString().checkHoroscope().first
                }
                    ?.let { horoscope ->
                        DialogUtils.showResultDialog(
                            requireActivity(),
                            null,
                            birthTime.checkRisingHoroscope(horoscope).name,
                            "See Detail"
                        ) { findNavController().popBackStack() }
                    }
            }
        }
    }

    @SuppressLint("PrivateResource")
    private fun showBirthDateSelector() {
        if (datePickerDialog != null) datePickerDialog?.dismiss()
        calculateSignViewModel.viewState.value.userBirthDate?.let {
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

            calculateSignViewModel.onBirthDateSelected(calendar.time)
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

    @SuppressLint("PrivateResource")
    private fun showTimePickerDialog() {
        if (timePickerDialog != null) timePickerDialog?.dismiss()
        calculateSignViewModel.viewState.value.userBirthTime?.let {
            calendar.timeInMillis = it
        }
        timePickerDialog = TimePickerDialog(
            requireContext(),
            AlertDialog.THEME_HOLO_LIGHT,
            this,
            calendar.get(Calendar.HOUR_OF_DAY),
            calendar.get(Calendar.MINUTE),
            true
        )
        timePickerDialog?.show()
    }

    override fun getFragmentView(): Int = R.layout.fragment_calculate_rising_sign
    override fun onTimeSet(p0: TimePicker?, hourOfDay: Int, minute: Int) {
        calendar.apply {
            set(Calendar.HOUR_OF_DAY, hourOfDay)
            set(Calendar.MINUTE, minute)
        }
        calculateSignViewModel.onBirthTimeSelected(calendar.timeInMillis)
    }
}