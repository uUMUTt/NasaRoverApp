package com.example.nasaroverapp.repository

import androidx.lifecycle.MutableLiveData
import com.example.nasaroverapp.model.PhotoData
import com.example.nasaroverapp.service.IRetrofitService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import java.lang.Exception
import javax.inject.Inject

class RetrofitRepository @Inject constructor(private val retrofitServiceInstance: IRetrofitService) {

    private val disposable = CompositeDisposable()

    fun getPhotosByRoverName(
        livePhotoData: MutableLiveData<PhotoData>,
        roverName: String,
        solarDay: String
    ) {
        println(roverName)
        disposable.addAll(
            retrofitServiceInstance.getPhotosByRoverName(roverName, solarDay)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<PhotoData>() {
                    override fun onSuccess(photoData: PhotoData) {
                        try {
                            livePhotoData.value = photoData
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }

                    override fun onError(e: Throwable) {
                        e.printStackTrace()
                    }

                })
        )

    }

    fun getPhotosByCameraName(
        livePhotoData: MutableLiveData<PhotoData>,
        roverName: String,
        cameraName: String,
        solarDay: String
    ) {

        disposable.addAll(
            retrofitServiceInstance.getPhotosByCameraName(roverName, cameraName, solarDay)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<PhotoData>() {
                    override fun onSuccess(photoData: PhotoData) {
                        try {
                            livePhotoData.value = photoData
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }

                    override fun onError(e: Throwable) {
                        e.printStackTrace()
                    }

                })
        )

    }
}