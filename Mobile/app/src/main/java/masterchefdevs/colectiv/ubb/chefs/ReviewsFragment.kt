package masterchefdevs.colectiv.ubb.chefs

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.fragment_first.*
import kotlinx.android.synthetic.main.fragment_reviews.*
import masterchefdevs.colectiv.ubb.chefs.core.TAG
import masterchefdevs.colectiv.ubb.chefs.data.RestaurantListAdaper
import masterchefdevs.colectiv.ubb.chefs.data.RestaurantListViewModel
import masterchefdevs.colectiv.ubb.chefs.data.restaurantFragment.RestaurantEditViewModel
import masterchefdevs.colectiv.ubb.chefs.data.reviews.ReviewListAdapter
import masterchefdevs.colectiv.ubb.chefs.data.reviews.ReviewListViewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER


/**
 * A simple [Fragment] subclass.
 * Use the [ReviewsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ReviewsFragment : Fragment() {

    private lateinit var itemListAdapter: ReviewListAdapter
    private lateinit var itemsModel: ReviewListViewModel

    companion object {
        const val ITEM_ID = "ITEM_ID"
    }
    private var itemId: String? = null

    private lateinit var restaurantwModel: RestaurantEditViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            itemId = it.getString(ReviewsFragment.ITEM_ID)
//            param2 = it.getString(ARG_PARAM2)
        }
        Log.v(TAG, "itemid:"+itemId)
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_reviews, container, false)
    }

    private fun setupItemList() {
        itemListAdapter = ReviewListAdapter(this)
        reviews_list.adapter = itemListAdapter
        itemsModel = ViewModelProvider(this).get(ReviewListViewModel::class.java)

        itemsModel.items.observe(viewLifecycleOwner, { items ->
            Log.i(TAG, "update items")
            itemListAdapter.items = items
            itemListAdapter.filterItems = items
        })
        itemsModel.loading.observe(viewLifecycleOwner, { loading ->
            Log.i(TAG, "update loading")
            progress3.visibility = if (loading) View.VISIBLE else View.GONE
        })
        itemsModel.loadingError.observe(viewLifecycleOwner, { exception ->
            if (exception != null) {
                Log.i(TAG, "update loading error")
                val message = "Loading exception ${exception.message}"
                //toast un mesaj care se afisaza pentru o durata scurta de timp
                Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
            }
        })
        itemsModel.loadItems()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.i(TAG, "onActivityCreated Reviews")
        setupItemList()
        setupViewModel()
    }
    private fun setupViewModel() {
        restaurantwModel = ViewModelProvider(this).get(RestaurantEditViewModel::class.java)
        restaurantwModel.loadItem(ITEM_ID)

        restaurantwModel.rating.observe(viewLifecycleOwner, { rating ->
            view?.findViewById<RatingBar>(R.id.rating_stars_stat_rew)?.rating = rating
        })
        restaurantwModel.item.observe(viewLifecycleOwner, { item ->
            Log.v(TAG, "update items")

            view?.findViewById<TextView>(R.id.res_name_rew)?.setText(item.nameR)

        })
        restaurantwModel.fetching.observe(viewLifecycleOwner, { fetching ->
            Log.v(TAG, "update fetching")
            progress3.visibility = if (fetching) View.VISIBLE else View.GONE
        })
        restaurantwModel.fetchingError.observe(viewLifecycleOwner, { exception ->
            if (exception != null) {
                Log.v(TAG, "update fetching error")
                val message = "Fetching exception ${exception.message}"
                val parentActivity = activity?.parent
                if (parentActivity != null) {
                    Toast.makeText(parentActivity, message, Toast.LENGTH_SHORT).show()
                }
            }
        })

        val id = itemId
        if (id != null) {
            restaurantwModel.loadItem(id)
        }

    }
}