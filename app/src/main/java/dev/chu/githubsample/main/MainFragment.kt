package dev.chu.githubsample.main

import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import dev.chu.chulibrary.arch.event.EventObserver
import dev.chu.chulibrary.arch.list.BaseAdapter
import dev.chu.chulibrary.arch.ui.BaseFragment
import dev.chu.chulibrary.arch.viewmodel.BaseDefaultHeaderViewModel
import dev.chu.chulibrary.util.extensions.changeValue
import dev.chu.chulibrary.util.extensions.getDrawableById
import dev.chu.chulibrary.util.extensions.toast
import dev.chu.githubsample.R
import dev.chu.githubsample.databinding.FragmentMainBinding

class MainFragment: BaseFragment<FragmentMainBinding, MainViewModel>(
    layoutId = R.layout.fragment_main
) {

    private val headerViewModel: BaseDefaultHeaderViewModel by viewModels()

    override fun connEssentialViews(): EssentialView {
        return buildEssentialViews {
            initBinding {
                it.viewModel = viewModel
                it.headerViewModel = headerViewModel.apply {
                    setTitle("Base-Framework Practice")
                    image.changeValue(requireContext().getDrawableById(R.drawable.ic_account_circle))
                }
            }
        }
    }

    override fun createAdapter(): BaseAdapter {
        return BaseAdapter()
    }

    override fun observeViewModel() {
        super.observeViewModel()
        headerViewModel.clickEvent.observe(viewLifecycleOwner, EventObserver(this) { txt, _ ->
            toast(txt)
        })

        viewModel.navigateToLargeImage.observe(viewLifecycleOwner, EventObserver(this) { _, _ ->
            openLargeImage()
        })

        viewModel.navigateToGithubPractice.observe(viewLifecycleOwner, EventObserver(this) { _, _ ->
            openGithub()
        })
    }

    private fun openLargeImage() {

    }

    private fun openGithub() {

    }
}