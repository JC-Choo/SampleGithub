package dev.chu.chulibrary.arch

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.android.support.DaggerFragment
import dev.chu.chulibrary.di.ViewModelInject
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

@Suppress("MemberVisibilityCanBePrivate")
abstract class BaseFragment<VDB: ViewDataBinding, VM: ViewModel>(
    @LayoutRes private val layoutId: Int
): DaggerFragment(), CoroutineScope {

    /**
     * [_binding] : [ViewDataBinding]을 내부에서 사용하기 위한 변수
     * [onDestroyView]에서 Memory leak 을 방지하기 위해 null 처리를 해준다.
     *
     * [binding] : [ViewDataBinding]을 외부에서 사용하기 위한 변수
     */
    private var _binding: VDB? = null
    protected val binding: VDB get() = _binding!!

    /**
     * [ViewModel]을 생성할 때 사용할 [ViewModelProvider.Factory]
     */
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    @Inject
    @ViewModelInject
    lateinit var viewModel: VM
        private set

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}