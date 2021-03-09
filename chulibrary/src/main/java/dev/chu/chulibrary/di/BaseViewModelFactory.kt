package dev.chu.chulibrary.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject
import javax.inject.Provider

/**
 * ViewModelProvider 를 만들어주기 위한 클래스
 * - [creators] : ViewModel class 를 키로 갖는 멀티 바인딩 Map
 * - [creator] : ViewModel 클래스를 키로 하며, ViewModel 객체를 생성하는 Provider 를 가져온다.
 * - [modelClass.isAssignableFrom(key)] : 해당 파라미터에 대해 값이 있는지 여부
 * - [creator.get() as T]] : Provider 로부터 ViewModel 객체 생성 및 반환
 */
class BaseViewModelFactory @Inject constructor(
    private val creators: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        var creator = creators[modelClass]

        if (creator == null) {
            for ((key, value) in creators) {
                if (modelClass.isAssignableFrom(key)) {
                    creator = value
                    break
                }
            }
        }

        if (creator == null) {
            error("unknown model class $modelClass")
        }

        try {
            return creator.get() as T
        } catch (e: Exception) {
            throw e
        }
    }
}