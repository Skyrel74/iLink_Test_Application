package com.example.ilink_test_application.ui.random

import android.content.Context
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.ilink_test_application.R
import com.example.ilink_test_application.data.entities.Animal
import com.example.ilink_test_application.databinding.FragmentRandomBinding
import com.example.ilink_test_application.utils.DoubleClickListener
import com.example.ilink_test_application.utils.Resource
import com.example.ilink_test_application.utils.toByteArray
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File

/**
 * Class of view of random
 *
 * @property[binding] provides layout ids
 * @property[viewModel] provides viewmodel
 *
 * @property[drawable] keeps drawable of last animal
 */
@AndroidEntryPoint
class RandomFragment : Fragment(R.layout.fragment_random) {

    private val binding by viewBinding(FragmentRandomBinding::bind)
    private val viewModel: RandomViewModel by viewModels()

    lateinit var drawable: Drawable

    /**
     * Function to action when [View] is created
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.ivRandomAnimal.isVisible = false
        binding.progressBar.isVisible = false
        setupListeners()
        viewModel.isFav().observe(viewLifecycleOwner) {
            viewModel.isFavorite = it
        }
    }

    /**
     * Function to setup listeners
     */
    private fun setupListeners() {
        binding.btnShowCat.setOnClickListener {
            setupObservers(viewModel.getRandomCat)
        }

        binding.btnShowDuck.setOnClickListener {
            setupObservers(viewModel.getRandomDuck)
        }

        binding.ivRandomAnimal.setOnClickListener(DoubleClickListener(
            callback = object : DoubleClickListener.Callback {
                override fun doubleClicked() {
                    if (viewModel.isFavorite) {
                        viewModel.deleteFavoriteAnimal()
                        Toast.makeText(requireContext(),
                            "Картинка удалена из понравившихся",
                            Toast.LENGTH_SHORT)
                            .show()
                    } else {
                        viewModel.saveFavoriteAnimal()
                        lifecycleScope.launch(Dispatchers.Main) {
                            saveToFile(drawable, viewModel.lastAnimal!!)
                        }
                        Toast.makeText(requireContext(),
                            "Картинка сохранена в понравившиеся",
                            Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        ))
    }

    /**
     * Function to setup observers
     */
    private fun setupObservers(liveData: LiveData<Resource<Animal>>) {
        liveData.observe(viewLifecycleOwner) { resource ->
            when (resource.status) {
                Resource.Status.SUCCESS -> {
                    val animal = resource.data!!
                    lifecycleScope.launch(Dispatchers.Main) {
                        viewModel.lastAnimal = animal
                        drawable = viewModel.getDrawable(animal.url)
                        binding.ivRandomAnimal.setImageDrawable(drawable)
                        blockInteraction(false)
                        binding.ivRandomAnimal.isVisible = true
                    }
                }
                Resource.Status.ERROR -> {
                    Toast.makeText(requireContext(), resource.message, Toast.LENGTH_SHORT)
                        .show()
                    blockInteraction(false)
                    binding.ivRandomAnimal.isVisible = false
                }
                Resource.Status.LOADING ->
                    blockInteraction(true)
            }
        }
    }

    /**
     * Function which block interactions by @param[isLoading]
     */
    private fun blockInteraction(isLoading: Boolean) {
        binding.progressBar.isVisible = isLoading
        binding.ivRandomAnimal.isEnabled = !isLoading
        binding.btnShowCat.isEnabled = !isLoading
        binding.btnShowDuck.isEnabled = !isLoading
    }

    /**
     * Function to save file in local storage
     *
     * @param[drawable] keeps drawable
     * @param[animal] keeps animal
     */
    fun saveToFile(drawable: Drawable, animal: Animal) {
        val filename = animal.url.substringAfterLast("/")
        val file = File(context?.filesDir, filename)
        context?.openFileOutput(filename, Context.MODE_PRIVATE).use { stream ->
            stream?.write(drawable.toByteArray())
        }
        animal.fileUri = Uri.fromFile(file).toString()
        viewModel.saveFavoriteAnimal()
    }
}
