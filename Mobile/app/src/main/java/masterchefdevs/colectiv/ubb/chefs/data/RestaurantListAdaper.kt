package masterchefdevs.colectiv.ubb.chefs.data


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
//import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.view_restaurant.view.*

import masterchefdevs.colectiv.ubb.chefs.R
import masterchefdevs.colectiv.ubb.chefs.data.restaurantFragment.RestaurantEditFragment
import masterchefdevs.colectiv.ubb.chefs.core.TAG
import masterchefdevs.colectiv.ubb.chefs.data.model.Restaurant
import java.io.InputStream
import java.net.URL
import java.util.*
import kotlin.collections.ArrayList

class RestaurantListAdaper(
    private val fragment: Fragment
) :RecyclerView.Adapter<RestaurantListAdaper.ViewHolder>() , Filterable {

    var items = emptyList<Restaurant>()
        set(value) {
            field = value
            notifyDataSetChanged();
        }

    var filterItems=emptyList<Restaurant>()
        set(value) {
            field = value
//            notifyDataSetChanged();
        }
    private var onItemClick: View.OnClickListener;

    init {
        filterItems=items
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
        val item = filterItems[position]
        holder.itemView.tag = item
        holder.name.text = item.nameR
        holder.adresa.text = item.adresa
//        Log.i(TAG, "poza url: "+item.poza)
        //Picasso.get().load(item.poza).into(holder.image);
        Log.v(TAG, "poza: "+item.poza)

        var el= DownloadImageTask(holder.image).execute(item.poza);

        holder.itemView.setOnClickListener(onItemClick)
    }
    override fun getItemCount() =  filterItems.size
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.restaurant_name
        val adresa: TextView = view.restaurant_address
      val image:ImageView=view.imageView5
    }



    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                Log.i(TAG, "perform filtering")
                Log.i(TAG, charSearch)
                if (charSearch.isEmpty()) {
                    filterItems = items
                    Log.i(TAG, "is empty")
                }else{
                    val resultList = ArrayList<Restaurant>()
                    for (row in items) {
                        Log.i(TAG, row.nameR)
                        if (row.nameR.toLowerCase(Locale.ROOT).contains(charSearch.toLowerCase(Locale.ROOT))) {
                            Log.i(TAG, row.nameR)
                            resultList.add(row)
                        }
                    }
                    Log.i(TAG, resultList.size.toString())
                    filterItems = resultList
//                    items=resultList
                }
                val filterResults = FilterResults()
                filterResults.values = filterItems
                return filterResults
            }
            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filterItems = if(results == null || results.values == null)
                    ArrayList<Restaurant>()
                else
                    results.values as List<Restaurant>
//                items=filterItems
                Log.i(TAG, filterItems.size.toString())
                notifyDataSetChanged()
            }
        }
    }

    private class DownloadImageTask(var bmImage: ImageView) :
        AsyncTask<String?, Void?, Bitmap?>() {
        override fun onPostExecute(result: Bitmap?) {
            bmImage.setImageBitmap(result)
            Log.i(TAG, "onpost")
        }
        override fun doInBackground(vararg params: String?): Bitmap? {
            val urldisplay = params[0]
            var mIcon11: Bitmap? = null
            try {
                val `in`: InputStream = URL(urldisplay).openStream()
                mIcon11 = BitmapFactory.decodeStream(`in`)
            } catch (e: Exception) {
                Log.e("Error", e.message!!)
                e.printStackTrace()
            }
            return mIcon11
        }
    }
}
