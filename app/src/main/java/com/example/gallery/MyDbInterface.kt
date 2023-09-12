package com.example.gallery

import com.example.gallery.models.MyImage

interface MyDbInterface {
    fun addImage(myImage: MyImage)
    fun getImage():ArrayList<MyImage>
}