package com.example.simepledemo.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.simepledemo.databinding.FragmentListBinding
import com.example.simepledemo.navigator.AppNavigator
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ListFragment: Fragment() {
    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ListViewModel by viewModels()

    @Inject
    lateinit var navigator: AppNavigator

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        binding.apply {
            viewModel = this@ListFragment.viewModel
            lifecycleOwner = this@ListFragment
        }

        binding.adapter = PhotosAdapter(listOf(), viewModel)
        binding.list.layoutManager = GridLayoutManager(context,3)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.showDetail.observe(viewLifecycleOwner){
            it.getContentIfNotHandled()?.let {
                // TODO remove, just test navigation
                navigator.navigateToDetail()
            }
        }
    }
}