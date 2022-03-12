package com.example.applicationbuscarlocalizao


import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.documentfile.provider.DocumentFile
import com.google.android.gms.location.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle


class MainActivity : AppCompatActivity(), LocationListener {

    private lateinit var btnGravarDados: Button
    private lateinit var btnCarregarDados: Button
    private lateinit var txtView: TextView
    val COARSE_REQUEST = 12345
    val FINE_REQUEST = 67890
    val WRITE_REQUEST = 999
    val READ_REQUEST = 888

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        txtView = findViewById<TextView>(R.id.txtgeolocalizacao)
        btnGravarDados = findViewById<Button>(R.id.btnGravarDados)
        btnCarregarDados = findViewById<Button>(R.id.btnLer)
        btnGravarDados.setOnClickListener {
            var location: Location? = obterCoordenasRede()
            if (location != null) {
                val latitude = location.latitude
                val longitude = location.longitude
                val formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)
                txtView.text = "Latitude: $latitude,\n Longitude: $longitude"

                val intent = Intent(Intent.ACTION_CREATE_DOCUMENT)
                intent.addCategory(Intent.CATEGORY_OPENABLE)
                intent.setType("text/plain")
                intent.putExtra(
                    Intent.EXTRA_TITLE,
                    "${LocalDateTime.now().format(formatter).toString()}.crd"
                )
                startActivityForResult(intent, WRITE_REQUEST)
                Toast.makeText(this, "Carregando !", Toast.LENGTH_SHORT).show()

            } else {
                Toast.makeText(this, "Houve um problema !", Toast.LENGTH_SHORT).show()

            }

        }
        btnCarregarDados.setOnClickListener {
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
            intent.addCategory(Intent.CATEGORY_OPENABLE)
            intent.setType("*/*")
            startActivityForResult(intent, READ_REQUEST)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == WRITE_REQUEST && resultCode == RESULT_OK) {
            val txtTexto = txtView.text
            val fos = getContentResolver().openOutputStream(data?.getData()!!)
            fos!!.write(txtTexto.toString().toByteArray())
            fos!!.close()
            txtView.setText(null)
            Toast.makeText(this, "Gravou com sucesso !", Toast.LENGTH_SHORT).show()

        }

        if (requestCode == READ_REQUEST && resultCode == RESULT_OK) {
            txtView.setText(null)
            val fis = getContentResolver().openInputStream(data?.getData()!!)
            val bytes = fis!!.readBytes()
            fis!!.close()
            val uri: Uri = data?.data!!
            val documentFile = DocumentFile.fromSingleUri(this, uri)
            if (documentFile!!.type.equals("text/plain")) {
                txtView.setText(String(bytes))
                Toast.makeText(this, "Carregado com sucesso !", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(
                    this,
                    "O tipo de arquivo é incompativel.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun obterCoordenasRede(): Location? {
        var location: Location? = null
        var locationManager = this.getSystemService(LOCATION_SERVICE) as LocationManager
        val isProviderEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)

        if (isProviderEnabled) {

            if (checkSelfPermission(android.Manifest.permission.ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_GRANTED
            ) {
                locationManager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER,
                    2000L,
                    0f,
                    this
                )
                location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
            }
            return location
        } else {
            this.requestPermissions(
                arrayOf(android.Manifest.permission.ACCESS_COARSE_LOCATION),
                COARSE_REQUEST
            )
            return location
        }
    }

    private fun obterCoordenasGps(): Location? {
        var location: Location? = null
        val locationManager = this.getSystemService(LOCATION_SERVICE) as LocationManager
        val isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        if (isGpsEnabled) {

            if (checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED
            ) {
                locationManager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER,
                    2000L,
                    0f,
                    this
                )
                location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
            }
            return location
        } else {
            requestPermissions(
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                FINE_REQUEST
            )
            return location
        }
    }

    override fun onLocationChanged(p0: Location) {
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == COARSE_REQUEST && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            this.obterCoordenasRede()
        } else if (requestCode == FINE_REQUEST && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            this.obterCoordenasGps()
        }
    }
}
