package com.example.applicationbuscarlocalizao


import android.Manifest
import android.content.Intent
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import org.w3c.dom.Text
import android.location.LocationManager
import android.net.Uri
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.*


class MainActivity : AppCompatActivity(){

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var btnCarregarDados: Button
    private lateinit var txtView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnCarregarDados = findViewById<Button>(R.id.btnCarregarDados)
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        btnCarregarDados.setOnClickListener {
                checkPermissions()
            }
        }

    private fun checkPermissions() {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION), 1)
        }else{
            getLocations()
        }
    }
    @SuppressLint("MissingPermission")
    private fun getLocations() {
        fusedLocationProviderClient.lastLocation?.addOnSuccessListener {
            txtView = findViewById<TextView>(R.id.txtgeolocalizacao)
            if(it == null){
                txtView.text = "Localização não encontrada"
            }else it.apply {
                val latitude = it.latitude
                val longitude = it.longitude
                txtView.text = "Latitude: $latitude, Longitude: $longitude"
                val mapIntent : Intent = Uri.parse("geo: $latitude,$longitude  ?z=zoom").let { location ->
                    Intent(Intent.ACTION_VIEW, location)
                }
                startActivity(mapIntent)
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == 1){
            if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show()
                getLocations()
            }else{
                Toast.makeText(this,"Permission Danied", Toast.LENGTH_SHORT).show()
            }
        }
    }

}
