package com.tekydevelop.radixfm.search.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tekydevelop.domain.model.search.SearchAlbum
import com.tekydevelop.radixfm.R

class SearchAdapter(private val listener: (SearchAlbum) -> Unit) : RecyclerView.Adapter<SearchAdapter.ViewHolder>() {

    private var context: Context? = null
    private var searchAlbums: List<SearchAlbum> = mutableListOf()

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val albumName: TextView = view.findViewById(R.id.album_name)
        val artistName: TextView = view.findViewById(R.id.artist_name)
        val albumImage: ImageView = view.findViewById(R.id.album_image)
        val itemRoot: ConstraintLayout = view.findViewById(R.id.item_root)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.top_item_album, parent, false)
        context = parent.context
        return SearchAdapter.ViewHolder(view)
    }

    override fun onBindViewHolder(holder: SearchAdapter.ViewHolder, position: Int) {
        val item = searchAlbums[position]

        holder.albumName.text = item.name
        holder.artistName.text = item.artist
        holder.itemRoot.setOnClickListener { listener(item) }

        if (context != null) {
            //todo fix item.image[3]
            Glide.with(context!!)
                .load(item.image[3].url)
                .error(R.drawable.placeholder)
                .into(holder.albumImage)
        }
    }

    override fun getItemCount(): Int {
        return searchAlbums.size
    }

    fun update(searchAlbums: List<SearchAlbum>) {
        this.searchAlbums = searchAlbums
        notifyDataSetChanged()
    }
}