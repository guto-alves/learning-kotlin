package com.gutotech.navigationdemo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.gutotech.navigationdemo.databinding.FragmentWelcomeBinding

class WelcomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentWelcomeBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_welcome, container, false)

        arguments?.let {
            val args = WelcomeFragmentArgs.fromBundle(it)
            binding.emailTextView.text = args.email
            binding.nameTextView.text = args.name
        }

        binding.termsButton.setOnClickListener {
            val action = WelcomeFragmentDirections.actionWelcomeFragmentToTermsFragment()
            it.findNavController().navigate(action)
        }

        return binding.root
    }

}