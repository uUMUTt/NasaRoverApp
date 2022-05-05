package com.example.nasaroverapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.nasaroverapp.model.PhotoData
import com.example.nasaroverapp.repository.RetrofitRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RoverFragmentViewModel @Inject constructor(private val repository: RetrofitRepository) :
    ViewModel() {

    private val _photosLiveData = MutableLiveData<PhotoData>()
    val photosLivePhotoData: LiveData<PhotoData>
        get() = _photosLiveData

    fun loadPhotosByRoverName(roverName: String, solarDay: String) {
        repository.getPhotosByRoverName(_photosLiveData, roverName, solarDay)
    }

    fun loadPhotosByCameraName(roverName: String, cameraName: String, solarDay: String) {
        repository.getPhotosByCameraName(_photosLiveData, roverName, cameraName, solarDay)
    }

}