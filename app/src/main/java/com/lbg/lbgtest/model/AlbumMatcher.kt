package com.lbg.lbgtest.model

import com.google.gson.annotations.SerializedName

data class AlbumMatcher(@SerializedName("album") var albums: List<Album>)

data class Album(@SerializedName("name") var  name : String, @SerializedName("artist") var artist : String,
                 @SerializedName("url") var url:String, @SerializedName("image") var imageList:List<AlbumImage>)

data class AlbumImage(@SerializedName("#text") var text : String , @SerializedName("size") var size : String)