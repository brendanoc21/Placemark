package ie.setu.placemark.activities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import ie.setu.placemark.R
import ie.setu.placemark.adapters.PlacemarkAdapter
import ie.setu.placemark.adapters.PlacemarkListener
import ie.setu.placemark.databinding.ActivityPlacemarkListBinding
import ie.setu.placemark.main.MainApp
import ie.setu.placemark.models.PlacemarkModel

class PlacemarkListActivity : AppCompatActivity(), PlacemarkListener {

    lateinit var app: MainApp
    private lateinit var binding: ActivityPlacemarkListBinding
    private var position: Int = 0

    override fun onPlacemarkClick(placemark: PlacemarkModel, pos : Int) {
        val launcherIntent = Intent(this, PlacemarkActivity::class.java)
        launcherIntent.putExtra("placemark_edit", placemark)
        position = pos
        getResult.launch(launcherIntent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlacemarkListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.topAppBar.title = title
        setSupportActionBar(binding.topAppBar)

        app = application as MainApp

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = PlacemarkAdapter(app.placemarks.findAll(),this)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_add -> {
                val launcherIntent = Intent(this, PlacemarkActivity::class.java)
                getResult.launch(launcherIntent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private val getResult =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            when(it.resultCode) {
                Activity.RESULT_OK ->
                    (binding.recyclerView.adapter)?.notifyItemRangeChanged(
                        0,
                        app.placemarks.findAll().size)
                Activity.RESULT_CANCELED ->
                    Snackbar.make(
                        binding.root,
                        getString(R.string.menu_cancelPlacemark), Snackbar.LENGTH_LONG).show()
                99 ->
                    (binding.recyclerView.adapter)?.notifyItemRemoved(position)
            }
        }
}