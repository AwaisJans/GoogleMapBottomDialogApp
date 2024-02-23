package com.jans.googlemap.cut.edge.app.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.Manifest.permission
import android.content.Intent
import android.content.pm.PackageManager
import android.widget.Button
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.jans.googlemap.cut.edge.app.R

class FirstScreenGoogleMap : AppCompatActivity() {


    lateinit var btnMap: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first_screen_google_map)

        btnMap = findViewById(R.id.btnMap)

        if(checkLocationPerm()){
            btnMap.setOnClickListener{
                startActivity(Intent(this@FirstScreenGoogleMap, MapScreen::class.java))
            }
        }
        else{
            ActivityCompat.requestPermissions(this,
                arrayOf(permission.ACCESS_FINE_LOCATION), 1)
            btnMap.setOnClickListener{
                Toast.makeText(this, "permission denied", Toast.LENGTH_SHORT).show()
            }
        }


    }


    private fun checkLocationPerm():Boolean{
        return (checkSelfPermission(permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
    }


}