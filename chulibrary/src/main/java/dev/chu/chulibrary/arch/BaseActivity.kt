package dev.chu.chulibrary.arch

import android.os.Bundle
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import dagger.android.support.DaggerAppCompatActivity

abstract class BaseActivity<VDB: ViewDataBinding>(
    @LayoutRes private val layoutId: Int
): DaggerAppCompatActivity() {

    private var _binding: VDB? = null
    val binding: VDB get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = DataBindingUtil.setContentView(this, layoutId)
        binding.lifecycleOwner = this

        connEssentialViews()
        observeViewModel()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    inner class EssentialView {
        fun initBinding(build: (VDB) -> Unit) {
            build(binding)
        }
    }

    /**
     * (EssentialView) -> Unit 이 아닌 EssentialView.() -> Unit 으로 한 이유는,
     * 첫번째의 경우 EssentialView 를 파라미터로 전달받아 it 으로 객체를 리턴하지만
     * 두번째의 경우 EssentialView 를 수신객체로 전달받아 this 로 객체를 리턴하는 차이가 있다.
     */
    protected fun buildEssentialView(buildOptions: EssentialView.() -> Unit): EssentialView {
        return EssentialView().apply(buildOptions)
    }

    protected abstract fun connEssentialViews(): EssentialView

    @CallSuper
    protected open fun observeViewModel() {}
}