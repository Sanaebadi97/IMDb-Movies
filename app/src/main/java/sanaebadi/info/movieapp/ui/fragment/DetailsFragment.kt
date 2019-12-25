package sanaebadi.info.movieapp.ui.fragment


import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_details.*
import sanaebadi.info.movieapp.R
import sanaebadi.info.movieapp.api.MovieApiInterface
import sanaebadi.info.movieapp.api.MovieClient
import sanaebadi.info.movieapp.api.POSTER_BASE_URL
import sanaebadi.info.movieapp.model.MovieDetails
import sanaebadi.info.movieapp.repository.MovieDetailsRepository
import sanaebadi.info.movieapp.utilitis.NetworkState
import sanaebadi.info.movieapp.viewModel.DetailsViewModel
import java.text.NumberFormat
import java.util.*


/**
 * A simple [Fragment] subclass.
 */
class DetailsFragment : Fragment() {

    private lateinit var viewModel: DetailsViewModel
    private lateinit var repository: MovieDetailsRepository


    private var movieId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        movieId = arguments!!.getInt("id")

        println("FIRST MOVIE ID IN DETAILS IS $movieId")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_details, container, false)
        activity!!.window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        val apiService: MovieApiInterface = MovieClient.getClient()
        repository = MovieDetailsRepository(apiService)


        viewModel = getViewModel(movieId!!)


        viewModel.movieDetails.observe(viewLifecycleOwner, Observer {
            bindUi(it)

        })

        viewModel.networkState.observe(viewLifecycleOwner, Observer {
            progress_bar.visibility = if (it == NetworkState.LOADED) View.GONE else View.VISIBLE
            txt_error.visibility = if (it == NetworkState.ERROR) View.VISIBLE else View.GONE
        })



        return view
    }


    @SuppressLint("SetTextI18n")
    private fun bindUi(it: MovieDetails) {
        movie_title.text = it.title
        movie_tagline.text = it.tagline
        movie_release_date.text = it.releaseDate
        movie_rating.text = it.rating.toString()
        movie_overview.text = it.overview
        movie_runtime.text = "${it.runtime} minutes"

        val formatCurrency = NumberFormat.getCurrencyInstance(Locale.US)
        movie_budget.text = formatCurrency.format(it.budget)
        movie_revenue.text = formatCurrency.format(it.revenue)

        val moviePosterURL = POSTER_BASE_URL + it.posterPath
        Glide.with(this)
            .load(moviePosterURL)
            .into(iv_movie_poster)
    }

    private fun getViewModel(movieId: Int): DetailsViewModel {
        return ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return DetailsViewModel(repository, movieId) as T
            }

        })[DetailsViewModel::class.java]
    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
    }

    override fun onStop() {
        super.onStop()
        (activity as AppCompatActivity?)!!.supportActionBar!!.show()
    }

}
