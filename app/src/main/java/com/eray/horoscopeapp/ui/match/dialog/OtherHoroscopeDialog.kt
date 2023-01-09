package com.eray.horoscopeapp.ui.match.dialog

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.eray.horoscopeapp.R
import com.eray.horoscopeapp.databinding.DialogOtherHoroscopeBinding
import com.eray.horoscopeapp.ui.decorations.BaseVerticalDividerItemDecoration
import com.eray.horoscopeapp.ui.match.adapter.OtherHoroscope
import com.eray.horoscopeapp.ui.match.adapter.OtherHoroscopeDialogAdapter
import com.eray.horoscopeapp.util.DeviceUtils
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

private const val SHEET_RATIO = 0.8F

class OtherHoroscopeDialog(
    private val list: List<OtherHoroscope>,
    val callback: (OtherHoroscope) -> Unit
) : BottomSheetDialogFragment() {
    private var _binding: DialogOtherHoroscopeBinding? = null
    val binding get() = _binding!!
    private val adapter = OtherHoroscopeDialogAdapter(this::getDataModel)
    var otherHoroscope: OtherHoroscope? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.TransparentBottomSheetDialogTheme)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogOtherHoroscopeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.clOtherHoroscope.let { sheetView ->
            SHEET_RATIO.let { ratio ->
                val clParams = sheetView.layoutParams
                clParams.height = (DeviceUtils.getDeviceHeight(requireActivity()) * ratio).toInt()
                sheetView.layoutParams = clParams
            }
        }

        with(binding) {
            rvOtherHoroscope.adapter = adapter
            rvOtherHoroscope.addItemDecoration(
                BaseVerticalDividerItemDecoration(
                    requireContext(),
                    paddingInResId = R.dimen.margin_10,
                    paddingOutResId = R.dimen.margin_20
                )
            )
            adapter.submitList(list)

            btnOtherHoroscope.setOnClickListener {
                dismiss()
                otherHoroscope?.let {
                    callback.invoke(it)
                }
            }
        }

    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        dialog.setOnShowListener {
            ((it as BottomSheetDialog).findViewById<View>(com.google.android.material.R.id.design_bottom_sheet) as FrameLayout).let { bottomSheet ->
                val behavior = BottomSheetBehavior.from(bottomSheet)
                behavior.state = BottomSheetBehavior.STATE_EXPANDED
            }
        }
        return dialog
    }

    private fun getDataModel(otherHoroscope: OtherHoroscope) {
        this.otherHoroscope = otherHoroscope
    }
}