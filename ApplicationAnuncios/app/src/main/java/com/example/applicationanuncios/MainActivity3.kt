package com.example.applicationanuncios

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import com.google.android.gms.ads.*
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import kotlinx.android.synthetic.main.activity_main3.*


class MainActivity3 : AppCompatActivity() {
    private var recompensa: RewardedAd? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)
        MobileAds.initialize(this) {}
        requestReward()

        btnReward.setOnClickListener {
            if (recompensa != null) {
                recompensa?.show(this) {
                    var rewardAmount = it.getAmount()
                    var rewardType = it.getType()
                    Log.d("REWARD", "User earned the reward: $rewardAmount $rewardType")
                }
            } else {
                Log.d("REWARD", "The rewarded ad wasn't ready yet.")
            }
        }
    }

    private fun requestReward() {
        var adRequest = AdRequest.Builder().build()

        RewardedAd.load(this, "ca-app-pub-3940256099942544/5224354917", adRequest,
            object : RewardedAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    Log.d("REWARDED", adError?.message)
                    recompensa = null
                }

                override fun onAdLoaded(rewardedAd: RewardedAd) {
                    Log.d("REWARDED", "Ad was loaded.")
                    recompensa = rewardedAd
                    configureFullScreenRecompensa()
                }
            })
    }

    private fun configureFullScreenRecompensa() {
        recompensa?.fullScreenContentCallback = createFullScreenCalback("REWARDED") {
            recompensa = null
        }
    }


    private fun createFullScreenCalback(
        TAG: String,
        funcao: () -> Unit
    ): FullScreenContentCallback = object : FullScreenContentCallback() {
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