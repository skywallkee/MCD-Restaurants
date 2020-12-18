package masterchefdevs.colectiv.ubb.chefs.data

import android.util.Log
import masterchefdevs.colectiv.ubb.chefs.core.RestaurantApi
import masterchefdevs.colectiv.ubb.chefs.core.TAG
import masterchefdevs.colectiv.ubb.chefs.data.model.Restaurant
import masterchefdevs.colectiv.ubb.chefs.data.model.RestaurantDTO

object RestaurantRepository {
    private var cachedItems: MutableList<Restaurant>? = null;

    suspend fun loadAll(): List<Restaurant> {
        Log.i(TAG, "loadAll")
        if (cachedItems != null) {
            return cachedItems as List<Restaurant>;
        }
        cachedItems = mutableListOf()
        val items = RestaurantApi.service.find()
        cachedItems?.addAll(items)
        return cachedItems as List<Restaurant>
    }

    suspend fun load(itemId: String): Restaurant {
        Log.i(TAG, "load")
        Log.i(TAG, itemId.toString())
        val item = cachedItems?.find { it.id.toString() == itemId }
        if (item != null) {
            return item
        }
        return RestaurantApi.service.read(itemId)
    }

    suspend fun save(item: Restaurant): Restaurant {
        Log.i(TAG, "save")
        val createdItem = RestaurantApi.service.create(item)
        cachedItems?.add(createdItem)
        return createdItem
    }

    suspend fun update(item: Restaurant): Restaurant{
        Log.i(TAG, "update")
        val updatedItem = RestaurantApi.service.update(item.id.toString(), item)
        //asta trebuie adaugata eventuala
        //cachedItems?.add(updatedItem)
        Log.i(TAG, updatedItem.id.toString())
        val index = cachedItems?.indexOfFirst { it.id == updatedItem.id }
        Log.i(TAG, updatedItem.nameR
        )
        if (index != null) {
            cachedItems?.set(index, updatedItem)
        }
        return updatedItem
    }
}