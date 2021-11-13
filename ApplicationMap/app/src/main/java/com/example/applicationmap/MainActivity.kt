package com.example.applicationmap

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.*

class MainActivity : AppCompatActivity() {

    val camera_permission_code = 100
    val camera_request = 1888

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var txtParametro = findViewById<EditText>(R.id.txtTelefone)
        val btnTelefone = findViewById<Button>(R.id.btnTelefone)
        btnTelefone.setOnClickListener{

            Log.i("ADS", "Iniciando Intent Ligação")
            val callIntent : Intent = Uri.parse("tel:${txtParametro.text.toString()}").let { number ->
                Intent(Intent.ACTION_CALL, number)
            }
            startActivity(callIntent)
        }

        val btnMapa = findViewById<Button>(R.id.btnMapa)
        btnMapa.setOnClickListener{
            val mapIntent : Intent = Uri.parse("geo:0,0?q=${txtParametro.text.toString()}").let { location ->
                Intent(Intent.ACTION_VIEW, location)
            }
            startActivity(mapIntent)
        }

        val btnCompartilhar = findViewById<Button>(R.id.btnCompartilhar)
            btnCompartilhar.setOnClickListener {
                val shareIntent: Intent = Intent(Intent.ACTION_SEND)
                shareIntent.setType("text/plain")
                shareIntent.putExtra(Intent.EXTRA_TEXT, txtParametro.text.toString())
                val title = "Selecione uma opeção de compartilhamento"
                val chooser = Intent.createChooser(shareIntent, title)
                startActivity(chooser)
            }

        val btnCamera = findViewById<Button>(R.id.btnFoto)
        btnCamera.setOnClickListener {
            if(checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED )
                requestPermissions(arrayOf(Manifest.permission.CAMERA), camera_permission_code)
            else{
                 var cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(cameraIntent, camera_request)
            }
        }

    }

    //METODO SUPER CLASSE - PALAVRA COMPLETE ONREQUESTPRERMISSIONRESULT
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == camera_permission_code)
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this, "A permissão foi concedida"   , Toast.LENGTH_LONG).show()
                val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(cameraIntent, camera_request)
            }else
                Toast.makeText(this, "A permissão foi negada"   , Toast.LENGTH_LONG).show()

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
         if(requestCode == camera_request && resultCode == RESULT_OK){
            val foto = data?.extras!!["data"] as Bitmap?
            val imgCamera = findViewById<ImageView>(R.id.imgCamera)
            imgCamera.setImageBitmap(foto)
         }

    }
}