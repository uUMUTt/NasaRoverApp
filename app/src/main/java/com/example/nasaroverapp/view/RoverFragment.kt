package com.example.nasaroverapp.view

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nasaroverapp.R
import com.example.nasaroverapp.adapter.PhotoAdapter
import com.example.nasaroverapp.databinding.FragmentRoverBinding
import com.example.nasaroverapp.model.PhotoByRover
import com.example.nasaroverapp.model.PhotoData
import com.example.nasaroverapp.viewmodel.RoverFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlin.collections.ArrayList

@AndroidEntryPoint
class RoverFragment constructor(private val _roverName: String) : Fragment() {

    private var page = 1
    private var isLoading = false
    private val limit = 10
    lateinit var layoutManager: LinearLayoutManager
    private val pagePhotos: ArrayList<PhotoByRover> = ArrayList()

    val roverName: String
        get() = _roverName

    private val _viewModel by viewModels<RoverFragmentViewModel>()
    val viewModel: RoverFragmentViewModel
        get() = _viewModel


    private lateinit var adapter: PhotoAdapter
    private lateinit var photoData: PhotoData

    private val defaultSolarDay = "10"

    private lateinit var dataBinding: FragmentRoverBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        dataBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_rover,
            container,
            false
        )
        layoutManager = LinearLayoutManager(context)
        dataBinding.recyclerView.layoutManager = this.layoutManager

        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _viewModel.loadPhotosByRoverName(_roverName, defaultSolarDay)

        setRecyclerViewScrollListener()

        observeLiveData()
    }


    private fun getPage() {
        dataBinding.pbCuriosityFragment.visibility = View.VISIBLE
        isLoading = true

        val start = (page - 1) * limit
        var end = page * limit
        if (end >= photoData.photos.size) {
            end = photoData.photos.size - 1
        }

        for (i in start..end) {
            pagePhotos.add(photoData.photos[i])
        }
        Handler(Looper.getMainLooper()).postDelayed({
            adapter.notifyDataSetChanged()
            isLoading = false
            dataBinding.pbCuriosityFragment.visibility = View.GONE
        }, 3000)
    }

    private fun observeLiveData() {
        _viewModel.photosLivePhotoData.observe(viewLifecycleOwner) {
            photoData = it
            if (photoData.photos.isEmpty()) {
                isLoading = false
                dataBinding.pbCuriosityFragment.visibility = View.GONE
                Toast.makeText(requireContext(), "Camera photos not found", Toast.LENGTH_LONG)
                    .show()
            }
            resetRecyclerView()
            getPage()
        }
    }

    private fun resetRecyclerView() {
        pagePhotos.clear()
        page = 1
        createRecyclerView()
    }

    private fun createRecyclerView() {
        adapter = PhotoAdapter(pagePhotos)
        dataBinding.recyclerView.adapter = adapter
    }


    private fun setRecyclerViewScrollListener() {
        dataBinding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val visibleItemCount = layoutManager.childCount
                val pastVisibleItem = layoutManager.findFirstVisibleItemPosition()
                val total = adapter.itemCount

                if (!isLoading) {
                    if ((visibleItemCount + pastVisibleItem) >= total) {
                        page++
                        getPage()
                    }
                }
            }
        })
    }

}