package com.lbg.lbgtest.apiInterface

import com.lbg.lbgtest.model.SearchResult

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.QueryMap

/**
 * Interface to interact with search service
 */
interface SearchService {

    @GET(".")
    fun getSearchResult(@QueryMap options: Map<String, String>): Observable<SearchResult>

}
