package dev.chu.chulibrary.arch

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.android.support.DaggerFragment
import dev.chu.chulibrary.di.ViewModelInject
import javax.inject.Inject

abstract class BaseFragment<VDB: ViewDataBinding, VM: ViewModel>(
    @LayoutRes private val layoutId: Int
): DaggerFragment() {

    private var _binding: VDB? = null
    private val binding: VDB get() = _binding!!

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    @ViewModelInject
    lateinit var viewModel: VM

    @CallSuper
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        connEssentialViews()
        return binding.root
    }

    @CallSuper
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        /**
         * fragment creation and reattachment 시에만 호출되며, restart 에는 호출되지 않기에 여기서 liveData 를 observe 한다.
         */
        observeViewModel()
    }

    @CallSuper
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    inner class EssentialView {
        fun initBinding(build: (VDB) -> Unit) {
            build(binding)
        }
    }

    protected fun buildEssentialView(buildOption: EssentialView.() -> Unit): EssentialView {
        return EssentialView().apply(buildOption)
    }

    protected abstract fun connEssentialViews(): EssentialView

    @CallSuper
    protected open fun observeViewModel() {}
}