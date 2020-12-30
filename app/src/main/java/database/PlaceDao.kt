package database

import alaiz.hashim.placeguide.Place
import androidx.lifecycle.LiveData
import androidx.room.*
import java.util.*

@Dao
interface PlaceDao {

    @Query("SELECT * FROM place ")
    fun getPlaces(): LiveData<List<Place>>

    @Query("SELECT * FROM place WHERE id=(:id)")
    fun getPlace(id: UUID): LiveData<Place?>

    @Query("SELECT * FROM place WHERE category = (:category)")
    fun getPlaceByCategory(category: Int):  LiveData<List<Place>>

    @Insert
    fun addPlace(place:Place)
    @Update
    fun updatePlace(place:Place)
    @Delete
    fun removePlace(place: Place)

}