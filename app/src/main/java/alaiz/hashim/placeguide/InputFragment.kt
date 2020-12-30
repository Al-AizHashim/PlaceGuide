package alaiz.hashim.placeguide

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import java.util.*


class InputFragment : Fragment() {
    private lateinit var nameField: EditText
    private lateinit var addressField: EditText
    private lateinit var longitudeField: EditText
    private lateinit var latitudeField: EditText
    private lateinit var placeSpinner: Spinner

    private val placeDetailViewModel: PlaceDetailViewModel by lazy {
        ViewModelProvider(this).get(PlaceDetailViewModel::class.java)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_input, container, false)
        setHasOptionsMenu(true)
        nameField = view.findViewById(R.id.name_et) as EditText
        addressField = view.findViewById(R.id.address_et) as EditText
        longitudeField = view.findViewById(R.id.longitude_et) as EditText
        latitudeField = view.findViewById(R.id.latitude_et) as EditText
        placeSpinner = view.findViewById(R.id.place_spinner)
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val appCompatActivity = activity as AppCompatActivity
        appCompatActivity.supportActionBar?.setTitle("Add place")


    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.input_fragment_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_add) {
            insertDataToDb()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun insertDataToDb() {
        val name = nameField.text.toString()
        val address = addressField.text.toString()
        val placeCategory=placeSpinner.selectedItemPosition
        val latitude = latitudeField.text.toString().toDouble()
        val longitude = longitudeField.text.toString().toDouble()


        val validation = verifyDataFromUser(name, address, latitude, longitude)
        if (validation) {
            // Insert Data to Database
            val newPlace = Place()
            newPlace.name = name
            newPlace.address = address
            newPlace.latitude = latitude
            newPlace.longitude = longitude
            newPlace.category=placeCategory
            placeDetailViewModel.savePlace(newPlace)

            Toast.makeText(requireContext(), "Place Successfully added!", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(requireContext(), "Please fill out all fields.", Toast.LENGTH_SHORT)
                .show()
        }
    }

    fun verifyDataFromUser(
        name: String,
        address: String,
        latitude: Double,
        longitude: Double
    ): Boolean {
        return !(name.isEmpty() || address.isEmpty() || latitude.toString()
            .isEmpty() || longitude.toString().isEmpty())
    }


    companion object {
        fun newInstance(): InputFragment {
            return InputFragment()
        }
    }


}