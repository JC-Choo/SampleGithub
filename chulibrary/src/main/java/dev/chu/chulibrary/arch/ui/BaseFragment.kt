package dev.chu.chulibrary.arch.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ProgressBar
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.core.view.ViewCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import dagger.android.support.DaggerFragment
import dev.chu.chulibrary.arch.event.EventHandler
import dev.chu.chulibrary.arch.list.BaseAdapter
import dev.chu.chulibrary.concurrent.BaseCoroutineScope
import dev.chu.chulibrary.concurrent.UICoroutineScope
import dev.chu.chulibrary.di.ViewModelInject
import java.util.*
import javax.inject.Inject

@Suppress("MemberVisibilityCanBePrivate")
abstract class BaseFragment<VDB: ViewDataBinding, VM: ViewModel> @JvmOverloads constructor(
    @LayoutRes private val layoutId: Int,
    private var isAutoSubmit: Boolean = true,
    scope: BaseCoroutineScope = UICoroutineScope()
): DaggerFragment(), EventHandler, BaseCoroutineScope by scope {

    companion object {
        private const val KEY_EVENT_HANDLE_KEY = "KEY_EVENT_HANDLE_KEY"
    }

    /**
     * [_binding] : [ViewDataBinding]을 내부에서 사용하기 위한 변수
     * [onDestroyView]에서 Memory leak 을 방지하기 위해 null 처리를 해준다.
     *
     * [binding] : [ViewDataBinding]을 외부에서 사용하기 위한 변수
     */
    private var _binding: VDB? = null
    protected val binding: VDB
        get() = _binding!!

    /**
     * [viewModelFactory] : [ViewModel]을 생성할 때 사용할 [ViewModelProvider.Factory]
     * [viewModel] : [ViewModelInject]을 통해 [Inject] 한, [BaseFragment]와 연결된 [ViewModel]
     */
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    @Inject
    @ViewModelInject
    lateinit var viewModel: VM
        protected set

    /**
     * 현재 [BaseFragment]의 [Event]를 구분하여 처리할 수 있게 하는 key 값.
     */
    private var _eventHandleKey: String = ""
    override val eventHandleKey: String
        get() = _eventHandleKey

    /**
     * [BaseFragment]의 [recyclerView]가 사용할 [BaseAdapter]
     */
    val adapter: BaseAdapter by lazy { createAdapter() }

    /**
     * 공통으로 사용할 [View]
     * [recyclerView] : [adapter]와 연결해 자동으로 data 를 fetch 한다.
     * [swipeRefreshLayout] : [viewModel]의 refresh 와 연결해 자동으로 data 를 fetch 한다.
     * [loadingProgressBar] : fetchData 수행 중 노출한다.
     */
    var recyclerView: RecyclerView? = null
        protected set
    var swipeRefreshLayout: SwipeRefreshLayout? = null
        protected set
    var loadingProgressBar: ProgressBar? = null
        protected set

    /**
     * view 를 연결하기 위한 class
     * 클래스 생성은, 반드시 [buildEssentialViews]를 통해서 생성하도록 한다.
     *
     * inflate 된 [binding] 내 존재하는 view([RecyclerView], [SwipeRefreshLayout], [ProgressBar]) 를 반환한다.
     */
    protected inner class EssentialView {
        fun initBinding(build: (VDB) -> Unit) {
            build(binding)
        }

        fun connRecyclerView(build: (VDB) -> RecyclerView?) {
            recyclerView = build(binding).also {
                if (it != null) {
                    it.adapter = adapter
                    initRecyclerView(it)
                }
            }
        }

        fun connSwipeRefreshLayout(build: (VDB) -> SwipeRefreshLayout?) {
            swipeRefreshLayout = build(binding).also {
                if (it != null) {
                    initSwipeRefreshLayout(it)
                }
            }
        }

        fun connLoadingProgressBar(build: (VDB) -> ProgressBar?) {
            loadingProgressBar = build(binding)
        }
    }

    /**
     * [EssentialView] 생성을 위한 DSL.
     * - [BaseFragment]로 부터 노출될 화면의 메인이 되는 [recyclerView]와 노출할 데이터를 가져오는 [viewModel]의
     *  fetchData 의 시작과 종료 흐름에 관여하는 [loadingProgressBar], [swipeRefreshLayout] 과 같은
     *  필수적인 view 를 [buildEssentialViews]를 사용해 연결한다.
     */
    protected fun buildEssentialViews(build: EssentialView.() -> Unit): EssentialView =
        EssentialView().apply(build)

    /**
     * [EssentialView] 연결을 위한 abstract fun
     */
    protected abstract fun connEssentialViews(): EssentialView

    protected abstract fun createAdapter(): BaseAdapter

    /**
     * [viewModel]과 관련한 observe 로직을 구현하는 곳
     * - [BaseFragment]에서 기본적으로 data fetch 를 통해 UI 처리를 해준다.
     * - [isAutoSubmit] == true, [BaseViewModel.items]을 observe 하여 [BaseAdapter.list]를 자동으로 변경해 준다.
     */
    @CallSuper
    protected open fun observeViewModel() {
        if (isAutoSubmit) {

        }
    }

    /**
     * 초기 설정 관련 필요 시 override 하기 위한 fun
     */
    protected open fun initRecyclerView(rv: RecyclerView) {}
    protected open fun initSwipeRefreshLayout(srl: SwipeRefreshLayout) {
        with(srl) {
            setOnRefreshListener {
//                refreshItems()
            }
        }
    }

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _eventHandleKey = if (savedInstanceState == null) {
            UUID.randomUUID().toString()
        } else {
            savedInstanceState.getString(KEY_EVENT_HANDLE_KEY, "")
        }
    }

    @CallSuper
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(KEY_EVENT_HANDLE_KEY, _eventHandleKey)
    }

    @CallSuper
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        connEssentialViews()

        return binding.root
    }

    @CallSuper
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        observeViewModel()
    }

    @CallSuper
    override fun onDestroyView() {
        super.onDestroyView()

        recyclerView?.addOnAttachStateChangeListener(object : View.OnAttachStateChangeListener {
            override fun onViewAttachedToWindow(v: View?) {
                v?.removeOnAttachStateChangeListener(this)
            }

            override fun onViewDetachedFromWindow(v: View?) {
                v?.removeOnAttachStateChangeListener(this)
                (v as? RecyclerView)?.adapter = null
            }
        })
        swipeRefreshLayout = null
        recyclerView?.adapter = null
        recyclerView = null
        loadingProgressBar = null
        _binding = null
    }

    @CallSuper
    override fun onDestroy() {
        super.onDestroy()
        releaseCoroutine()
    }

    override fun onCreateAnimation(transit: Int, enter: Boolean, nextAnim: Int): Animation? {
        if (nextAnim != 0) {
            val nextAnimation = AnimationUtils.loadAnimation(context, nextAnim)
            nextAnimation.setAnimationListener(object : Animation.AnimationListener {
                private var startZ = 0f

                override fun onAnimationStart(animation: Animation?) {
                    view?.run {
                        startZ = ViewCompat.getTranslationZ(this)
                        ViewCompat.setTranslationZ(this, 1f)
                    }
                }

                override fun onAnimationEnd(animation: Animation?) {
                    view?.run {
                        this.postDelayed({
                            ViewCompat.setTranslationZ(this, startZ)
                        }, 100)
                    }
                }

                override fun onAnimationRepeat(animation: Animation?) {}
            })
            return nextAnimation
        } else {
            return null
        }
    }
}