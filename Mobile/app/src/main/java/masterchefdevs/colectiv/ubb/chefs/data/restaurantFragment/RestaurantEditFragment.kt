package masterchefdevs.colectiv.ubb.chefs.data.restaurantFragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_first.*

import kotlinx.android.synthetic.main.fragment_restaurant.*
import masterchefdevs.colectiv.ubb.chefs.R
import masterchefdevs.colectiv.ubb.chefs.core.TAG

class RestaurantEditFragment: Fragment() {
    companion object {
        const val ITEM_ID = "ITEM_ID"
    }

    private lateinit var viewModel: RestaurantEditViewModel
    private var itemId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.v(TAG, "onCreate")
        arguments?.let {
            if (it.containsKey(ITEM_ID)) {
                itemId = it.getString(ITEM_ID).toString()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.v(TAG, "onCreateView")
        return inflater.inflate(R.layout.fragment_restaurant, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<Button>(R.id.make_reservation_button).setOnClickListener{
            findNavController().navigate(R.id.action_restaurantEditFragment_to_restaurantLayout,
                Bundle().apply {
                    putString("ITEM_ID", itemId)

                })
        }

        view.findViewById<Button>(R.id.view_statistics_button).setOnClickListener {
            findNavController().navigate(R.id.action_restaurantFragment_to_Satistics,
                Bundle().apply {
                    putString("ITEM_ID", itemId)

                })
        }
        Log.v(TAG, "onViewCreated")
        itemId?.let { Log.v(TAG, it) }
//        item_text.setText(itemId)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.v(TAG, "onActivityCreated")
        setupViewModel()

    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(this).get(RestaurantEditViewModel::class.java)
        viewModel.loadItem(ITEM_ID)
        viewModel.rating.observe(viewLifecycleOwner, { rating ->
            view?.findViewById<RatingBar>(R.id.rating_stars_map)?.rating = rating
        })
        viewModel.item.observe(viewLifecycleOwner, { item ->
            Log.v(TAG, "update items")
            view?.findViewById<TextView>(R.id.restaurant_name_map)?.setText(item.nameR)
            view?.findViewById<TextView>(R.id.restaurant_address_map)?.setText(item.adresa)
        })
        viewModel.fetching.observe(viewLifecycleOwner, { fetching ->
            Log.v(TAG, "update fetching")
            progress2.visibility = if (fetching) View.VISIBLE else View.GONE
        })
        viewModel.fetchingError.observe(viewLifecycleOwner, { exception ->
            if (exception != null) {
                Log.v(TAG, "update fetching error")
                val message = "Fetching exception ${exception.message}"
                val parentActivity = activity?.parent
                if (parentActivity != null) {
                    Toast.makeText(parentActivity, message, Toast.LENGTH_SHORT).show()
                }
            }
        })
        viewModel.completed.observe(viewLifecycleOwner, Observer { completed ->
            if (completed) {
                Log.v(TAG, "completed, navigate back")
                findNavController().navigateUp()
            }
        })
        val id = itemId
        if (id != null) {
            viewModel.loadItem(id)
        }

        view?.findViewById<Button>(R.id.back_to_list)?.setOnClickListener {
            findNavController().navigate(R.id.action_restaurantEditFragment_to_FirstFragment)
        }
    }
}