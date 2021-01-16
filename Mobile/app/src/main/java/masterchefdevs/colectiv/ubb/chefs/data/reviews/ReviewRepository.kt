package masterchefdevs.colectiv.ubb.chefs.data.reviews

import android.util.Log
import masterchefdevs.colectiv.ubb.chefs.core.RestaurantApi
import masterchefdevs.colectiv.ubb.chefs.core.RestaurantApi.service

import masterchefdevs.colectiv.ubb.chefs.core.TAG
import masterchefdevs.colectiv.ubb.chefs.data.model.Restaurant
import masterchefdevs.colectiv.ubb.chefs.data.model.Review
import masterchefdevs.colectiv.ubb.chefs.data.remote.RemoteReviewDataSource

object ReviewRepository {
    private var cachedItems: MutableList<Review>? = null;
    suspend fun loadAll(): List<Review> {
        Log.i(TAG, "loadAll reviews")
        if (cachedItems != null) {
            return cachedItems as List<Review>;
        }
        cachedItems = mutableListOf()
        val items = RemoteReviewDataSource.service.find()
        cachedItems?.addAll(items)
        return cachedItems as List<Review>
    }

    suspend fun load(itemId: Number): Review {
        Log.i(TAG, "load review")
        Log.i(TAG, itemId.toString())
        val item = cachedItems?.find { it.id == itemId }
        if (item != null) {
            return item
        }
        return RemoteReviewDataSource.service.read(itemId)
    }

    suspend fun save(item: Review): Review {
        Log.i(TAG, "save review")
        val createdItem = RemoteReviewDataSource.service.create(item)
        cachedItems?.add(createdItem)
        return createdItem
    }

    suspend fun update(item: Review): Review {
        Log.i(TAG, "update review")
        val updatedItem = RemoteReviewDataSource.service.update(item.id, item)
        //asta trebuie adaugata eventuala
        //cachedItems?.add(updatedItem)
        Log.i(TAG, updatedItem.id.toString())
        val index = cachedItems?.indexOfFirst { it.id == updatedItem.id }
        Log.i(TAG, updatedItem.mesaj
        )
        if (index != null) {
            cachedItems?.set(index, updatedItem)
        }
        return updatedItem
    }
}