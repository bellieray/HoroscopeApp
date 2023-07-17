package com.eray.horoscopeapp.util

import android.app.Activity
import android.text.Editable
import android.text.TextWatcher
import android.view.Window
import android.view.WindowManager
import android.widget.AutoCompleteTextView
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions
import coil.ImageLoader
import coil.decode.SvgDecoder
import coil.imageLoader
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.appodeal.ads.Appodeal
import com.appodeal.ads.BannerCallbacks
import com.appodeal.ads.InterstitialCallbacks
import com.eray.horoscopeapp.model.Horoscope
import com.eray.horoscopeapp.ui.profile.adapter.UserHoroscopeProperties
import com.eray.horoscopeapp.util.StringUtils.horoscopesWithId
import java.util.*


fun NavController.navigateWithPushAnimation(directions: NavDirections) {
    val navOptions = NavOptions
        .Builder()
        .setEnterAnim(com.eray.horoscopeapp.R.anim.slide_in_right)
        .setExitAnim(com.eray.horoscopeapp.R.anim.slide_out_left)
        .setPopEnterAnim(com.eray.horoscopeapp.R.anim.slide_in_left)
        .setPopExitAnim(com.eray.horoscopeapp.R.anim.slide_out_right)
        .build()
    navigate(directions, navOptions)
}

fun NavController.navigateWithPushAnimationAndPop(
    directions: NavDirections,
    destinationId: Int,
    isInclusive: Boolean = false,
) {
    val navOptions = NavOptions
        .Builder()
        .setEnterAnim(com.eray.horoscopeapp.R.anim.slide_in_right)
        .setExitAnim(com.eray.horoscopeapp.R.anim.slide_out_left)
        .setPopEnterAnim(com.eray.horoscopeapp.R.anim.slide_in_left)
        .setPopExitAnim(com.eray.horoscopeapp.R.anim.slide_out_right)
        .setPopUpTo(destinationId, isInclusive)
        .build()
    navigate(directions, navOptions)
}

fun String.getHoroscopeIdFromDate(): Int {
    return when {
        (this.substring(5, 7).toInt() == 3 && this.substring(0, 2)
            .toInt() >= 21) || (this.substring(5, 7).toInt() == 4 && this.substring(0, 2)
            .toInt() <= 19) -> 7
        (this.substring(5, 7).toInt() == 4 && this.substring(0, 2)
            .toInt() >= 20) || (this.substring(5, 7).toInt() == 5 && this.substring(0, 2)
            .toInt() <= 20) -> 5
        (this.substring(5, 7).toInt() == 5 && this.substring(0, 2)
            .toInt() >= 21) || (this.substring(5, 7).toInt() == 6 && this.substring(0, 2)
            .toInt() <= 20) -> 6
        (this.substring(5, 7).toInt() == 6 && this.substring(0, 2)
            .toInt() >= 21) || (this.substring(5, 7).toInt() == 7 && this.substring(0, 2)
            .toInt() <= 22) -> 12
        (this.substring(5, 7).toInt() == 7 && this.substring(0, 2)
            .toInt() >= 23) || (this.substring(5, 7).toInt() == 8 && this.substring(0, 2)
            .toInt() <= 22) -> 1
        (this.substring(5, 7).toInt() == 8 && this.substring(0, 2)
            .toInt() >= 23) || (this.substring(5, 7).toInt() == 9 && this.substring(0, 2)
            .toInt() <= 22) -> 4
        (this.substring(5, 7).toInt() == 9 && this.substring(0, 2)
            .toInt() >= 23) || (this.substring(5, 7).toInt() == 10 && this.substring(0, 2)
            .toInt() <= 22) -> 10
        (this.substring(5, 7).toInt() == 10 && this.substring(0, 2)
            .toInt() >= 23) || (this.substring(5, 7).toInt() == 11 && this.substring(0, 2)
            .toInt() <= 21) -> 2
        (this.substring(5, 7).toInt() == 11 && this.substring(0, 2)
            .toInt() >= 22) || (this.substring(5, 7).toInt() == 12 && this.substring(0, 2)
            .toInt() <= 21) -> 11
        (this.substring(5, 7).toInt() == 12 && this.substring(0, 2)
            .toInt() >= 22) || (this.substring(5, 7).toInt() == 1 && this.substring(0, 2)
            .toInt() <= 19) -> 9
        (this.substring(5, 7).toInt() == 1 && this.substring(0, 2)
            .toInt() >= 20) || (this.substring(5, 7).toInt() == 2 && this.substring(0, 2)
            .toInt() <= 18) -> 8
        (this.substring(5, 7).toInt() == 2 && this.substring(0, 2)
            .toInt() >= 19) || (this.substring(5, 7).toInt() == 3 && this.substring(0, 2)
            .toInt() <= 20) -> 3
        else -> 0
    }
}

