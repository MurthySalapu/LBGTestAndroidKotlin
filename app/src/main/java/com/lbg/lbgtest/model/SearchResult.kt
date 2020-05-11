package com.lbg.lbgtest.model

import com.google.gson.annotations.SerializedName

data class SearchResult(
    @SerializedName("results") var result: Result
)

data class Result (
    @SerializedName("albummatches")  var albumMatcher: AlbumMatcher?,
    @SerializedName("artistmatches") var artistMatcher: ArtistMatcher?,
    @SerializedName("trackmatches") var trackMatcher: TrackMatcher?
)