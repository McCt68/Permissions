package eu.example.permissions

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.app.ActivityCompat
import eu.example.permissions.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*

// In the manifest.xml file
// We can specify which permissions the app requires
// There are many different permissions
// Some require the app user to active give permission when running the app
// Other are not so strict and just informs the user that the permissions is needed when he installs thw app.

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        btnRequestPermissions.setOnClickListener {
            // call the request permissions function
            requestPermissions()
        }
    }

    private fun hasWriteExternalStoragePermission() =
        // Check if the permission is granted. Will return true or false
        ActivityCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED

    private fun hasLocationForegroundPermission() =
        ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED

    private fun hasLocationBackgroundPermission() =
        ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_BACKGROUND_LOCATION) == PackageManager.PERMISSION_GRANTED


    // Check if permissions is not given, and if not add them to the list of permissions to request
    private fun requestPermissions(){
        var permissonsToRequest = mutableListOf<String>() // list of permissions to request ( Empty for now)

        // run function hasWrite... and if it returns false, add the permission to our permissions to request list
        if (!hasWriteExternalStoragePermission()) {
            permissonsToRequest.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
        if (!hasLocationForegroundPermission()) {
            permissonsToRequest.add(Manifest.permission.ACCESS_COARSE_LOCATION)
        }
        if (!hasLocationBackgroundPermission()) {
            permissonsToRequest.add(Manifest.permission.ACCESS_BACKGROUND_LOCATION)
        }
        // check if permissionsToRequest is not empty, and if it not empty ask for those permissions that are in the list
        if (permissonsToRequest.isNotEmpty()) {
            ActivityCompat.requestPermissions(this,permissonsToRequest.toTypedArray(), 0)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == 0 && grantResults.isNotEmpty()) {
            for (i in grantResults.indices) { // loops through the Array until there is no more integers in it
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    Log.d("PermissionsRequest", "${permissions[i]} granted.")
                }
            }
        }
    }
}