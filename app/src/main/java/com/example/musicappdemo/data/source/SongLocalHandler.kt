package com.example.musicappdemo.data.source

import android.content.Context
import android.provider.MediaStore
import com.example.musicappdemo.data.model.Song

class SongLocalHandler(private val context: Context) {
    fun getLocalSong(): List<Song> {
        val songList = mutableListOf<Song>()
        val uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        val cursor = context.contentResolver.query(uri, null, null, null, null)
        cursor?.let {
            if (it.moveToFirst()) {
                do {
                    songList.add(Song(it))
                } while (it.moveToNext())
            }
        }
        cursor?.close()
        return songList
    }
}
