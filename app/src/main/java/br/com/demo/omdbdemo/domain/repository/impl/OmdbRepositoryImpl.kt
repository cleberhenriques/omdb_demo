package br.com.demo.omdbdemo.domain.repository.impl

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.demo.omdbdemo.data.api.OmdbAPI
import br.com.demo.omdbdemo.data.mapper.MovieMapper
import br.com.demo.omdbdemo.data.response.MovieResponse
import br.com.demo.omdbdemo.domain.model.Movie
import br.com.demo.omdbdemo.domain.repository.OmdbRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class OmdbRepositoryImpl @Inject constructor(private val api: OmdbAPI) : OmdbRepository {

    override fun searchMovies(title: String, type: String?, year: String?): LiveData<List<Movie>> {
        val movieLiveData = MutableLiveData<List<Movie>>()
        api.getSearch(title, type, year).enqueue(object : Callback<List<MovieResponse>> {
            override fun onFailure(call: Call<List<MovieResponse>>, t: Throwable) {
                movieLiveData.value = null
            }

            override fun onResponse(call: Call<List<MovieResponse>>, response: Response<List<MovieResponse>>) {
                movieLiveData.value = MovieMapper.toMovieList(response.body())
            }

        })
        return movieLiveData
    }

    override fun getMovie(id: String): LiveData<Movie> {
        val movieLiveData = MutableLiveData<Movie>()
        api.getMovie(id).enqueue(object : Callback<MovieResponse> {
            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                movieLiveData.value = null
            }

            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                movieLiveData.value = MovieMapper.toMovie(response.body())
            }

        })
        return movieLiveData
    }
}