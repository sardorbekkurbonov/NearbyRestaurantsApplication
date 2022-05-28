package com.uzpsb.nearbyrestaurantsapplication.ui.activity

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.uzpsb.nearbyrestaurantsapplication.R
import com.uzpsb.nearbyrestaurantsapplication.ui.fragment.MapFragment
import com.uzpsb.nearbyrestaurantsapplication.ui.dialog.WarningDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    override fun getLayout(): Int {
        return R.layout.activity_main
    }

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
    }

    override fun setupViews() {
        enableMyLocation()
    }


    private fun enableMyLocation() {
        val fineLocationGranted = isPermissionGranted(Manifest.permission.ACCESS_FINE_LOCATION)
        if (fineLocationGranted) {
            val mapFragment= MapFragment()
            pushFragment(R.id.container,mapFragment, MapFragment.fragmentTag)
        } else {
            requestPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        }
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            LOCATION_PERMISSION_REQUEST_CODE -> {
                val fineLocationGranted = isPermissionGranted(
                    permissions,
                    grantResults,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
                if (fineLocationGranted) {

                } else {
                    finish()
                }
            }
            else -> super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }

    private fun requestPermission(activity: AppCompatActivity, vararg permissions: String) {
        val shouldShowRationale = permissions.all { permission ->
            ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)
        }

        if (shouldShowRationale) {
            WarningDialog(this).show()
        } else {
            // Location permission has not been granted yet, request it.
            ActivityCompat.requestPermissions(
                activity,
                permissions,
                LOCATION_PERMISSION_REQUEST_CODE
            )
        }
    }

    private fun isPermissionGranted(
        grantPermissions: Array<String>,
        grantResults: IntArray,
        permission: String
    ): Boolean {
        for (i in grantPermissions.indices) {
            if (permission == grantPermissions[i]) {
                return grantResults[i] == PackageManager.PERMISSION_GRANTED
            }
        }
        return false
    }

    private fun isPermissionGranted(permission: String): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            permission
        ) == PackageManager.PERMISSION_GRANTED
    }

}