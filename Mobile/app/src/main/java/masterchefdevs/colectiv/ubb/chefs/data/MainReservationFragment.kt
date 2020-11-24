package masterchefdevs.colectiv.ubb.chefs.data

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.SearchView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import masterchefdevs.colectiv.ubb.chefs.R
import masterchefdevs.colectiv.ubb.chefs.auth.data.LoginRepository
import masterchefdevs.colectiv.ubb.chefs.auth.login.LoginViewModel
import masterchefdevs.colectiv.ubb.chefs.core.Api

class MainReservationFragment : Fragment() {
    private lateinit var viewModel: RestaurantViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_rezerva_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(RestaurantViewModel::class.java)

        view.findViewById<TextView>(R.id.restaurant_name).setText(viewModel.restaurant.value?.name)
        view.findViewById<TextView>(R.id.restaurant_address).setText(viewModel.restaurant.value?.address)
        view.findViewById<TextView>(R.id.rating_stars).setRawInputType(3)
        }

}