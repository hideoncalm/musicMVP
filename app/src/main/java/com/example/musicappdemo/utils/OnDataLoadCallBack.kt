package com.example.musicappdemo.utils

interface OnDataLoadCallBack<T> {
    fun onSuccess(data: T?)
    fun onFail(message: String)
}
