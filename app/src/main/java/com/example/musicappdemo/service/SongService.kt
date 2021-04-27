package com.example.musicappdemo.service

import com.example.musicappdemo.data.model.Song

interface SongService {
    fun create(song: Song)
    fun play(title: String)
    fun pause(title: String)
    fun isPlaying(): Boolean
}
