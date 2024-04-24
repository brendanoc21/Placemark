package ie.setu.placemark.views.placemarkList

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import ie.setu.placemark.R
import ie.setu.placemark.adapters.PlacemarkAdapter
import ie.setu.placemark.adapters.PlacemarkListener
import ie.setu.placemark.databinding.ActivityPlacemarkListBinding
import ie.setu.placemark.main.MainApp
import ie.setu.placemark.models.PlacemarkModel

class PlacemarkListView : AppCompatActivity(), PlacemarkListener {

    lateinit var app: MainApp
    private lateinit var binding: ActivityPlacemarkListBinding
    lateinit var presenter: PlacemarkListPresenter
    private var position: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlacemarkListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.topAppBar.title = title
        setSupportActionBar(binding.topAppBar)
        presenter = PlacemarkListPresenter(this)
        app = application as MainApp

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        loadPlacemarks()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_add -> { presenter.doAddPlacemark() }
            R.id.item_map -> { presenter.doShowPlacemarksMap() }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onPlacemarkClick(placemark: PlacemarkModel, position: Int) {
        this.position = position
        presenter.doEditPlacemark(placemark, this.position)
    }

    private fun loadPlacemarks() {
        binding.recyclerView.adapter = PlacemarkAdapter(presenter.getPlacemarks(), this)
        onRefresh()
    }

    fun onRefresh() {
        binding.recyclerView.adapter?.
        notifyItemRangeChanged(0,presenter.getPlacemarks().size)
    }

    fun onDelete(position : Int) {
        binding.recyclerView.adapter?.notifyItemRemoved(position)
    }
}