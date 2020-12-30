package alaiz.hashim.placeguide


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import java.util.*

class PlaceDetailViewModel:ViewModel (){
    private val placeRepository = PlaceRepository.get()

    fun savePlace(place: Place) {
        placeRepository.addPlace(place)
        Log.d("PlaceDetailViewM_fun ","savePlace fun")
    }

/*
    private val placeIdLiveData = MutableLiveData<UUID>()
    var placeLiveData: LiveData<Place?> =
    Transformations.switchMap(placeIdLiveData) { placeId ->
        placeRepository.getPlaces(placeId)
    }


    fun loadPlace(placeId: UUID) {
        placeIdLiveData.value = placeId
    }
    fun deletePlace(place: Place){
        placeRepository.removePlace(place)
    }

 */



}