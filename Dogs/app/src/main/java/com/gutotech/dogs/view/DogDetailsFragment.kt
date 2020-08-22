package com.gutotech.dogs.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.gutotech.dogs.R
import com.gutotech.dogs.databinding.DogDetailsFragmentBinding
import com.gutotech.dogs.viewmodel.DogDetailsViewModel
import com.gutotech.dogs.viewmodel.DogDetailsViewModelFactory

class DogDetailsFragment : Fragment() {
    private lateinit var binding: DogDetailsFragmentBinding
    private lateinit var viewModel: DogDetailsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.dog_details_fragment,
            container, false
        )

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        arguments?.let {
            val dog = DogDetailsFragmentArgs.fromBundle(it).dog
            viewModel = ViewModelProvider(
                this,
                DogDetailsViewModelFactory(dog)
            ).get(DogDetailsViewModel::class.java)

            binding.viewModel = viewModel
        }
    }
}