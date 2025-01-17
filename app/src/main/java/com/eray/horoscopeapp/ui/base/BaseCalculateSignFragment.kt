package com.eray.horoscopeapp.ui.base

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.widget.TimePicker
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.appodeal.ads.Appodeal
import com.eray.horoscopeapp.databinding.FragmentCalculateRisingSignBinding
import com.eray.horoscopeapp.ui.SessionViewModel
import com.eray.horoscopeapp.ui.calculatesign.CalculateSignViewModel
import com.eray.horoscopeapp.util.*
import com.google.android.material.R
import kotlinx.coroutines.launch
import java.util.*

abstract class BaseCalculateSignFragment : BaseFragment<FragmentCalculateRisingSignBinding>(),
    TimePickerDialog.OnTimeSetListener {

    protected val calculateSignViewModel by viewModels<CalculateSignViewModel>()
    val sessionViewModel: SessionViewModel by activityViewModels()

    private var datePickerDialog: DatePickerDialog? = null
    private var timePickerDialog: TimePickerDialog? = null
    private val calendar: Calendar = Calendar.getInstance()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observe()
        initViews()
        onPageOpened()
        calculateSignViewModel.fetchHoroscopes(sessionViewModel.viewState.value.isEnglish == true)
    }

    abstract fun onPageOpened()

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
            title = setPageTitle()
            etBirthdateUserBirthday.setOnClickListener {
                showBirthDateSelector()
            }
            etUserBirthTime.setOnClickListener {
                showTimePickerDialog()
            }
            btnCalculate.setOnClickListener {
                if (StringUtils.checkInformation(
                        etBirthdateUserBirthday.text.toString(),
                        etUserBirthTime.text.toString()
                    )
                ) {
                    showInterstitialAd()
                } else {
                    DialogUtils.showCustomAlert(
                        requireActivity(),
                        textRes = com.eray.horoscopeapp.R.string.please_fill_the_all_blanks
                    )
                }
            }
            ivArrowBack.setOnClickListener {
                findNavController().popBackStack()
            }
        }
        Appodeal.interstitialCallbacks(
            onInterstitialFailedToLoad = { doOnCalculateClicked() },
            onInterstitialShown = { doOnCalculateClicked() })
        showMrecAd(com.eray.horoscopeapp.R.id.mrec_view_calculate_sign)
    }

    @SuppressLint("PrivateResource")
    private fun showBirthDateSelector() {
        if (datePickerDialog != null) datePickerDialog?.dismiss()
        calculateSignViewModel.viewState.value.userBirthDate?.let {
            calendar.time = it
        }
        datePickerDialog = DatePickerDialog(
            requireContext(),
            R.style.ThemeOverlay_MaterialComponents_MaterialAlertDialog_Picker_Date_Spinner,
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

    override fun getFragmentView(): Int =
        com.eray.horoscopeapp.R.layout.fragment_calculate_rising_sign

    override fun onTimeSet(p0: TimePicker?, hourOfDay: Int, minute: Int) {
        calendar.apply {
            set(Calendar.HOUR_OF_DAY, hourOfDay)
            set(Calendar.MINUTE, minute)
        }
        calculateSignViewModel.onBirthTimeSelected(calendar.timeInMillis)
    }

    abstract fun doOnCalculateClicked()
    abstract fun setPageTitle(): String
}