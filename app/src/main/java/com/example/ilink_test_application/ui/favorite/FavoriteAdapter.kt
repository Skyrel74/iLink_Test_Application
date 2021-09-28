package com.example.ilink_test_application.ui.favorite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ilink_test_application.data.entities.Animal
import com.example.ilink_test_application.databinding.ItemFavoriteBinding
import com.example.ilink_test_application.utils.DoubleClickListener

class FavoriteAdapter(private val onClick: (Animal) -> Unit) :
    ListAdapter<Animal, FavoriteAdapter.FavoriteViewHolder>(diffUtilCallback) {

    private object diffUtilCallback : DiffUtil.ItemCallback<Animal>() {
        override fun areItemsTheSame(oldItem: Animal, newItem: Animal): Boolean =
            oldItem.url == newItem.url

        override fun areContentsTheSame(oldItem: Animal, newItem: Animal): Boolean =
            oldItem.fileUri == newItem.fileUri
    }

    inner class FavoriteViewHolder(private val binding: ItemFavoriteBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Animal) {
            Glide.with(itemView).load(item.fileUri).centerCrop().into(binding.ivContainer)
            binding.ivContainer.setOnClickListener(DoubleClickListener(
                callback = object : DoubleClickListener.Callback {
                    override fun doubleClicked() {
                        onClick(item)
                    }
                }
            ))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemFavoriteBinding.inflate(inflater, parent, false)
        return FavoriteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) =
        holder.bind(getItem(position))
}