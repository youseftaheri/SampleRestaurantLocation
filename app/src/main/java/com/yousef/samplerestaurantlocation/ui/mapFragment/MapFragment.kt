package com.yousef.samplerestaurantlocation.ui.mapFragment

import android.Manifest
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.location.LocationRequest.PRIORITY_HIGH_ACCURACY
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.gms.tasks.Task
import com.yousef.samplerestaurantlocation.BR
import com.yousef.samplerestaurantlocation.R
import com.yousef.samplerestaurantlocation.data.model.db.Restaurant
import com.yousef.samplerestaurantlocation.databinding.FragmentMapBinding
import com.yousef.samplerestaurantlocation.factory.ViewModelProviderFactory
import com.yousef.samplerestaurantlocation.ui.base.BaseFragment
import com.yousef.samplerestaurantlocation.utils.Const.DEFAULT_ZOOM
import com.yousef.samplerestaurantlocation.utils.Const.ERROR_DIALOG_REQUEST
import com.yousef.samplerestaurantlocation.utils.Const.LOCATION_PERMISSION_REQUEST_CODE
import com.yousef.samplerestaurantlocation.utils.Const.PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION
import com.yousef.samplerestaurantlocation.utils.Const.PERMISSIONS_REQUEST_ENABLE_GPS
import com.yousef.samplerestaurantlocation.utils.MyToast
import javax.inject.Inject


