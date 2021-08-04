package com.aisier.architecture.net

import androidx.lifecycle.Observer
import com.aisier.architecture.base.IUiView
import com.aisier.architecture.entity.DataState
import com.aisier.architecture.entity.IBaseResponse

abstract class IStateObserver<T>(private val uiView: IUiView? = null) : Observer<IBaseResponse<T>> {

    override fun onChanged(t: IBaseResponse<T>) {
        if (t.dataState == DataState.STATE_LOADING) {
            onShowLoading()
            return
        }
        when (t.dataState) {
            DataState.STATE_SUCCESS -> onSuccess(t.httpData)
            DataState.STATE_EMPTY -> onDataEmpty()
            DataState.STATE_FAILED -> onFailed(t.httpCode)
            DataState.STATE_ERROR -> onError(t.error)
            else -> Unit
        }
        uiView?.dismissLoading()
        onComplete()
    }

    abstract fun onSuccess(data: T?)

    abstract fun onDataEmpty()

    open fun onShowLoading() = uiView?.showLoading()

    abstract fun onError(e: Throwable)

    abstract fun onComplete()

    abstract fun onFailed(httpCode: Int)

}