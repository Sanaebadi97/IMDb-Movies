package sanaebadi.info.movieapp.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import io.reactivex.disposables.CompositeDisposable
import sanaebadi.info.movieapp.model.Movie
import sanaebadi.info.movieapp.repository.MoviePopularRepository
import sanaebadi.info.movieapp.utilitis.NetworkState

class PopularViewModel(private val moviePopularRepository: MoviePopularRepository) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    val moviePageList: LiveData<PagedList<Movie>> by lazy {
        moviePopularRepository.fetchLiveMoviePagedList(compositeDisposable)
    }

    val networkState: LiveData<NetworkState> by lazy {
        moviePopularRepository.getNetworkState()
    }

    fun listIsEmpty(): Boolean {
        return moviePageList.value?.isEmpty() ?: true
    }





    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

}