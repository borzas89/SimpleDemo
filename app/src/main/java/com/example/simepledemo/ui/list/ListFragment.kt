package com.example.simepledemo.ui.list

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.example.simepledemo.databinding.FragmentListBinding
import com.example.simepledemo.navigator.AppNavigator
import com.example.simepledemo.util.Util.launchAndRepeatWithViewLifecycle
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import javax.inject.Inject

@AndroidEntryPoint
class ListFragment : Fragment() {
    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ListViewModel by viewModels()

    private val mDisposable = CompositeDisposable()

    @Inject
    lateinit var navigator: AppNavigator

    @SuppressLint("UnsafeRepeatOnLifecycleDetector")
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

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = PhotoAdapter(viewModel)
        binding.list.adapter = adapter
        binding.list.layoutManager = GridLayoutManager(context, 2)

        viewModel.getPhotos()
            .subscribe {
                adapter.submitData(lifecycle, it)
            }.addTo(mDisposable)

        viewModel.showDetail.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let { photo ->
                navigator.navigateToDetail(photo.id, photo.url)
            }
        }

        launchAndRepeatWithViewLifecycle{
            adapter.loadStateFlow.collect {
                binding.prependProgress.isVisible = it.source.prepend is LoadState.Loading
                binding.appendProgress.isVisible = it.source.append is LoadState.Loading
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mDisposable.clear()
    }

}
