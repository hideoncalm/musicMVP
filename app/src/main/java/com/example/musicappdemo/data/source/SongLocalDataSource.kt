package com.example.musicappdemo.data.source

import com.example.musicappdemo.data.model.Song
import com.example.musicappdemo.utils.LoadSongAsyncTask
import com.example.musicappdemo.utils.OnDataLoadCallBack

class SongLocalDataSource private constructor(
    private val handler: SongLocalHandler
) : SongDataSource {

    override fun getSong(callback: OnDataLoadCallBack<List<Song>>) {
        LoadSongAsyncTask(callback) {
            handler.getLocalSong()
        }.execute()
    }

    companion object {
        private var instance: SongLocalDataSource? = null
        fun getInstance(handler: SongLocalHandler) =
            instance ?: SongLocalDataSource(handler).also { instance = it }
    }
}
