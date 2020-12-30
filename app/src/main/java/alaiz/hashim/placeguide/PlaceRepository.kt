package alaiz.hashim.placeguide


import android.content.Context

import androidx.lifecycle.LiveData
import androidx.room.Room
import database.PlaceDatabase

import java.util.*
import java.util.concurrent.Executors

private const val DATABASE_NAME = "place-database"
class PlaceRepository private constructor(context: Context) {


    private val database : PlaceDatabase = Room.databaseBuilder(
        context.applicationContext,
        PlaceDatabase::class.java,
        DATABASE_NAME
    ).build()

    private val placeDao = database.placeDao()

    private val executor = Executors.newSingleThreadExecutor()

    fun getPlaces(): LiveData<List<Place>> = placeDao.getPlaces()

    fun getPlaces(id: UUID): LiveData<Place?> = placeDao.getPlace(id)
    fun getPlaceByCategory(category: Int): LiveData<List<Place>> = placeDao.getPlaceByCategory(category)

    fun addPlace(place: Place) {
        executor.execute {
            placeDao.addPlace(place)
        }
    }
    fun removePlace(place: Place){
        executor.execute {
            placeDao.removePlace(place)
        }
    }
    fun updatePlace(place: Place) {
        executor.execute {
            placeDao.updatePlace(place)
        }
    }

    companion object {
        private var INSTANCE: PlaceRepository? = null

        fun initialize(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = PlaceRepository(context)
            }
        }

        fun get(): PlaceRepository {
            return INSTANCE ?:
            throw IllegalStateException("PlaceRepository must be initialized")
        }
    }
}