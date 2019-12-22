package sanaebadi.info.movieapp.api

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * https://image.tmdb.org/t/p/w342/or06FN3Dka5tukK1e9sl16pB3iy.jpg => image url
 * https://api.themoviedb.org/3/movie/popular?api_key=f882fe7e318f300420b26bdf6e0db009&page=3 =>popular movies in page 3
 * https://api.themoviedb.org/3/movie/181812?api_key=f882fe7e318f300420b26bdf6e0db009 => details of movies
 * https://api.themoviedb.org/3/ => base url
 */

const val API_KEY = "f882fe7e318f300420b26bdf6e0db009"
const val BASE_URL = " https://api.themoviedb.org/3/"
const val POSTER_BASE_URL = "https://image.tmdb.org/t/p/w342/"

object MovieClient {

    fun getClient(): MovieApiInterface {
        // Interceptor take only one argument which is a lambda function so parenthesis can be omitted
        val requestInterceptor = Interceptor { chain ->

            val url = chain.request()
                .url()
                .newBuilder()
                .addQueryParameter("api_key", API_KEY)
                .build()

            val request = chain.request()
                .newBuilder()
                .url(url)
                .build()

            //explicitly return a value from whit @ annotation. lambda always returns the value of the last expression implicitly
            return@Interceptor chain.proceed(request)
        }

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(requestInterceptor)
            .connectTimeout(60, TimeUnit.SECONDS)
            .build()

        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MovieApiInterface::class.java)
    }
}