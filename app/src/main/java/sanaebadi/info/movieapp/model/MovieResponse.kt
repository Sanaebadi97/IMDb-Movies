package sanaebadi.info.movieapp.model


import com.google.gson.annotations.SerializedName

data class MovieResponse(
    val page: Int,
    val movies: List<Movie>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)