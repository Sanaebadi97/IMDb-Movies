package sanaebadi.info.movieapp.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import sanaebadi.info.movieapp.model.MovieDetails
import sanaebadi.info.movieapp.repository.MovieDetailsRepository
import sanaebadi.info.movieapp.utilitis.NetworkState

class DetailsViewModel(private val movieDetailsRepository: MovieDetailsRepository, movieId: Int) :
    ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    //we used lazy for better performance
    val movieDetails: LiveData<MovieDetails> by lazy {
        movieDetailsRepository.fetchMovieDetails(compositeDisposable, movieId)
    }

    val networkState: LiveData<NetworkState> by lazy {
        movieDetailsRepository.getMovieDetailsNetworkState()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}