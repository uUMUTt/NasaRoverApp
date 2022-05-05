package com.example.nasaroverapp.model

data class PhotoByRover(
    val camera: Camera,
    val earth_date: String,
    val id: Int,
    val img_src: String,
    val rover: Rover,
    val sol: Int
)