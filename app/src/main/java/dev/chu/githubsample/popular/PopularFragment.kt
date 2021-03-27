package dev.chu.githubsample.popular

import androidx.fragment.app.viewModels
import dev.chu.chulibrary.arch.BaseFragment
import dev.chu.chulibrary.util.extensions.getDrawableById
import dev.chu.chulibrary.util.extensions.toast
import dev.chu.githubsample.R
import dev.chu.githubsample.common.HeaderViewModel
import dev.chu.githubsample.databinding.FragmentPopularBinding

class PopularFragment :
    BaseFragment<FragmentPopularBinding, PopularViewModel>(R.layout.fragment_popular) {

    private val headerViewModel: HeaderViewModel by viewModels()

    override fun connEssentialViews(): EssentialView {
        return buildEssentialView {
            initBinding {
                it.headerViewModel = headerViewModel
                headerViewModel.setTitle(getString(R.string.popular))
                headerViewModel.setImageLeft(requireContext().getDrawableById(R.drawable.account_circle_black))
                headerViewModel.showImageRight(true)
                headerViewModel.setImageRight(requireContext().getDrawableById(R.drawable.search))
            }
        }
    }

    override fun observeViewModel() {
        super.observeViewModel()

        headerViewModel.clickHeaderImageLeft.observe(viewLifecycleOwner) {
            toast("좌측 메뉴 클릭")
        }

        headerViewModel.clickHeaderImageRight.observe(viewLifecycleOwner) {
            toast("우측 메뉴 클릭")
        }
    }
}