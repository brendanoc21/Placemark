package ie.setu.placemark.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import ie.setu.placemark.databinding.ActivityPlacemarkBinding
import ie.setu.placemark.main.MainApp
import ie.setu.placemark.models.PlacemarkModel
import timber.log.Timber
import timber.log.Timber.i

class PlacemarkActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPlacemarkBinding
    var placemark = PlacemarkModel()
    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPlacemarkBinding.inflate(layoutInflater)
        setContentView(binding.root)

        app = application as MainApp
        i("Placemark Activity started...")

        binding.btnAdd.setOnClickListener() {
            placemark.title = binding.placemarkTitle.text.toString()
            placemark.description = binding.placemarkDescription.text.toString()
            if (placemark.title.isNotEmpty() && placemark.description.isNotEmpty()) {
                i("add Button Pressed: ${placemark.title}, ${placemark.description}")
                app.placemarks.add(placemark.copy())
                for (i in app.placemarks.indices) {
                    i("Placemark[$i]:${app.placemarks[i]}")
                }
            }
            else {
                Snackbar
                    .make(it,"Please Enter a title and description", Snackbar.LENGTH_LONG)
                    .show()
            }
        }
    }
}
        /*
        binding.themeButton.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                Toast.makeText(applicationContext, "Dark Mode", Toast.LENGTH_LONG).show()
                i("Dark Mode active")
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                Toast.makeText(applicationContext, "Light Mode", Toast.LENGTH_LONG).show()
                i("Light mode active")
            }
        }
        */