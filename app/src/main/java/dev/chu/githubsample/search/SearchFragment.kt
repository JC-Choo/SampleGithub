package dev.chu.githubsample.search

import dev.chu.chulibrary.arch.list.BaseAdapter
import dev.chu.chulibrary.arch.ui.BaseFragment
import dev.chu.githubsample.R
import dev.chu.githubsample.databinding.FragmentSearchBinding

class SearchFragment: BaseFragment<FragmentSearchBinding, SearchViewModel>(
    layoutId = R.layout.fragment_search
) {
    override fun connEssentialViews(): EssentialView {
        return buildEssentialViews {
            initBinding {
                it.headerViewModel
                it.viewModel = viewModel
            }
        }
    }

    override fun createAdapter(): BaseAdapter {
        TODO("Not yet implemented")
    }
}