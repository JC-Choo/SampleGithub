package dev.chu.chulibrary.arch.viewmodel

import androidx.lifecycle.ViewModel
import dev.chu.chulibrary.concurrent.AppDispatchers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlin.coroutines.CoroutineContext

abstract class BaseViewModel(

) : ViewModel(), CoroutineScope {

    private val job: Job = SupervisorJob()

    override val coroutineContext: CoroutineContext
        get() = AppDispatchers.UI + job


}