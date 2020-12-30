package alaiz.hashim.placeguide

import androidx.lifecycle.ViewModel

class PlaceViewModel:ViewModel() {

    private val placeRepository = PlaceRepository.get()
    val placeListLiveData = placeRepository.getPlaces()
    val placeSchoolListLiveData=placeRepository.getPlaceByCategory(0)
    val placeHospitalListLiveData=placeRepository.getPlaceByCategory(1)
    val placePoliceStationListLiveData=placeRepository.getPlaceByCategory(2)
    val placeGardenListLiveData=placeRepository.getPlaceByCategory(3)

    fun addPlace(place: Place) {
        placeRepository.addPlace(place)
    }

}