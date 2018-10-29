/*
 * Copyright 2018 MadAppGang.
 *
 * Created by Andrii Fedorov afedorov@madappgang.com on 7/20/18.
 */

package chat.testapp.app.ui.pages.splash


import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import chat.testapp.app.databinding.FragmentSplashBinding
import chat.testapp.ui.extentions.getArgument
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein

internal class SplashFragment : androidx.fragment.app.Fragment(), KodeinAware {

    companion object {
        private val IS_SHOW_CONTINUE_KEY = "is_show_continue_key"

        fun newInstance(isShowContinue: Boolean) = SplashFragment().apply {
            arguments = bundleOf(IS_SHOW_CONTINUE_KEY to isShowContinue)
        }

    }

    override val kodein: Kodein by closestKodein()

    private lateinit var binding: FragmentSplashBinding

    private val isShowContinue by lazy { getArgument(IS_SHOW_CONTINUE_KEY, true) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSplashBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.setLifecycleOwner(this)

        binding.viewModel = prepareViewModel()
    }

    private fun prepareViewModel(): SplashViewModel {
        val provider = SplashViewModelProvider(kodein, isShowContinue)
        return ViewModelProviders.of(this, provider).get(SplashViewModel::class.java)
    }

}
