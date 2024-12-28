package com.bignerdranch.android.movies.network

import com.bignerdranch.android.movies.database.OmdbResponse
import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface RetrofitInterface {
    @GET("services/rest?method=flickr.interestingness.getList")
    fun searchMovie(/*@Query("query") s: String*/): Call<OmdbResponse>

    @GET
    fun searchUrlBytes(@Url url: String): Call<ResponseBody>

    @GET("/?&apikey=1596d355")
    fun searchMovies(@Query("text") query: String): Call<OmdbResponse>
}