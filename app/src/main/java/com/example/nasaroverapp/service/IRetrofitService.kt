package com.example.nasaroverapp.service

import com.example.nasaroverapp.model.PhotoData
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface IRetrofitService {

    // apiKey = "1kHiIy6P21mNw500yHuP6RGdL1DSQVEeO9WXMkvZ"

    @GET("v1/rovers/{rover_name}/photos?&api_key=1kHiIy6P21mNw500yHuP6RGdL1DSQVEeO9WXMkvZ")
    fun getPhotosByRoverName(
        @Path("rover_name") roverName: String,
        @Query("sol") solarDay: String
    ): Single<PhotoData>

    @GET("v1/rovers/{rover_name}/photos?&api_key=1kHiIy6P21mNw500yHuP6RGdL1DSQVEeO9WXMkvZ")
    fun getPhotosByCameraName(
        @Path("rover_name") roverName: String,
        @Query("camera") cameraName: String,
        @Query("sol") solarDay: String
    ): Single<PhotoData>
}
