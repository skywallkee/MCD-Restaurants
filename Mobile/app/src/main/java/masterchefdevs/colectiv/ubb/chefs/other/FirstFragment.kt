package masterchefdevs.colectiv.ubb.chefs.other

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_first.*
import masterchefdevs.colectiv.ubb.chefs.R
import masterchefdevs.colectiv.ubb.chefs.auth.data.LoginRepository
import masterchefdevs.colectiv.ubb.chefs.core.Api
import masterchefdevs.colectiv.ubb.chefs.core.TAG
import masterchefdevs.colectiv.ubb.chefs.data.RestaurantListAdaper
import masterchefdevs.colectiv.ubb.chefs.data.RestaurantListViewModel
import java.io.InputStream
import java.net.URL


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {
    private lateinit var itemListAdapter: RestaurantListAdaper
    private lateinit var itemsModel: RestaurantListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?{
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (Api.tokenInterceptor.token!=null) {
            view.findViewById<TextView>(R.id.toolbar_text)
                .setText("Welcome " + LoginRepository.user?.username + " ! ")
        }
        else
            view.findViewById<Button>(R.id.logout_button).visibility = View.GONE;

        view.findViewById<Button>(R.id.button_first).setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }
        view.findViewById<Button>(R.id.logout_button).setOnClickListener {
            LoginRepository.logout()
            view.findViewById<TextView>(R.id.toolbar_text)
                .setText("")
        }

//        val img_1 = view.findViewById(R.id.imageView4) as ImageView
//        val img_2 = view.findViewById(R.id.imageView5) as ImageView
//        img_1.setOnClickListener{
//            findNavController().navigate(R.id.action_FirstFragment_to_restaurantFragment)
//        }
//        img_2.setOnClickListener{
//            findNavController().navigate(R.id.action_FirstFragment_to_restaurantFragment)
//        }
        view.findViewById<SearchView>(R.id.search_view_first).setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                itemListAdapter.filter.filter(query)
                return false
            }

        })
//            findNavController().navigate(R.id.action_FirstFragment_to_filterFragment)
//        }

        this.view?.setBackgroundColor(Color.CYAN);
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.i(TAG, "onActivityCreated")
        setupItemList()
    }

    private fun setupItemList() {
        itemListAdapter = RestaurantListAdaper(this)
        restaurant_list.adapter = itemListAdapter
        itemsModel = ViewModelProvider(this).get(RestaurantListViewModel::class.java)

        itemsModel.items.observe(viewLifecycleOwner, { items ->
            Log.i(TAG, "update items")
            itemListAdapter.items = items
            itemListAdapter.filterItems = items
        })
        itemsModel.loading.observe(viewLifecycleOwner, { loading ->
            Log.i(TAG, "update loading")
            progress.visibility = if (loading) View.VISIBLE else View.GONE
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
}