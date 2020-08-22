package com.gutotech.navigationdemo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.gutotech.navigationdemo.databinding.FragmentEmailBinding

class EmailFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentEmailBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_email, container, false
        )

        binding.submitButton.setOnClickListener { view ->
            val email = binding.emailEditText.text.toString()

            if (email.isNotBlank()) {
                arguments?.let { bundle ->
                    val name = EmailFragmentArgs.fromBundle(bundle).name

                    val action =
                        EmailFragmentDirections.actionEmailFragmentToWelcomeFragment(name, email)
                    view.findNavController().navigate(action)
                }
            } else {
                Toast.makeText(context, "Email cannot be empty", Toast.LENGTH_SHORT).show()
            }
        }

        return binding.root
    }

}