package masterchefdevs.colectiv.ubb.chefs.data.reviews

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_review.view.*
import kotlinx.android.synthetic.main.view_restaurant.view.*
import masterchefdevs.colectiv.ubb.chefs.R
import masterchefdevs.colectiv.ubb.chefs.core.TAG
import masterchefdevs.colectiv.ubb.chefs.data.RestaurantListAdaper
import masterchefdevs.colectiv.ubb.chefs.data.model.Restaurant
import masterchefdevs.colectiv.ubb.chefs.data.model.Review
import masterchefdevs.colectiv.ubb.chefs.data.restaurantFragment.RestaurantEditFragment
import java.io.InputStream
import java.net.URL
import java.util.*
import kotlin.collections.ArrayList

class ReviewListAdapter(
    private val fragment: Fragment
) : RecyclerView.Adapter<ReviewListAdapter.ViewHolder>() {

    var items = emptyList<Review>()
        set(value) {
            field = value
            notifyDataSetChanged();
        }

    var filterItems=emptyList<Review>()
        set(value) {
            field = value
//            notifyDataSetChanged();
        }


    init {
        filterItems=items
        Log.v(TAG, "init adapter ")

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_review, parent, false)
        Log.v(TAG, "onCreateViewHolder")
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.v(TAG, "onBindViewHolder $position")
        val item = filterItems[position]
        holder.itemView.tag = item
        holder.name.text = item.id_U.toString()
        holder.review.text = item.mesaj

    }
    override fun getItemCount() =  filterItems.size
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.username_t
        val review: TextView = view.review_t

    }



}
