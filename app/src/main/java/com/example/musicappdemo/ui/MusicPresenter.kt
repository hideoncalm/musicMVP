package com.example.musicappdemo.ui

import com.example.musicappdemo.data.model.Song
import com.example.musicappdemo.data.repository.SongRepository
import com.example.musicappdemo.utils.OnDataLoadCallBack

class MusicPresenter(
    private val view: MusicContract.View,
    private val repository: SongRepository
) : MusicContract.Presenter {

    override fun getListSong() {
        repository.getSong(object : OnDataLoadCallBack<List<Song>> {
            override fun onSuccess(data: List<Song>?) {
                data?.let {
                    if (it.isNotEmpty()) view.showListSong(it)
                }
            }

            override fun onFail(message: String) {
                view.showError(message)
            }
        })
    }
}
