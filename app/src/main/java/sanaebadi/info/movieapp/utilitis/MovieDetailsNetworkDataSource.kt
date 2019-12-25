package sanaebadi.info.movieapp.utilitis

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import sanaebadi.info.movieapp.api.MovieApiInterface
import sanaebadi.info.movieapp.model.MovieDetails
import java.lang.Exception

const val TAG: String = "MovieDetailsNetwork"

class MovieDetailsNetworkDataSource(
    private val apiService: MovieApiInterface,
    private val compositeDisposable: CompositeDisposable
) {


    private val _networkState = MutableLiveData<NetworkState>()
    val networkState: LiveData<NetworkState>
        get() = _networkState

    private val _downloadMovieDetailsResponse = MutableLiveData<MovieDetails>()
    val downloadMovieDetailsResponse: LiveData<MovieDetails>
        get() = _downloadMovieDetailsResponse

    fun fetchMovieDetails(movieId: Int) {
        _networkState.postValue(NetworkState.LOADING)

        try {
            compositeDisposable.add(
                apiService.getMovieDetails(movieId)
                    .subscribeOn(Schedulers.io())
                    .subscribe(
                        {
                            _downloadMovieDetailsResponse.postValue(it)
                            _networkState.postValue(NetworkState.LOADED)
                        },
                        {
                            _networkState.postValue(NetworkState.ERROR)
                            Log.e(TAG, it.message)
                        }
                    )
            )
        } catch (e: Exception) {
            Log.e(TAG, e.message)
        }
    }
}