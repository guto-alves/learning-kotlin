package com.gutotech.navigationdemo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.gutotech.navigationdemo.databinding.FragmentNameBinding

class NameFragment : Fragment() {
    private lateinit var binding: FragmentNameBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_name, container, false
        )

        binding.nameEditText.setOnEditorActionListener { textView, i, keyEvent ->
            goToNextFragment(textView)
            return@setOnEditorActionListener false
        }

        binding.nextButton.setOnClickListener(this::goToNextFragment)

        return binding.root
    }

    private fun goToNextFragment(view: View) {
        val name = binding.nameEditText.text.toString()

        if (name.isNotBlank()) {
            val action = NameFragmentDirections.actionNameFragmentToEmailFragment(name)
            view.findNavController().navigate(action)
        } else {
            Toast.makeText(context, "User name cannot be empty!", Toast.LENGTH_SHORT).show()
        }
    }

}