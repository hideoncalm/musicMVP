package com.example.musicappdemo.service

import android.app.Service
import android.content.ContentUris
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Binder
import android.os.Build
import android.os.IBinder
import android.provider.MediaStore
import androidx.annotation.RequiresApi
import com.example.musicappdemo.data.model.Song
import com.example.musicappdemo.notification.sendNotification
import com.example.musicappdemo.utils.Constant.NOTIFY_ID

@RequiresApi(Build.VERSION_CODES.O)
class SongServiceImpl : Service(), SongService {

    private var player: MediaPlayer? = null

    override fun onBind(intent: Intent?): IBinder {
        return SongBinder(this)
    }

    override fun create(song: Song) {
        player?.release()
        val uri = ContentUris.withAppendedId(
            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
            song.id
        )
        player = MediaPlayer.create(applicationContext, uri)
    }

    override fun play(title: String) {
        player?.start()
        setNotification(title)
    }

    override fun pause(title: String) {
        player?.pause()
        setNotification(title)
    }

    override fun isPlaying(): Boolean {
        return player?.isPlaying ?: false
    }

    private fun setNotification(title: String) {
        startForeground(NOTIFY_ID, sendNotification(this, title))
    }

    override fun onDestroy() {
        super.onDestroy()
        stopForeground(true)
        player?.apply {
            release()
            stopSelf()
        }
    }

    class SongBinder(private val service: SongServiceImpl) : Binder() {
        fun getService(): SongServiceImpl = service
    }

    companion object {
        fun getIntent(context: Context) = Intent(context, SongServiceImpl::class.java)
    }
}
