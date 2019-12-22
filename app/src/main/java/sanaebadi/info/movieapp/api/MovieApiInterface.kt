package sanaebadi.info.movieapp.api

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import sanaebadi.info.movieapp.model.MovieDetails


/**
 * https://api.themoviedb.org/3/movie/popular?api_key=f882fe7e318f300420b26bdf6e0db009&page=3 =>popular movies in page 3
 * https://api.themoviedb.org/3/movie/181812?api_key=f882fe7e318f300420b26bdf6e0db009 => details of movies
 * https://api.themoviedb.org/3/ => base url
 *
 */
interface MovieApiInterface {

    //details
    @GET("movie/{movie_id}")
    fun getMovieDetails(@Path("movie_id") movieId: Long): Single<MovieDetails>

}