package alaiz.hashim.placeguide

import android.app.Application



class PlaceIntentApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        PlaceRepository.initialize(this)
    }
}