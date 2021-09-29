package com.example.ilink_test_application.ui.favorite

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.ilink_test_application.R
import com.example.ilink_test_application.databinding.FragmentFavotireBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * Class of view of favorites
 *
 * @property[binding] provides layout ids
 * @property[viewModel] provides viewmodel
 */
@AndroidEntryPoint
class FavoriteFragment : Fragment(R.layout.fragment_favotire) {

    private val binding by viewBinding(FragmentFavotireBinding::bind)
    private val viewModel: FavoriteViewModel by viewModels()

    /**
     * Function to action when [View] is created
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecyclerView()
    }

    /**
     * Function to set Recycler View
     */
    private fun setRecyclerView() {
        val adapter = FavoriteAdapter { animal ->
            viewModel.deleteAnimal(animal)
        }

        with(binding.rvFavorite) {
            this.layoutManager = GridLayoutManager(context, 3)
            this.adapter = adapter
        }

        viewModel.favoriteAnimals.observe(viewLifecycleOwner) { adapter.submitList(it) }
    }
}