class MapFragment : BaseFragment<FragmentMapBinding, MapViewModel?>(), MapNavigator,
    OnMapReadyCallback, GoogleMap.OnCameraIdleListener,
    GoogleMap.OnMarkerClickListener{

    private var locationPermissionGranted: Boolean = false
    private lateinit var map: GoogleMap
    //    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var address: String = ""
    private lateinit var geocoder: Geocoder
    private var currentLocationList: MutableList<LatLng> = ArrayList()
    private var isCameraIdle = false
    private val mapMarkerToData: HashMap<Marker, Restaurant> = HashMap()
    private lateinit var currentLatLng: LatLng

    private val fusedLocationClient: FusedLocationProviderClient by lazy {
        LocationServices.getFusedLocationProviderClient(requireActivity().applicationContext)
    }

    private var cancellationTokenSource = CancellationTokenSource()

    private val callback = OnMapReadyCallback { googleMap ->
        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        val sydney = LatLng(-34.0, 151.0)
        googleMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }

    //, MapAdapter.MapAdapterListener {
    var mFragmentMapBinding: FragmentMapBinding? = null

    @JvmField
    @Inject
    var mLayoutManager: LinearLayoutManager? = null

    @JvmField
    @Inject
    var factory: ViewModelProviderFactory? = null
    private var mMapViewModel: MapViewModel? = null
    override val bindingVariable: Int
        get() = BR.viewModel

    override val layoutId: Int
        get() = R.layout.fragment_map

    override val viewModel: MapViewModel
        get() {
            mMapViewModel =
                ViewModelProvider(this, factory!!).get(MapViewModel::class.java)
            return mMapViewModel!!
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mMapViewModel!!.setNavigator(this)
        //      mMapAdapter.setListener(this);
    }
    fun requestDeviceLocationSettings() {
        val locationRequest = LocationRequest.create().apply {
            interval = 10000
            fastestInterval = 5000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }

        val builder = LocationSettingsRequest.Builder()
            .addLocationRequest(locationRequest)

        val client: SettingsClient = LocationServices.getSettingsClient(requireActivity())
        val task: Task<LocationSettingsResponse> = client.checkLocationSettings(builder.build())

        task.addOnSuccessListener { locationSettingsResponse ->
            // All location settings are satisfied. The client can initialize
            // location requests here.
            // ...
            val state = locationSettingsResponse.locationSettingsStates
//            if (ContextCompat.checkSelfPermission(
//                    requireContext().applicationContext,
//                    Manifest.permission.ACCESS_FINE_LOCATION
//                )
//                == PackageManager.PERMISSION_GRANTED
//            ) {
//                locationPermissionGranted = true
//                initMap()
//            onMapReady(map)
//            getLocationPermission()
//            }

        }

        task.addOnFailureListener { exception ->
            if (exception is ResolvableApiException) {
                // Location settings are not satisfied, but this can be fixed
                // by showing the user a dialog.
                try {
                    // Show the dialog by calling startResolutionForResult(),
                    // and check the result in onActivityResult().
                    exception.startResolutionForResult(
                        requireActivity(),
                        100
                    )
                } catch (sendEx: IntentSender.SendIntentException) {
                    // Ignore the error.
//                    textView.text = sendEx.message.toString()
                }
            }
        }

    }

    fun showToast(msg: String) {
        MyToast.show(requireActivity(), msg, true)
//        Toast.makeText(requireActivity(), msg, Toast.LENGTH_LONG).show()
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mFragmentMapBinding = viewDataBinding
    }

    override fun setMarkers(venues: List<Restaurant>?) {
        if (venues!!.isNotEmpty()) {
            map.clear()
            for (i in venues.indices) {
                val responseLatLng: LatLng = LatLng(venues[i].lat!!, venues[i].lng!!)
                val marker = MarkerOptions()
                    .position(responseLatLng)
                    .title(venues[i].name).visible(true)
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))

                mapMarkerToData[map.addMarker(marker)!!] = venues[i]
            }
        } else if (venues.isNullOrEmpty()) {
            Log.d(TAG, "An empty or null list was returned.")
        }
    }

    override fun onMarkerClick(marker: Marker?): Boolean {
        marker?.let {
            val locationData = mapMarkerToData[marker]
            mMapViewModel!!.storeSelectedRestaurant(locationData)
            locationData?.id?.let { locationId ->
                findNavController().navigate(
                    MapFragmentDirections.actionMapFragmentToDetailFragment(locationId))
            }
        }
        return true
    }

    override fun onCameraIdle() {
        val center: LatLng = map.cameraPosition.target
        Log.d(TAG, "onCameraIdle: center: $center")

        val latLngStr = center.latitude.toString() + "," + center.longitude.toString()

        if(latLngStr!= "0.0,0.0")
            mMapViewModel!!.searchByCategory(center, currentLatLng)

    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        onCameraIdle()

        if (isPermissionGranted()) {
            if (ActivityCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return
            }
            map.isMyLocationEnabled = true
            getDeviceLocation()
            setListeners()
            map.apply {
                uiSettings.isMyLocationButtonEnabled = true
//                uiSettings.isZoomControlsEnabled = true
            }
        }
    }

    /**
     *  Gets the SupportMapFragment and request notification when map is ready to be used
     */
    private fun initMap() {
        Log.d(TAG, "initMap: called")
        val mapFragment: SupportMapFragment? = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)

    }

    /**
     *  Gets the current device location
     */
    private fun getDeviceLocation() {
        Log.d(TAG, "getDeviceLocation: getting current location")
        try {
            if (ActivityCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED) {
                // Main code
                val currentLocationTask: Task<Location> = fusedLocationClient.getCurrentLocation(
                    PRIORITY_HIGH_ACCURACY,
                    cancellationTokenSource.token
                )

                currentLocationTask.addOnCompleteListener { task: Task<Location> ->
                    val result = if (task.isSuccessful) {
                        val result: Location = task.result

                        currentLatLng = LatLng(result.latitude, result.longitude)
                        moveCamera(currentLatLng, DEFAULT_ZOOM, getString(R.string.my_location))
                        "Location (success): ${result.latitude}, ${result.longitude}"
                    } else {
                        val exception = task.exception
                        "Location (failure): $exception"
                    }

                    Log.d(TAG, "getCurrentLocation() result: $result")
                }
            }
            isCameraIdle = true
        } catch (e: SecurityException) {
            Log.e(TAG, "getDeviceLocation: SecurityException: ${e.message!!}")
        }

    }

    /**
     *  Moves the camera to the current location
     */
    private fun moveCamera(latLng: LatLng, zoom: Float, title: String) {
        Log.d(
            TAG,
            "moveCamera: moving camera to: lat: ${latLng.latitude} , long: ${latLng.longitude}"
        )
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom))

        if (title != getString(R.string.my_location)) {
            val options = MarkerOptions()
                .position(latLng)
                .title(title)
            map.addMarker(options)

        }
    }

    private fun checkMap(): Boolean {
        if (isServicesOK()) {
            if (isGpsEnabled()) {
                return true
            }
        }
        return false
    }

    override fun onResume() {
        super.onResume()
        if (checkMap()) {
            if (!locationPermissionGranted) {
                getLocationPermission()
            }
        }
    }

    private fun setListeners() {
        map.setOnCameraIdleListener(this)
        map.setOnMarkerClickListener(this)
    }

    //////////////////////////////////////////////////////////////////////////

    // PERMISSIONS
    /**
     *  Makes sure that google services is installed on the device
     */
    private fun isServicesOK(): Boolean {

        Log.d(TAG, "isServicesOK: checking google services version")

        val available: Int =
            GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(requireContext())

        when {
            available == ConnectionResult.SUCCESS -> {
                Log.d(TAG, "isServicesOK: Google Play Services is working")
                return true
            }
            GoogleApiAvailability.getInstance().isUserResolvableError(available) -> {
                Log.d(TAG, "isServicesOK: an error occurred")

                val dialog: Dialog? = GoogleApiAvailability.getInstance()
                    .getErrorDialog(requireActivity(), available, ERROR_DIALOG_REQUEST)
                dialog!!.show()
            }
            else -> {
                MyToast.show(activity, "You can't make map requests",false)
            }
        }
        return false
    }

    /**
     *  Location permission for getting the location of the device.
     */
    private fun getLocationPermission() {
        if (ContextCompat.checkSelfPermission(
                requireContext().applicationContext,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
            == PackageManager.PERMISSION_GRANTED
        ) {
            locationPermissionGranted = true
            initMap()
        } else {
            ActivityCompat.requestPermissions(
                requireActivity(), arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION
            )
        }
    }

    /**
     *  Returns true if permission is granted
     */
    private fun isPermissionGranted(): Boolean {
        Log.d(TAG, "isPermissionGranted: called")
        return locationPermissionGranted
//        (ContextCompat.checkSelfPermission(
//            requireContext().applicationContext,
//            Manifest.permission.ACCESS_FINE_LOCATION
//        )
//                == PackageManager.PERMISSION_GRANTED)
    }

    /**
     *  Returns true is GPS is enabled.
     */
    private fun isGpsEnabled(): Boolean {
        Log.d(TAG, "checking gps")
        val manager = requireActivity().applicationContext.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            requestDeviceLocationSettings()

            Log.d(TAG, "gps is not enabled")
            return false
        }
        Log.d(TAG, "gps is enabled")
        return true
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        Log.d(TAG, "onRequestPermissionsResult: called")
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults.contains(PackageManager.PERMISSION_GRANTED)) {
                initMap()
            }
        }
    }


    companion object {
        private const val TAG = "MapFragment"
        fun newInstance(): MapFragment {
            val args = Bundle()
            val fragment = MapFragment()
            fragment.arguments = args
            return fragment
        }
    }
}


