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
import android.os.Environment
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.*
import java.io.File
import java.io.FileOutputStream
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle


class MainActivity : AppCompatActivity() {

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var btnCarregarDados: Button
    private lateinit var txtView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        txtView = findViewById<TextView>(R.id.txtgeolocalizacao)
        btnCarregarDados = findViewById<Button>(R.id.btnCarregarDados)
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        btnCarregarDados.setOnClickListener {
            checkPermissions()

            val formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)
            val file = File(
                this.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS),
                "${LocalDateTime.now().format(formatter).toString()}.crd"
            )
            val fos = FileOutputStream(file)
            fos.write(txtView.text.toString().toByteArray())
            fos.close()
            Toast.makeText(this, "Arquivo Salvo com sucesso !", Toast.LENGTH_SHORT).show()

        }
    }

    private fun checkPermissions() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                128
            )
        } else {
            getLocations()
        }
    }

    @SuppressLint("MissingPermission")
    private fun getLocations() {
        fusedLocationProviderClient.lastLocation?.addOnSuccessListener {
            if (it == null) {
                txtView.text = "Localização não encontrada"
            } else it.apply {
                val latitude = it.latitude
                val longitude = it.longitude
                txtView.text = "Latitude: $latitude,\n Longitude: $longitude"
            }
        }
    }
}


//METODO PARA TRATAR REQUEST PERMISSIONS EM PARTICULAR
//  override fun onRequestPermissionsResult(
//      requestCode: Int,
//      permissions: Array<out String>,
//     grantResults: IntArray
// ) {
//     super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//     if(requestCode == 1){
//         if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
//             Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show()
//             getLocations()
//         }else{
//             Toast.makeText(this,"Permission Danied", Toast.LENGTH_SHORT).show()
//         }
//      }
//  }
//}
