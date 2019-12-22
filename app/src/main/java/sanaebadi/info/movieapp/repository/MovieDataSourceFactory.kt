package sanaebadi.info.movieapp.repository

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import io.reactivex.disposables.CompositeDisposable
import sanaebadi.info.movieapp.api.MovieApiInterface
import sanaebadi.info.movieapp.model.Movie
import sanaebadi.info.movieapp.utilitis.MovieDataSource


class MovieDataSourceFactory(
    private val apiService: MovieApiInterface,
    private val compositeDisposable: CompositeDisposable
) : DataSource.Factory<Int, Movie>() {

    val moviesLiveDataSource = MutableLiveData<MovieDataSource>()


    override fun create(): DataSource<Int, Movie> {
        val movieDataSource = MovieDataSource(apiService, compositeDisposable)

        moviesLiveDataSource.postValue(movieDataSource)
        return movieDataSource
    }

}