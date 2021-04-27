package com.example.musicappdemo.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.musicappdemo.R
import com.example.musicappdemo.data.model.Song
import kotlinx.android.synthetic.main.single_song_item.view.*

class SongAdapter(
    private val itemClickListener: (Int) -> Unit
) : RecyclerView.Adapter<SongAdapter.ViewHolder>() {

    private val songs = mutableListOf<Song>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.single_song_item, parent, false)
        return ViewHolder(view, itemClickListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(songs[position])
    }

    override fun getItemCount() = songs.size

    fun updateData(songs: MutableList<Song>) {
        this.songs.run {
            clear()
            addAll(songs)
            notifyDataSetChanged()
        }
    }

    class ViewHolder(
        itemView: View,
        private val itemClick: (Int) -> Unit
    ) : RecyclerView.ViewHolder(itemView) {

        private var songItem: Song? = null

        init {
            itemView.setOnClickListener {
                itemClick(adapterPosition)
            }
        }

        fun bindData(song: Song) {
            songItem = song
            itemView.apply {
                textViewArtist.text = song.artist
                textViewTitle.text = song.title
            }
        }
    }
}
