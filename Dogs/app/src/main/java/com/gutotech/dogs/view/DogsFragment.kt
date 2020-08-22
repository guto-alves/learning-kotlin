package com.gutotech.dogs.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.gutotech.dogs.R
import com.gutotech.dogs.viewmodel.DogsViewModel
import kotlinx.android.synthetic.main.fragment_dogs.*

class DogsFragment : Fragment() {
    private lateinit var viewModel: DogsViewModel
    private val adapter = DogsAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_dogs, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dogsRecyclerView.adapter = adapter

        viewModel = ViewModelProvider(this).get(DogsViewModel::class.java)

        viewModel.dogs.observe(viewLifecycleOwner, Observer { dogs ->
            dogs?.let {
                adapter.updateDogList(it)
            }
        })

        viewModel.isLoading.observe(viewLifecycleOwner, Observer { isLoading ->
            progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        })

        viewModel.hasError.observe(viewLifecycleOwner, Observer { hasError ->
            errorTextView.visibility = if (hasError) View.VISIBLE else View.GONE
        })

        viewModel.refresh()

        swipeRefreshLayout.setOnRefreshListener {
            viewModel.refresh()
            swipeRefreshLayout.isRefreshing = false
        }
    }
}