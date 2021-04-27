package com.example.musicappdemo.data.source

import com.example.musicappdemo.data.model.Song
import com.example.musicappdemo.utils.OnDataLoadCallBack

interface SongDataSource {
    fun getSong(callback: OnDataLoadCallBack<List<Song>>)
}
