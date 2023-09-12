package com.example.gallery.models

class MyImage {
    var id:Int?=null
    var name:String?=null
    var imageURI:String?=null


    constructor(id:Int?,name:String?,imageURI:String?){
        this.id=id
        this.name=name
        this.imageURI=imageURI
    }

    constructor(name:String?,imageURI:String?){
        this.name=name
        this.imageURI=imageURI
    }

}