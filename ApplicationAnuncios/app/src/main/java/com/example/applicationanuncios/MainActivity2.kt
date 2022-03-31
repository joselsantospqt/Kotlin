package com.example.applicationanuncios

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import com.google.android.gms.ads.*
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main2.*



class MainActivity2 : AppCompatActivity() {

    private var anuncioInter: InterstitialAd? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        MobileAds.initialize(this) {}
        requestNewInterstitial()

        btnIntersticiais.setOnClickListener {
            if (anuncioInter != null) {
                anuncioInter?.show(this)
            } else {
                Log.d("INTERSTITIAL", "The interstitial ad wasn't ready yet.")
            }
        }

        btnProximo3.setOnClickListener {
            val intent = Intent(this, MainActivity3::class.java)
            startActivity(intent)
        }

    }

    private fun requestNewInterstitial() {
        val adRequest = AdRequest.Builder().build()
        InterstitialAd.load(this,"ca-app-pub-3940256099942544/1033173712", adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    Log.d("INTERSTITIAL", adError?.message)
                    anuncioInter = null
                }

                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    Log.d("INTERSTITIAL", "Ad was loaded.")
                    anuncioInter = interstitialAd
                    configureFullScreen()
                }
            })
    }

    private fun configureFullScreen(){
        anuncioInter?.fullScreenContentCallback = createFullScreenCalback("INTERSTITIAL") {
            anuncioInter = null
        }
    }

    private fun createFullScreenCalback(TAG: String, funcao: () -> Unit ): FullScreenContentCallback
            = object : FullScreenContentCallback() {
        override fun onAdDismissedFullScreenContent() {
            Log.d(TAG, "Ad was dismissed.")
        }

        override fun onAdFailedToShowFullScreenContent(adError: AdError?) {
            Log.d(TAG, "Ad failed to show.")
        }

        override fun onAdShowedFullScreenContent() {
            Log.d(TAG, "Ad showed fullscreen content.")
            funcao()
        }
    }
}