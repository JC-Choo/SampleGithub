package dev.chu.githubsample.popular

import dev.chu.chulibrary.arch.BaseFragment
import dev.chu.githubsample.R
import dev.chu.githubsample.databinding.FragmentPopularBinding

class PopularFragment :
    BaseFragment<FragmentPopularBinding, PopularViewModel>(R.layout.fragment_popular) {
    override fun connEssentialViews(): EssentialView {
        return buildEssentialView {

        }
    }
}