package sanaebadi.info.movieapp.ui.fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_home.*
import sanaebadi.info.movieapp.R
import sanaebadi.info.movieapp.api.MovieApiInterface
import sanaebadi.info.movieapp.api.MovieClient
import sanaebadi.info.movieapp.repository.MoviePopularRepository
import sanaebadi.info.movieapp.ui.adapter.PopularMoviePagedListAdapter
import sanaebadi.info.movieapp.utilitis.NetworkState
import sanaebadi.info.movieapp.viewModel.PopularViewModel

/**
 * A simple [Fragment] subclass.
 */
class PopularFragment : Fragment() {

    private var navController: NavController? = null
    private lateinit var viewModel: PopularViewModel
    private lateinit var moviePopularRepository: MoviePopularRepository

    private lateinit var rvListItem: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val apiService: MovieApiInterface = MovieClient.getClient()
        moviePopularRepository = MoviePopularRepository(apiService)
        viewModel = getViewModel()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_home, container, false)

        rvListItem = view.findViewById(R.id.rv_movie_list)


        val movieAdapter = PopularMoviePagedListAdapter(activity!!)
        val gridLayoutManager = GridLayoutManager(activity, 2)

        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                val viewType = movieAdapter.getItemViewType(position)
                if (viewType == movieAdapter.MOVIE_VIEW_TYPE) return 1     //Movie_View_TYPE will occupy 1 out of 3 span
                else return 3                                             //NETWORK_VIEW_TYPE will occupy all 3 span
            }
        }



        viewModel.moviePageList.observe(viewLifecycleOwner, Observer {
            movieAdapter.submitList(it)
        })

        viewModel.networkState.observe(viewLifecycleOwner, Observer {
            progress_bar_popular.visibility =
                if (viewModel.listIsEmpty() && it == NetworkState.LOADING) View.VISIBLE else View.GONE

            txt_error_popular.visibility =
                if (viewModel.listIsEmpty() && it == NetworkState.ERROR) View.VISIBLE else View.GONE

            if (!viewModel.listIsEmpty()) {
                movieAdapter.setNetworkState(it)
            }
        })

        rvListItem.layoutManager = gridLayoutManager
        rvListItem.setHasFixedSize(true)
        rvListItem.adapter = movieAdapter

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        val bundle = bundleOf(
            "id" to 181812

        )

    }

    private fun getViewModel(): PopularViewModel {
        return ViewModelProviders.of(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return PopularViewModel(moviePopularRepository) as T
            }
        })[PopularViewModel::class.java]
    }
}
