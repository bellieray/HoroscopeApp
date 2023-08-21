package com.eray.horoscopeapp.ui.tarot

import android.graphics.Rect
import android.os.Bundle
import android.os.Parcelable
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.appodeal.ads.Appodeal
import com.eray.horoscopeapp.R
import com.eray.horoscopeapp.databinding.FragmentTarotListBinding
import com.eray.horoscopeapp.model.Tarot
import com.eray.horoscopeapp.ui.SessionViewModel
import com.eray.horoscopeapp.ui.base.BaseFragment
import com.eray.horoscopeapp.util.DialogUtils
import com.eray.horoscopeapp.util.interstitialCallbacks
import com.eray.horoscopeapp.util.navigateWithPushAnimation
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlinx.parcelize.Parcelize

@AndroidEntryPoint
class TarotListFragment : BaseFragment<FragmentTarotListBinding>() {
    private val tarotsViewModel: TarotListViewModel by viewModels()
    private val sessionViewModel: SessionViewModel by activityViewModels()
    lateinit var tarotListAdapter: TarotListAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tarotsViewModel.fetchTarots(sessionViewModel.viewState.value.isEnglish == true)
        initViews()
        initObservers()
    }

    private fun initObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                tarotsViewModel.viewState.collect {
                    it.tarots?.let { tarots ->
                        tarotListAdapter.setList(tarots.shuffled())
                    }
                    binding.isLoading = it.isLoading
                }
            }
        }
    }

    private fun initViews() = with(binding) {
        tarotListAdapter = TarotListAdapter()
        rvTarotList.layoutManager = GridLayoutManager(requireContext(), 4)
        rvTarotList.adapter = tarotListAdapter
        rvTarotList.setHasFixedSize(true)
        rvTarotList.addItemDecoration(
            BaseVerticalGridItemDecoration(
                spanCount = 4,
                rowSpacing = resources.getDimensionPixelSize(R.dimen.margin_5),
                columnSpacing = resources.getDimensionPixelSize(R.dimen.margin_10),
                horizontalPadding = resources.getDimensionPixelSize(R.dimen.margin_5),
                verticalPadding = resources.getDimensionPixelSize(R.dimen.margin_10)
            )
        )
        ivArrowBackTarotList.setOnClickListener { findNavController().popBackStack() }
        btnChoose.setOnClickListener {
            showInterstitialAd()
        }
        Appodeal.interstitialCallbacks(
            onInterstitialShown = { chooseClicked() },
            onInterstitialFailedToLoad = { chooseClicked() },
            onInterstitialLoaded = { chooseClicked() })
    }

    private fun chooseClicked(){
        val list = tarotsViewModel.viewState.value.tarots
        val selectedCount = list?.count { it.isSelected } ?: 0
        if (selectedCount < 3) DialogUtils.showCustomAlert(
            requireActivity(),
            errorText = getString(R.string.tarot_item_count_error)
        ) else {
            findNavController().navigateWithPushAnimation(
                TarotListFragmentDirections.actionTarotListFragmentToTarotFragment(
                    tarotListItem = TarotList(list?.filter { it.isSelected })
                )
            )
        }
    }

    override fun getFragmentView() = R.layout.fragment_tarot_list
}

@Parcelize
data class TarotList(val list: List<Tarot>?) : Parcelable

class BaseVerticalGridItemDecoration(
    private val spanCount: Int,
    private val columnSpacing: Int,
    private val rowSpacing: Int,
    private val horizontalPadding: Int,
    private val verticalPadding: Int
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view)
        val column = position % spanCount

        outRect.left = column * columnSpacing / spanCount
        outRect.right = columnSpacing - (column + 1) * columnSpacing / spanCount

        if (position >= spanCount) {
            outRect.top = rowSpacing
        }

        outRect.bottom = rowSpacing

        // Add start and end padding as horizontal padding
        outRect.left += if (column == 0) horizontalPadding else 0
        outRect.right += if (column == spanCount - 1) horizontalPadding else 0

        // Add top and bottom padding as vertical padding
        outRect.top += if (position < spanCount) verticalPadding else 0
        outRect.bottom += verticalPadding
    }
}