package com.example.musicappdemo.ui

import android.Manifest
import android.content.ComponentName
import android.content.ServiceConnection
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.musicappdemo.R
import com.example.musicappdemo.data.model.Song
import com.example.musicappdemo.data.repository.SongRepository
import com.example.musicappdemo.data.source.SongLocalHandler
import com.example.musicappdemo.data.source.SongLocalDataSource
import com.example.musicappdemo.service.SongServiceImpl
import com.example.musicappdemo.ui.adapter.SongAdapter
import kotlinx.android.synthetic.main.activity_main.*

@RequiresApi(Build.VERSION_CODES.O)
class MainActivity : AppCompatActivity(),
    MusicContract.View,
    View.OnClickListener {

    private var service: SongServiceImpl? = null
    private var presenter: MusicPresenter? = null
    private var songList = mutableListOf<Song>()
    private var songAdapter = SongAdapter(::onItemClickListener)
    private var currentSong = 0

    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, servicee: IBinder?) {
            val binder = servicee as SongServiceImpl.SongBinder
            service = binder.getService()
        }

        override fun onServiceDisconnected(name: ComponentName?) {
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkPermission()
        initRecyclerView()
        initData()
        initListeners()
    }

    override fun onStart() {
        super.onStart()
        bindService(SongServiceImpl.getIntent(this), serviceConnection, BIND_AUTO_CREATE)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.buttonPlayMusic -> playOrPauseSong(null)
            R.id.buttonSkipNext -> playNextSong()
            R.id.buttonSkipPrevious -> playPreviousSong()
        }
    }

    override fun setPlayButton() {
        buttonPlayMusic.setBackgroundResource(R.drawable.ic_baseline_play_arrow)
    }

    override fun setPauseButton() {
        buttonPlayMusic.setBackgroundResource(R.drawable.ic_baseline_stop)
    }

    override fun showListSong(songList: List<Song>) {
        this.songList.addAll(songList)
        songAdapter.updateData(songList.toMutableList())
    }

    override fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
            requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 1)
        }
    }

    private fun initRecyclerView() {
        recyclerViewListMusic.adapter = songAdapter
    }

    private fun initData() {
        presenter = MusicPresenter(
            this,
            SongRepository.getInstance(SongLocalDataSource.getInstance(SongLocalHandler(this)))
        )
        presenter?.getListSong()
    }

    private fun initListeners() {
        buttonPlayMusic.setOnClickListener(this)
        buttonSkipPrevious.setOnClickListener(this)
        buttonSkipNext.setOnClickListener(this)
    }

    private fun playPreviousSong() {
        if (--currentSong < 0) currentSong = songList.size - 1
        service?.create(songList[currentSong])
        setPlay(true)
    }

    private fun playNextSong() {
        ++currentSong
        currentSong %= (songList.size)
        service?.create(songList[currentSong])
        setPlay(true)
    }

    private fun playOrPauseSong(b: Boolean?) {
        if (b != null) setPlay(b) else setPlay(service?.isPlaying() == false)
    }

    private fun setPlay(b: Boolean) {
        if (b) {
            service?.play(songList[currentSong].title)
            setPauseButton()
        } else {
            service?.pause(songList[currentSong].title)
            setPlayButton()
        }
    }

    private fun onItemClickListener(position: Int) {
        presenter?.apply {
            currentSong = position
            service?.create(songList[currentSong])
            playOrPauseSong(true)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unbindService(serviceConnection)
    }
}
