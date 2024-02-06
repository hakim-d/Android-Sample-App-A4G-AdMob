package com.hakim_ma.a4g

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import com.google.android.gms.ads.AdListener
import com.hakim_ma.a4g.databinding.ActivityMainBinding
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.OnUserEarnedRewardListener
import com.google.android.gms.ads.admanager.AdManagerAdRequest
import com.google.android.gms.ads.admanager.AdManagerAdView
import com.google.android.gms.ads.admanager.AdManagerInterstitialAd
import com.google.android.gms.ads.admanager.AdManagerInterstitialAdLoadCallback
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    private val sdf = SimpleDateFormat("hh:mm:ss")

    lateinit var mAdManagerAdView : AdManagerAdView
    var mAdManagerInterstitialAd: AdManagerInterstitialAd? = null
    var rewardedAd: RewardedAd? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 👉 Check FirstFragment for Banner Ad
        // 👉 Load an Interstitial Ad
        // 👉 Check FirstFragment for the Show method
        var adRequestInterstitial = AdManagerAdRequest.Builder().build()
        AdManagerInterstitialAd.load(
            this.applicationContext,
            "ca-app-pub-2451195056696095/3209457622",
            adRequestInterstitial,
            object : AdManagerInterstitialAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    mAdManagerInterstitialAd = null
                }
                override fun onAdLoaded(interstitialAd: AdManagerInterstitialAd) {
                    mAdManagerInterstitialAd = interstitialAd
//                    mAdManagerInterstitialAd?.show(this@MainActivity)
                }
            })

//        👉 Load a Reward Ad
        var adRequest = AdManagerAdRequest.Builder().build()
        RewardedAd.load(this,"ca-app-pub-2451195056696095/8045934376", adRequest, object : RewardedAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
                rewardedAd = null
                findViewById<TextView>(R.id.textview_stats).text = "${sdf.format(Date())}   ❌ ${adError?.toString().toString()}\n" + findViewById<TextView>(R.id.textview_stats).text
            }
            override fun onAdLoaded(ad: RewardedAd) {
                rewardedAd = ad
                findViewById<TextView>(R.id.textview_stats).text = "${sdf.format(Date())}   ⭐️ Reward Ad loaded \n" + findViewById<TextView>(R.id.textview_stats).text
            }
        })

        setSupportActionBar(binding.toolbar)
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }

}