package com.lbg.lbgtest.model

import com.google.gson.annotations.SerializedName

data class ArtistMatcher (@SerializedName("artist") var artist: List<Artist>)

data class Artist( @SerializedName("name") var name: String, @SerializedName("listeners") var listeners: String ,
                   @SerializedName("url") var url: String, @SerializedName("image") var imageList: List<AlbumImage>)