@BindingAdapter("loadImage")
fun ImageView.loadImage(url: String?) {

    val imageLoader = ImageLoader.Builder(this.context)
        .allowHardware(false) // Disable hardware bitmaps if needed
        .diskCachePolicy(CachePolicy.DISABLED) // Disable disk cache
        .memoryCachePolicy(CachePolicy.DISABLED)
        .componentRegistry { add(SvgDecoder(this@loadImage.context)) }
        .build()

    val newLoader = this.context.imageLoader
    newLoader.memoryCache.clear()

    val request = ImageRequest.Builder(this.context)
        .data(url)
        .target(this)
        .build()

    imageLoader.enqueue(request)
}

fun String.checkHoroscopeProperties(): UserHoroscopeProperties {
    return when (this) {
        "Aries" -> UserHoroscopeProperties(
            "Fire", "Red", "Tuesday"
        )
        "Taurus"
        -> UserHoroscopeProperties(
            "Earth", "Green", "Friday"
        )
        "Gemini"
        -> UserHoroscopeProperties(
            "Air", "Yellow", "Wednesday"
        )
        "Cancer"
        -> UserHoroscopeProperties(
            "Water", "White - Silver", "Monday"
        )
        "Leo"
        -> UserHoroscopeProperties(
            "Fire", "Gold", "Sunday"
        )
        "Virgo"
        -> UserHoroscopeProperties(
            "Earth", "Green - Brown", "Wednesday"
        )
        "Libra"
        -> UserHoroscopeProperties(
            "Air", "Pink - Blue", "Friday"
        )
        "Scorpio"
        -> UserHoroscopeProperties(
            "Water", "Black", "Tuesday"
        )
        "Sagittarius"
        -> UserHoroscopeProperties(
            "Fire", "Purple", "Thursday"
        )
        "Capricorn"
        -> UserHoroscopeProperties(
            "Earth", "Brown - Gray", "Saturday"
        )
        "Aquarius"
        -> UserHoroscopeProperties(
            "Air", "Blue", "Saturday"
        )
        "Pisces"
        -> UserHoroscopeProperties(
            "Water", "Light Green", "Thursday"
        )
        else -> throw Exception("")
    }
}

val horoscopeList = mutableListOf(
    Horoscope(7, "Aries", "", ""),
    Horoscope(5, "Taurus", "", ""),
    Horoscope(6, "Gemini", "", ""),
    Horoscope(12, "Cancer", "", ""),
    Horoscope(1, "Leo", "", ""),
    Horoscope(4, "Virgo", "", ""),
    Horoscope(10, "Libra", "", ""),
    Horoscope(2, "Scorpio", "", ""),
    Horoscope(11, "Sagittarius", "", ""),
    Horoscope(9, "Capricorn", "", ""),
    Horoscope(8, "Aquarius", "", ""),
    Horoscope(3, "Pisces", "", "")
)

