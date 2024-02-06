package com.hakim_ma.a4g

import android.os.Bundle
import android.text.Html
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.OnUserEarnedRewardListener
import com.google.android.gms.ads.admanager.AdManagerAdRequest
import com.hakim_ma.a4g.databinding.FragmentFirstBinding
import java.text.SimpleDateFormat
import java.util.Date

class FirstFragment : Fragment() {
    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!
    private val sdf = SimpleDateFormat("hh:mm:ss")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mainActivity = requireActivity() as MainActivity

        // 👉 Show a Banner Ad
        mainActivity.mAdManagerAdView = binding.adView
        val adRequest = AdManagerAdRequest.Builder().build()
        mainActivity.mAdManagerAdView.loadAd(adRequest)

        // 👉 Banner Ad Events
        mainActivity.mAdManagerAdView.adListener = object: AdListener() {
            override fun onAdClicked() {
                binding.root.findViewById<TextView>(R.id.textview_stats).text = ("${sdf.format(Date())}   👆 Banner Ad Clicked\n") + binding.root.findViewById<TextView>(R.id.textview_stats).text
            }
            override fun onAdClosed() {
                binding.root.findViewById<TextView>(R.id.textview_stats).text = ("${sdf.format(Date())}   ✋ Back from clicking the Banner Ad\n") + binding.root.findViewById<TextView>(R.id.textview_stats).text
            }
            override fun onAdFailedToLoad(adError : LoadAdError) {
                binding.root.findViewById<TextView>(R.id.textview_stats).text = ("${sdf.format(Date())}   ❌\nBanner Ad error:\n${adError.responseInfo}\n") + binding.root.findViewById<TextView>(R.id.textview_stats).text
            }
            override fun onAdImpression() {
                binding.root.findViewById<TextView>(R.id.textview_stats).text = ("${sdf.format(Date())}   ✅ Banner Ad Impression\n") + binding.root.findViewById<TextView>(R.id.textview_stats).text
            }
            override fun onAdLoaded() {
                binding.root.findViewById<TextView>(R.id.textview_stats).text = ("${sdf.format(Date())}   ✅ Banner Ad Loaded\n") + binding.root.findViewById<TextView>(R.id.textview_stats).text
            }
            override fun onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
                binding.root.findViewById<TextView>(R.id.textview_stats).text = ("${sdf.format(Date())}   ✅ Banner Ad Opened\n") + binding.root.findViewById<TextView>(R.id.textview_stats).text
            }
        }

        binding.buttonFirst.setOnClickListener {
            // 👉 Show Interstitial Ad
            if (mainActivity.mAdManagerInterstitialAd != null) {
                mainActivity.mAdManagerInterstitialAd?.show(this.requireActivity())
                binding.root.findViewById<TextView>(R.id.textview_stats).text = ("${sdf.format(Date())}   🌠 Show Interstitial Ad\n")  + binding.root.findViewById<TextView>(R.id.textview_stats).text
            } else {
                binding.root.findViewById<TextView>(R.id.textview_stats).text = ("${sdf.format(Date())}   ❌ The interstitial Ad wasn't ready yet\n")  + binding.root.findViewById<TextView>(R.id.textview_stats).text
            }

//            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }

        binding.buttonReward.setOnClickListener {
            // 👉 Show Reward Ad
            mainActivity.rewardedAd?.let { ad ->
                ad.show(this.requireActivity(), OnUserEarnedRewardListener { rewardItem ->
                    // Handle the reward.
                    val rewardAmount = rewardItem.amount
                    val rewardType = rewardItem.type
                    binding.root.findViewById<TextView>(R.id.textview_stats).text = ("${sdf.format(Date())}   \uD83E\uDD11 The user earned the reward\n") + binding.root.findViewById<TextView>(R.id.textview_stats).text
                    binding.root.findViewById<TextView>(R.id.textview_stats).text = ("${sdf.format(Date())}   \uD83E\uDD11 Reward Amount: ${rewardAmount}\n") + binding.root.findViewById<TextView>(R.id.textview_stats).text
                    binding.root.findViewById<TextView>(R.id.textview_stats).text = ("${sdf.format(Date())}   \uD83E\uDD11 Reward Type: ${rewardType}\n") + binding.root.findViewById<TextView>(R.id.textview_stats).text
                })
            } ?: run {
                binding.root.findViewById<TextView>(R.id.textview_stats).text = ("${sdf.format(Date())}   ❌ The Rewarded Ad wasn't ready yet\n") + binding.root.findViewById<TextView>(R.id.textview_stats).text
            }
//            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}