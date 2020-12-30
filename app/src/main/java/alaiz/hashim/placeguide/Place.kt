package alaiz.hashim.placeguide

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class Place( @PrimaryKey val id: UUID = UUID.randomUUID(),
    var name: String = "",
    var address: String = "",
    var latitude: Double = 13.5670213,
    var longitude: Double = 44.0115724,
    var category: Int = 0,
    var city: String = ""
)