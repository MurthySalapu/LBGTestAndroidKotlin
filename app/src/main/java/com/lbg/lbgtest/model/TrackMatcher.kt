package com.lbg.lbgtest.model

import com.google.gson.annotations.SerializedName

data class TrackMatcher (
    @SerializedName("track") var track: List<Track>
)

data class Track (
    @SerializedName("name") var name: String,
    @SerializedName("listeners") var listeners: String,
    @SerializedName("artist")  var artist: String,
    @SerializedName("url") var url: String,
    @SerializedName("image") var imageList: List<AlbumImage>
)
