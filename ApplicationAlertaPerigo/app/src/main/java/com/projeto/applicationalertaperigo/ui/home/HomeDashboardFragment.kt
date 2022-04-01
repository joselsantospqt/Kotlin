package com.projeto.applicationalertaperigo.ui.home

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.google.android.gms.ads.*
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import com.projeto.applicationalertaperigo.R
import com.projeto.applicationalertaperigo.databinding.FragmentHomeDashboardBinding
import com.projeto.applicationalertaperigo.viewModel.HomeViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.projeto.applicationalertaperigo.viewModel.DenunciaViewModel


class HomeDashboardFragment : Fragment() {

    private var recompensa: RewardedAd? = null
    private var _binding: FragmentHomeDashboardBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by activityViewModels()
    private val DenunciaViewModel: DenunciaViewModel by activityViewModels()
    private lateinit var auth: FirebaseAuth

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth

        MobileAds.initialize(context) {}
        requestReward()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeDashboardBinding.inflate(inflater, container, false)
        val view = binding.root
        setup(view)
        return view
    }

    private fun setup(view: View) {
        lerViewModel()
        setupObservers(view)
        setupButton(view)
    }

    private fun lerViewModel() {
        viewModel.CarregaDadosUsuario(auth.currentUser?.uid.toString())
    }


    private fun setupButton(view: View) {
        binding.btnPerfil.setOnClickListener {
            Navigation.findNavController(view)
                .navigate(R.id.action_homeDashboardFragment_to_homePerfilFragment)
        }
        binding.btnDenunciar.setOnClickListener {
            Navigation.findNavController(view)
                .navigate(R.id.action_homeDashboardFragment_to_denunciaCadastroFragment)
        }
        binding.btnListDenuncia.setOnClickListener {
            Navigation.findNavController(view)
                .navigate(R.id.action_homeDashboardFragment_to_denunciaListarFragment)
        }

        binding.btnListReward.setOnClickListener {
            if (recompensa != null) {
                recompensa?.show(requireActivity()) {
                    var rewardAmount = it.getAmount()
                    var rewardType = it.getType()
                    Log.d("REWARD", "User earned the reward: $rewardAmount $rewardType")
                }
            } else {
                Log.d("REWARD", "The rewarded ad wasn't ready yet.")
            }
        }
    }

    private fun setupObservers(view: View) {
        viewModel.dadosPessoa.observe(viewLifecycleOwner, {
            if (it != null) {
                binding.txtNomeUsuario.setText(it.nome.toString() + " " + it.sobrenome.toString())
            }
        })

        viewModel.trocaFragment.observe(viewLifecycleOwner, {
            if (it != null) {
                when (it) {
                    2 -> {
                        viewModel.NavegaFragment(0)
                        Navigation.findNavController(view)
                            .navigate(R.id.action_homeDashboardFragment_to_homePerfilFragment)
                    }
                    3 -> {
                        viewModel.NavegaFragment(0)
                        Navigation.findNavController(view)
                            .navigate(R.id.action_homeDashboardFragment_to_denunciaCadastroFragment)
                    }
                    4 -> {
                        viewModel.NavegaFragment(0)
                        Navigation.findNavController(view)
                            .navigate(R.id.action_homeDashboardFragment_to_denunciaListarFragment)
                    }
                }
            }
        })

    }


    private fun requestReward() {
        var adRequest = AdRequest.Builder().build()

        RewardedAd.load(requireActivity(), "ca-app-pub-3940256099942544/5224354917", adRequest,
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