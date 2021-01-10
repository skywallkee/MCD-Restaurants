package masterchefdevs.colectiv.ubb.chefs.data


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.view_restaurant.view.*

import masterchefdevs.colectiv.ubb.chefs.R
import masterchefdevs.colectiv.ubb.chefs.data.restaurantFragment.RestaurantEditFragment
import masterchefdevs.colectiv.ubb.chefs.core.TAG
import masterchefdevs.colectiv.ubb.chefs.data.model.Restaurant

class RestaurantListAdaper(
    private val fragment: Fragment
) : RecyclerView.Adapter<RestaurantListAdaper.ViewHolder>() {

    var items = emptyList<Restaurant>()
        set(value) {
            field = value
            notifyDataSetChanged();
        }
    private var onItemClick: View.OnClickListener;

    init {
        Log.v(TAG, "init adapter ")
        onItemClick = View.OnClickListener { view ->
            val item = view.tag as Restaurant
            Log.v(TAG, "on item view")
            fragment.findNavController().navigate(R.id.restaurantEditFragment, Bundle().apply {
                putString(RestaurantEditFragment.ITEM_ID, item.id.toString())
            })
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_restaurant, parent, false)
        Log.v(TAG, "onCreateViewHolder")
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.v(TAG, "onBindViewHolder $position")
        val item = items[position]
        holder.itemView.tag = item
        holder.name.text = item.nameR
        holder.adresa.text = item.adresa
        holder.itemView.setOnClickListener(onItemClick)
    }

    override fun getItemCount() = items.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.restaurant_name
        val adresa: TextView = view.restaurant_address
    }
}
