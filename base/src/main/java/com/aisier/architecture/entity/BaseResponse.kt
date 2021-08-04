package com.aisier.architecture.entity

class BaseResponse<T> : IBaseResponse<T> {

    override val httpCode: Int
        get() = errorCode

    override val httpMsg: String?
        get() = errorMsg

    override val httpData: T?
        get() = data

    override val isSuccess: Boolean
        get() = errorCode == 0

    private var errorCode = -1

    var errorMsg: String? = null

    override var dataState: DataState? = null

    override lateinit var error: Throwable

    private val data: T? = null
}