package com.example.applicationanuncios

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import com.google.android.gms.ads.*
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    private lateinit var adView: AdView
    private var initialLayoutComplete = false
    private val adSize: AdSize
        get() {
            val display = windowManager.defaultDisplay
            val outMetrics = DisplayMetrics()
            display.getMetrics(outMetrics)

            val density = outMetrics.density

            var adWidthPixels = ad_view_container.width.toFloat()
            if (adWidthPixels == 0f) {
                adWidthPixels = outMetrics.widthPixels.toFloat()
            }

            val adWidth = (adWidthPixels / density).toInt()
            return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(this, adWidth)
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnProximo.setOnClickListener {
            val intent = Intent(this, MainActivity2::class.java)
            startActivity(intent)
        }

        MobileAds.initialize(this) { }

        MobileAds.setRequestConfiguration(
            RequestConfiguration.Builder()
                .setTestDeviceIds(listOf("ABCDEF012345"))
                .build()
        )

        adView = AdView(this)
        ad_view_container.addView(adView)
        // Since we're loading the banner based on the adContainerView size, we need to wait until this
        // view is laid out before we can get the width.
        ad_view_container.viewTreeObserver.addOnGlobalLayoutListener {
            if (!initialLayoutComplete) {
                initialLayoutComplete = true
                loadBanner()
            }
        }
    }
    /** Called when leaving the activity  */
    public override fun onPause() {
        adView.pause()
        super.onPause()
    }

    /** Called when returning to the activity  */
    public override fun onResume() {
        super.onResume()
        adView.resume()
    }

    /** Called before the activity is destroyed  */
    public override fun onDestroy() {
        adView.destroy()
        super.onDestroy()
    }

    private fun loadBanner() {
        adView.adUnitId = AD_UNIT_ID

        adView.adSize = adSize

        // Create an ad request.
        val adRequest = AdRequest.Builder().build()

        // Start loading the ad in the background.
        adView.loadAd(adRequest)
    }

    companion object {
        // This is an ad unit ID for a test ad. Replace with your own banner ad unit ID.
        private val AD_UNIT_ID = "ca-app-pub-3940256099942544/9214589741"
    }
}