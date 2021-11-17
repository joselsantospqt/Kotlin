package com.example.applicationredictlink

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.content.Intent
import android.net.Uri
import android.widget.Button

lateinit var title:String

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val textUrl = findViewById<EditText>(R.id.txt_url)
        val btn_click = findViewById<Button>(R.id.btn_abrir)
        title = "App com Intent Implícita"+ textUrl.text.toString()
        btn_click.setOnClickListener{
            getUrlFromIntent(textUrl.text.toString())
        }
    }

    fun getUrlFromIntent(url: String) {
        val url = "http://${url}"
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(url)
        startActivity(intent)
    }
}