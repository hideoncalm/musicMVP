package com.example.musicappdemo.data.repository

import com.example.musicappdemo.data.model.Song
import com.example.musicappdemo.data.source.SongDataSource
import com.example.musicappdemo.utils.OnDataLoadCallBack

class SongRepository private constructor(
    private val localSource: SongDataSource
) : SongDataSource {

    override fun getSong(callback: OnDataLoadCallBack<List<Song>>) {
        localSource.getSong(callback)
    }

    companion object {
        private var instance: SongRepository? = null
        fun getInstance(localSource: SongDataSource) =
            instance ?: SongRepository(localSource).also { instance = it }
    }

}
