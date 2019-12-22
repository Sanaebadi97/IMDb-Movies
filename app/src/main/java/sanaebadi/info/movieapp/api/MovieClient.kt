package sanaebadi.info.movieapp.api

import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit


/**
 * https://image.tmdb.org/t/p/w342/or06FN3Dka5tukK1e9sl16pB3iy.jpg => image url
 * https://api.themoviedb.org/3/movie/popular?api_key=f882fe7e318f300420b26bdf6e0db009&page=3 =>popular movies in page 3
 * https://api.themoviedb.org/3/movie/181812?api_key=f882fe7e318f300420b26bdf6e0db009 => details of movies
 * https://api.themoviedb.org/3/ => base url
 */

const val API_KEY = "6e63c2317fbe963d76c3bdc2b785f6d1"
const val BASE_URL = "https://api.themoviedb.org/3/"
const val POSTER_BASE_URL = "https://image.tmdb.org/t/p/w342"

object MovieClient {

    fun getClient(): MovieApiInterface {

        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY


        val client = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .connectTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(object : Interceptor {
                @Throws(IOException::class)
                override fun intercept(chain: Interceptor.Chain): Response {

                    var request: Request = chain.request()
                    val url: HttpUrl =
                        request.url.newBuilder().addQueryParameter("api_key", API_KEY).build()
                    request = request.newBuilder().url(url).build()

                    request = request
                        .newBuilder()
                        .url(url)
                        .build()
                    return chain.proceed(request)
                }
            }).build()


        return Retrofit.Builder()
            .client(client)
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MovieApiInterface::class.java)
    }
}