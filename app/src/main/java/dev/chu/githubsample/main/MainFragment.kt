package dev.chu.githubsample.main

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dev.chu.chulibrary.arch.BaseFragment
import dev.chu.chulibrary.util.extensions.getDrawableById
import dev.chu.chulibrary.util.extensions.toast
import dev.chu.githubsample.R
import dev.chu.githubsample.common.HeaderViewModel
import dev.chu.githubsample.databinding.FragmentMainBinding

class MainFragment : BaseFragment<FragmentMainBinding, MainViewModel>(R.layout.fragment_main) {

    private val headerViewModel: HeaderViewModel by viewModels()

    override fun connEssentialViews(): EssentialView {
        return buildEssentialView {
            initBinding {
                it.viewModel = viewModel
                it.headerViewModel = headerViewModel
                headerViewModel.setTitle("MainFragment")
                headerViewModel.setLeftImage(requireContext().getDrawableById(R.drawable.account_circle_black))
            }
        }
    }

    override fun observeViewModel() {
        super.observeViewModel()

        headerViewModel.clickHeaderLeftImage.observe(viewLifecycleOwner) {
            toast("클릭")
        }

        viewModel.navigateToPopular.observe(viewLifecycleOwner) {
            val action = MainFragmentDirections.actionMainToPopular()
            findNavController().navigate(action)
        }
    }
}