fun String.checkRisingHoroscope(horoscope: Horoscope): Int? {
    return when {
        this.substring(0, 2) == "05" || this.substring(0, 2) == "06" -> horoscopesWithId[horoscope.name]
        this.substring(0, 2) == "07" || this.substring(0, 2) == "08" -> {
            if (horoscopeList.indexOf(horoscope) == horoscopeList.size - 1)
                horoscopesWithId[horoscopeList[horoscopeList.indexOf(horoscope) + 1].name]
            else horoscopesWithId[horoscopeList[0].name]
        }
        this.substring(0, 2) == "09" || this.substring(0, 2) == "10" -> {
            if (horoscopeList.indexOf(horoscope) == horoscopeList.size - 1)
                horoscopesWithId[horoscopeList[horoscopeList.indexOf(horoscope) + 2].name]
            else horoscopesWithId[horoscopeList[1].name]
        }
        this.substring(0, 2).toInt() in 11..12 -> {
            if (horoscopeList.indexOf(horoscope) == horoscopeList.size - 1)
                horoscopesWithId[horoscopeList[horoscopeList.indexOf(horoscope) + 3].name]
            else horoscopesWithId[horoscopeList[2].name]
        }
        this.substring(0, 2).toInt() in 13..14 -> {
            if (horoscopeList.indexOf(horoscope) == horoscopeList.size - 1)
                horoscopesWithId[horoscopeList[horoscopeList.indexOf(horoscope) + 4].name]
            else horoscopesWithId[horoscopeList[3].name]
        }
        this.substring(0, 2).toInt() in 15..16 -> {
            if (horoscopeList.indexOf(horoscope) == horoscopeList.size - 1)
                horoscopesWithId[horoscopeList[horoscopeList.indexOf(horoscope) + 5].name]
            else horoscopesWithId[horoscopeList[4].name]
        }
        this.substring(0, 2).toInt() in 17..18 -> {
            if (horoscopeList.indexOf(horoscope) == horoscopeList.size - 1)
                horoscopesWithId[horoscopeList[horoscopeList.indexOf(horoscope) + 6].name]
            else horoscopesWithId[horoscopeList[5].name]
        }
        this.substring(0, 2).toInt() in 19..20 -> {
            if (horoscopeList.indexOf(horoscope) == horoscopeList.size - 1)
                horoscopesWithId[horoscopeList[horoscopeList.indexOf(horoscope) + 7].name]
            else horoscopesWithId[horoscopeList[6].name]
        }
        this.substring(0, 2).toInt() in 21..22 -> {
            if (horoscopeList.indexOf(horoscope) == horoscopeList.size - 1)
                horoscopesWithId[horoscopeList[horoscopeList.indexOf(horoscope) + 8].name]
            else horoscopesWithId[horoscopeList[7].name]
        }
        this.substring(0, 2).toInt() == 23 || this.substring(0, 2) == "00" -> {
            if (horoscopeList.indexOf(horoscope) == horoscopeList.size - 1)
                horoscopesWithId[horoscopeList[horoscopeList.indexOf(horoscope) + 9].name]
            else horoscopesWithId[horoscopeList[8].name]
        }
        this.substring(0, 2) == "01" || this.substring(0, 2) == "02" -> {
            if (horoscopeList.indexOf(horoscope) == horoscopeList.size - 1)
                horoscopesWithId[horoscopeList[horoscopeList.indexOf(horoscope) + 10].name]
            else horoscopesWithId[horoscopeList[9].name]
        }
        this.substring(0, 2) == "03" || this.substring(0, 2) == "04" -> {
            if (horoscopeList.indexOf(horoscope) == horoscopeList.size - 1)
                horoscopesWithId[horoscopeList[horoscopeList.indexOf(horoscope) + 11].name]
            else horoscopesWithId[horoscopeList[10].name]
        }
        else -> throw Exception("")
    }
}

fun AutoCompleteTextView.setTextWatcher(doAfterTextChanged: () -> Unit) {
    val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        override fun afterTextChanged(p0: Editable?) {
            doAfterTextChanged.invoke()
        }
    }
    addTextChangedListener(textWatcher)
}

fun Activity.setStatusBarColor(color:Int){
    val window: Window = this.window
    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
    window.statusBarColor = ContextCompat.getColor(this, color)
}

fun Appodeal.interstitialCallbacks(
    onInterstitialLoaded: (() -> Unit)? = null,
    onInterstitialFailedToLoad: (() -> Unit)? = null,
    onInterstitialShown: (() -> Unit)? = null,
    onInterstitialShowFailed: (() -> Unit)? = null,
    onInterstitialClicked: (() -> Unit)? = null,
    onInterstitialClosed: (() -> Unit)? = null,
    onInterstitialExpired: (() -> Unit)? = null
) {
    setInterstitialCallbacks(object : InterstitialCallbacks {
        override fun onInterstitialLoaded(isPrecache: Boolean) {
            onInterstitialLoaded?.invoke()
        }

        override fun onInterstitialFailedToLoad() {
            onInterstitialFailedToLoad?.invoke()
        }

        override fun onInterstitialShown() {
            onInterstitialShown?.invoke()
        }

        override fun onInterstitialShowFailed() {
            onInterstitialShowFailed?.invoke()
        }

        override fun onInterstitialClicked() {
            onInterstitialClicked?.invoke()
        }

        override fun onInterstitialClosed() {
            onInterstitialClosed?.invoke()
        }

        override fun onInterstitialExpired() {
            onInterstitialExpired?.invoke()
        }
    })
}

fun Appodeal.bannerCallback(
    onBannerLoaded: (() -> Unit)? = null,
    onBannerFailedToLoad: (() -> Unit)? = null,
    onBannerShown: (() -> Unit)? = null,
    onBannerShowFailed: (() -> Unit)? = null,
    onBannerClicked: (() -> Unit)? = null,
    onBannerExpired: (() -> Unit)? = null
) {
    setBannerCallbacks(object : BannerCallbacks {
        override fun onBannerLoaded(height: Int, isPrecache: Boolean) {
            onBannerLoaded?.invoke()
        }

        override fun onBannerFailedToLoad() {
            onBannerFailedToLoad?.invoke()
        }

        override fun onBannerShown() {
            onBannerShown?.invoke()
        }

        override fun onBannerShowFailed() {
            onBannerShowFailed?.invoke()
        }

        override fun onBannerClicked() {
            onBannerClicked?.invoke()
        }

        override fun onBannerExpired() {
            onBannerExpired?.invoke()
        }
    })
}