package com.example.templatemvvm.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.templatemvvm.R
import com.example.templatemvvm.ui.dashboard.home.details.PhotoVO
import kotlinx.android.synthetic.main.item_photos.view.*

class PhotosAdapter constructor(
    private val onPhotoSelected:
        (PhotoVO) -> Unit
) : RecyclerView.Adapter<PhotosAdapter.PhotosCellViewHolder>() {

    private val photos: MutableList<PhotoVO> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotosCellViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.item_photos,
            parent,
            false
        )
        return PhotosCellViewHolder(view)
    }

    fun addPhotos(photos: List<PhotoVO>) {
        this.photos.addAll(photos)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return this.photos.size
    }

    override fun onBindViewHolder(holder: PhotosCellViewHolder, position: Int) {
        val photo = photos[position]
        holder.bind(photo, onPhotoSelected)
    }


    class PhotosCellViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(photo: PhotoVO, listener: (PhotoVO) -> Unit) = with(itemView) {
            img.setImageResource(photo.photo)
            setOnClickListener { listener(photo) }
        }
    }
}