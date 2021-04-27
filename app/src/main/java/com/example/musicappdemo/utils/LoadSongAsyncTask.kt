package com.example.musicappdemo.utils

import android.os.AsyncTask
import com.example.musicappdemo.R

class LoadSongAsyncTask<T>(
    private val callback: OnDataLoadCallBack<T>,
    private val handle: () -> T?
) : AsyncTask<Unit, Unit, T>() {

    override fun doInBackground(vararg params: Unit?): T? {
        return try {
            handle()
        } catch (e: Exception) {
            null
        }
    }

    override fun onPostExecute(result: T?) {
        super.onPostExecute(result)
        result?.let { callback.onSuccess(it) } ?: callback.onFail(R.string.data_load_error.toString())
    }
}
