package sanaebadi.info.movieapp.repository

import androidx.lifecycle.LiveData
import io.reactivex.disposables.CompositeDisposable
import sanaebadi.info.movieapp.api.MovieApiInterface
import sanaebadi.info.movieapp.model.MovieDetails
import sanaebadi.info.movieapp.utilitis.MovieDetailsNetworkDataSource
import sanaebadi.info.movieapp.utilitis.NetworkState

class MovieDetailsRepository(private val apiService: MovieApiInterface) {
    lateinit var movieDetailsNetworkDataSource: MovieDetailsNetworkDataSource

    fun fetchMovieDetails(
        compositeDisposable: CompositeDisposable,
        movieId: Long
    ): LiveData<MovieDetails> {

        movieDetailsNetworkDataSource =
            MovieDetailsNetworkDataSource(apiService, compositeDisposable)
        movieDetailsNetworkDataSource.fetchMovieDetails(movieId)

        return movieDetailsNetworkDataSource.downloadMovieDetailsResponse

    }

    fun getMovieDetailsNetworkState(): LiveData<NetworkState> {
        return movieDetailsNetworkDataSource.networkState
    }
}