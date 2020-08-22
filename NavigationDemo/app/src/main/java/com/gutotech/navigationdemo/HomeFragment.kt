package com.gutotech.navigationdemo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.gutotech.navigationdemo.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentHomeBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)

        binding.signUpButton.setOnClickListener {
            it.findNavController()
                .navigate(HomeFragmentDirections.actionHomeFragmentToNameFragment())
        }

        binding.termsButton.setOnClickListener {
            it.findNavController()
                .navigate(HomeFragmentDirections.actionHomeFragmentToTermsFragment())
        }

        return binding.root
    }
}