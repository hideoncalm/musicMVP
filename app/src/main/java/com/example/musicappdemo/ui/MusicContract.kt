package com.example.musicappdemo.ui

import com.example.musicappdemo.data.model.Song

interface MusicContract {
    interface View {
        fun setPlayButton()
        fun setPauseButton()
        fun showListSong(songList: List<Song>)
        fun showError(message: String)
    }

    interface Presenter {
        fun getListSong()
    }
}
