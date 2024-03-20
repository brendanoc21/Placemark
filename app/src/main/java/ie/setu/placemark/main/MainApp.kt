package ie.setu.placemark.main

import android.app.Application
import ie.setu.placemark.models.PlacemarkJSONStore
import ie.setu.placemark.models.PlacemarkMemStore
import ie.setu.placemark.models.PlacemarkModel
import ie.setu.placemark.models.PlacemarkStore
import timber.log.Timber
import timber.log.Timber.i

class MainApp : Application() {

    lateinit var placemarks: PlacemarkStore

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        //placemarks = PlacemarkMemStore()
        placemarks = PlacemarkJSONStore(applicationContext)
        i("Placemark started")
    }
}