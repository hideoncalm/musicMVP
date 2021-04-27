package com.example.musicappdemo.data.model

import android.database.Cursor
import android.provider.MediaStore

data class Song(
    val id: Long,
    val title: String,
    val artist: String
) {

    constructor(cursor: Cursor) : this(
        cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media._ID)),
        cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME)),
        cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST))
    )
}
