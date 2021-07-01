package dev.chu.githubsample.base

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.android.support.DaggerAppCompatActivity
import dev.chu.githubsample.R
import dev.chu.githubsample.concurrency.AppDispatchers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancelChildren
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

enum class BaseActivityAnimationType(val type: String) {
    PUSH_LEFT("PUSH_LEFT"),
    PUSH_RIGHT("PUSH_RIGHT"),
    PUSH_BOTTOM("PUSH_BOTTOM"),
    NONE("NONE");

    companion object {
        fun getType(type: String): BaseActivityAnimationType {
            return if (type.isEmpty()) {
                NONE
            } else {
                values().find { it.type == type } ?: NONE
            }
        }
    }
}

abstract class BaseActivity<VDB: ViewDataBinding, VM: ViewModel>(
    @LayoutRes private val layoutRes: Int
): DaggerAppCompatActivity(), CoroutineScope {

    companion object {
        private const val EXTRA_ANI_TYPE = "EXTRA_ANI_TYPE"
        private const val EXTRA_ANIM_IN = "EXTRA_ANIM_IN"
        private const val EXTRA_ANIM_OUT = "EXTRA_ANIM_OUT"

        @JvmStatic
        fun putAnimationIntent(intent: Intent, aniType: BaseActivityAnimationType): Intent {
            intent.putExtra(EXTRA_ANI_TYPE, aniType.type)
            when (aniType) {
                BaseActivityAnimationType.PUSH_LEFT -> {
                    intent.putExtra(EXTRA_ANIM_IN, R.anim.activity_left_from_m50_to_0)
                    intent.putExtra(EXTRA_ANIM_OUT, R.anim.activity_left_from_0_to_100)
                }
                BaseActivityAnimationType.PUSH_RIGHT -> {
                    intent.putExtra(EXTRA_ANIM_IN, R.anim.activity_right_from_50_to_0)
                    intent.putExtra(EXTRA_ANIM_OUT, R.anim.activity_right_from_0_to_m100)
                }
                BaseActivityAnimationType.PUSH_BOTTOM -> {
                    intent.putExtra(EXTRA_ANIM_IN, R.anim.activity_bottom_in)
                    intent.putExtra(EXTRA_ANIM_OUT, R.anim.activity_bottom_from_0_to_100)
                }
                else -> {
                    intent.putExtra(EXTRA_ANIM_IN, 0)
                    intent.putExtra(EXTRA_ANIM_OUT, 0)
                }
            }
            return intent
        }

        @JvmStatic
        fun showActivityAnimation(context: Context?, aniType: BaseActivityAnimationType) {
            if (context is Activity) {
                when (aniType) {
                    BaseActivityAnimationType.PUSH_LEFT -> {
                        context.overridePendingTransition(
                            R.anim.activity_ark_push_show_left_in,
                            R.anim.activity_ark_push_show_left_out
                        )
                    }
                    BaseActivityAnimationType.PUSH_RIGHT -> {
                        context.overridePendingTransition(
                            R.anim.activity_ark_push_show_right_in,
                            R.anim.activity_ark_push_show_right_out
                        )
                    }
                    BaseActivityAnimationType.PUSH_BOTTOM -> {
                        context.overridePendingTransition(
                            R.anim.activity_ark_pop_show_in,
                            R.anim.activity_ark_pop_show_out
                        )
                    }
                    else -> {
                        context.overridePendingTransition(0, 0)
                    }
                }
            }
        }
    }

    private var _binding: VDB? = null
    protected val binding: VDB get() = _binding!!

    private lateinit var job: Job

    override val coroutineContext: CoroutineContext
        get() = AppDispatchers.UI + job

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @field:ViewModelInject
    @field:Inject
    lateinit var viewModel: VM
        protected set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = DataBindingUtil.setContentView(this, layoutRes)
        binding.lifecycleOwner = this

        connEssentialViews()
        job = SupervisorJob()

        observeViewModel()
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancelChildren()
        _binding = null
        job.cancel()
    }

    protected abstract fun connEssentialViews(): EssentialView

    protected open fun observeViewModel() {}

    protected inner class EssentialView {
        fun initBinding(build: (VDB) -> Unit) {
            build(binding)
        }
    }

    protected fun buildEssentialView(buildOptions: EssentialView.() -> Unit): EssentialView {
        return EssentialView().apply(buildOptions)
    }
}