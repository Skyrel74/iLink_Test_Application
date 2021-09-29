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

/**
 * Class of adapter to set data in recycler view
 *
 * @property[onClick] action with recycler view item
 */
class FavoriteAdapter(private val onClick: (Animal) -> Unit) :
    ListAdapter<Animal, FavoriteAdapter.FavoriteViewHolder>(diffUtilCallback) {

    /**
     * Object to perform callback when data changing
     */
    private object diffUtilCallback : DiffUtil.ItemCallback<Animal>() {

        /**
         * Function compare [oldItem] and [newItem]
         */
        override fun areItemsTheSame(oldItem: Animal, newItem: Animal): Boolean =
            oldItem.url == newItem.url

        /**
         * Function compare [oldItem] content and [newItem] content
         */
        override fun areContentsTheSame(oldItem: Animal, newItem: Animal): Boolean =
            oldItem.fileUri == newItem.fileUri
    }

    /**
     * Inner class to hold data in view
     *
     * @property[binding] provides layout ids
     */
    inner class FavoriteViewHolder(private val binding: ItemFavoriteBinding) :
        RecyclerView.ViewHolder(binding.root) {

        /**
         * Function to bind @param[item]
         */
        fun bind(item: Animal) {

            Glide.with(itemView)
                .load(item.fileUri)
                .centerCrop()
                .into(binding.ivContainer)

            binding.ivContainer.setOnClickListener(DoubleClickListener(
                callback = object : DoubleClickListener.Callback {

                    override fun doubleClicked() {
                        onClick(item)
                    }
                }
            ))
        }
    }

    /**
     * Function to create view holder
     *
     * @return[FavoriteViewHolder]
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemFavoriteBinding.inflate(inflater, parent, false)
        return FavoriteViewHolder(binding)
    }

    /**
     * Function to bind data element in [FavoriteViewHolder] by its [position] in collection
     */
    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) =
        holder.bind(getItem(position))
}
