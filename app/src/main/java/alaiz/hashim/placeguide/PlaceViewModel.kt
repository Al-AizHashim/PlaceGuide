package alaiz.hashim.placeguide

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel

class PlaceViewModel:ViewModel() {

    private val placeRepository = PlaceRepository.get()
    val placeListLiveData = placeRepository.getPlaces()
    val placeCategoryListLiveData=MutableLiveData<Int> ()
    //val placeSchoolListLiveData=placeRepository.getPlaceByCategory(0)
    //val placeHospitalListLiveData=placeRepository.getPlaceByCategory(1)
    //val placePoliceStationListLiveData=placeRepository.getPlaceByCategory(2)
   // val placeGardenListLiveData=placeRepository.getPlaceByCategory(3)

    var placesLiveData: LiveData<List<Place>>? =
        Transformations.switchMap(placeCategoryListLiveData) { category ->
            placeRepository.getPlaceByCategory(category)
        }

    fun loadPlaces(category:Int){
        placeCategoryListLiveData.value=category
    }


}