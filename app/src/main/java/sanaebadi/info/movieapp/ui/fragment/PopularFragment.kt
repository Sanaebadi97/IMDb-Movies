package sanaebadi.info.movieapp.ui.fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.fragment_home.*

import sanaebadi.info.movieapp.R

/**
 * A simple [Fragment] subclass.
 */
class PopularFragment : Fragment() {

    private var navController: NavController? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        val bundle = bundleOf(
            "id" to 181812

        )

        btn.setOnClickListener {

            navController!!.navigate(R.id.action_homeFragment_to_detailsFragment, bundle)
        }
    }
}
