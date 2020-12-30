package database

import alaiz.hashim.placeguide.Place
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [ Place::class ], version=1)
@TypeConverters(PlaceTypeConverters::class)
abstract class PlaceDatabase : RoomDatabase() {
    abstract fun placeDao(): PlaceDao
}