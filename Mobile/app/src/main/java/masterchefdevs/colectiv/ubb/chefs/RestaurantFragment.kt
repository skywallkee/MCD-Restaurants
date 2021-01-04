package masterchefdevs.colectiv.ubb.chefs

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RatingBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import masterchefdevs.colectiv.ubb.chefs.core.TAG
import masterchefdevs.colectiv.ubb.chefs.data.RestaurantViewModel
import java.util.*


class RestaurantFragment : Fragment(), OnMapReadyCallback {
    private lateinit var viewModel: RestaurantViewModel
    private lateinit var mMap: GoogleMap
    private lateinit var myContext: Context
    private lateinit var geoCoder : Geocoder

    companion object {
        var ITEM_ID = "ITEM_ID"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       arguments?.let {
            ITEM_ID = it.getString("ITEM_ID").toString()
       }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        myContext = inflater.context
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_restaurant, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d(TAG, ITEM_ID);
        setUpElements()

        view.findViewById<Button>(R.id.back2).setOnClickListener {
            findNavController().navigate(R.id.action_restaurantFragment_to_FirstFragment)
        }
        view.findViewById<Button>(R.id.make_reservation_button).setOnClickListener{
            findNavController().navigate(
                R.id.action_restaurantFragment_to_MakeReservation,
                Bundle().apply {
                    putString("ITEM_ID", ITEM_ID)
                })
        }
        view.findViewById<Button>(R.id.view_statistics_button).setOnClickListener{
            findNavController().navigate(
                R.id.action_restaurantFragment_to_Satistics,
                Bundle().apply {
                    putString("ITEM_ID", ITEM_ID)

                })
        }
    }
    fun setUpElements(){
        geoCoder = Geocoder(this.myContext, Locale.getDefault())

        viewModel = ViewModelProvider(this).get(RestaurantViewModel::class.java)
        viewModel.getRestaurant(ITEM_ID.toInt())

        viewModel.restaurant.observe(viewLifecycleOwner, { restaurant ->
            view?.findViewById<TextView>(R.id.restaurant_name_map)?.setText(restaurant.nameR)
            view?.findViewById<TextView>(R.id.restaurant_address_map)?.setText(restaurant.adresa)
            view?.findViewById<RatingBar>(R.id.rating_stars_map)?.rating = 3.0f
        })
    }

    fun getAddress(): String?{
        return viewModel.restaurant.value?.adresa
    }
// poate utila pt map?? deocamdata nu merge
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        var address: String? = getAddress()
        var list = geoCoder.getFromLocationName(address, 1);
        val location: Address = list.get(0)
        val lat = location.getLatitude()
        val lng = location.getLongitude()
        // Add a marker in Sydney and move the camera
        val addressCoord = LatLng(lat, lng)
        mMap.addMarker(
            MarkerOptions()
                .position(addressCoord)
                .title("Marker in Sydney")
        )
        mMap.moveCamera(CameraUpdateFactory.newLatLng(addressCoord))
    }
}